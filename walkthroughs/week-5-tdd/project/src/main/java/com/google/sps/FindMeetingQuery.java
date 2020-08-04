// Copyright 2019 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     https://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.google.sps;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public final class FindMeetingQuery {
  public Collection<TimeRange> query(Collection<Event> events, MeetingRequest request) {
    long workingPeriod = request.getDuration();
    // If meeting duration is greater than a day, then meeting cannot happen.
    if (workingPeriod > TimeRange.WHOLE_DAY.duration()) return new ArrayList<TimeRange>();

    // If no attendees are present, then the meeting can happen at any time of the day.
    if (events.isEmpty()) return Arrays.asList(TimeRange.WHOLE_DAY);

    ArrayList<TimeRange> attendeesWorkTime = getWorkTime(getTimeConflicts(events, request), workingPeriod);
    
    return attendeesWorkTime;
  }

  /**
   * Returns an arraylist containing time range that works for everyone.
   * @param timeConflicts The list of conflicting times between the attendees.
   * @param workingPeriod Duration of meeting in minutes.
   */
  private ArrayList<TimeRange> getWorkTime(List<TimeRange> timeConflicts, long workingPeriod) {
    ArrayList<TimeRange> workTime = new ArrayList<>();
    Collections.sort(timeConflicts, TimeRange.ORDER_BY_START);
    int workTimeStart = TimeRange.START_OF_DAY;
    int workTimeEnd = 0;
    int trackMergingConflict = 0;
    for (TimeRange timeConflict: timeConflicts) {
        workTimeEnd = timeConflict.start();
        if (workingPeriod <= workTimeEnd - workTimeStart){
          workTime.add(TimeRange.fromStartEnd(workTimeStart, workTimeEnd, false));
        }
        if (trackMergingConflict < timeConflict.end()) {
          trackMergingConflict = timeConflict.end(); 
        }
        if (trackMergingConflict > timeConflict.end()) {
          workTimeStart = trackMergingConflict;
        } else {
          workTimeStart = timeConflict.end();
        }
    } 
    if (TimeRange.END_OF_DAY - workTimeStart >= workingPeriod) {
      workTime.add(TimeRange.fromStartEnd(workTimeStart, TimeRange.END_OF_DAY, true));
    }
    return workTime;
  }

  /**
   *Returns a list time range that contains time conflicts.
   * @param events The collection of events happening.
   * @param request Meeting request that is to be scheduled for attendees. 
   */
  private List<TimeRange> getTimeConflicts(Collection<Event> events, MeetingRequest request) {
    List <TimeRange> timeConflicts = new ArrayList<>();
    for (Event event: events) {
      Set<String> eventAttendees = event.getAttendees();
      if (!Collections.disjoint(eventAttendees, request.getAttendees())) {
        timeConflicts.add(event.getWhen());
      }
    }
    return timeConflicts;
  }
}
