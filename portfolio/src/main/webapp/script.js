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

/**
 * Adds a fun fact to the page.
 */
function getRandomQuoteUsingArrowFunctions() {
  fetch('/fact').then(response => response.text()).then((message) => {
    document.querySelector('#fun-fact').innerText = JSON.parse(message);
  });
}
/**
  *Fetches comments as a list.  
  */
function getCommentsUsingArrowFunctions() {
  const commentContainer = document.querySelector('#comments');
  fetch('/comments')
    .then(response => response.json())
    .then(formComments => {
      formComments.forEach(comment => {
        const commentList = document.createElement("li");
        commentList.innerText = comment.commentText;
        commentContainer.appendChild(commentList);
      });
  });
}
/** Creates a map and adds it to the page. 
function createMap() {
  const map = new google.maps.Map(
      document.querySelector('#map'),
      {center: {lat: 0.00000, lng: 0.00000}, zoom: 5});
    
  const hagiasophiaMarker = new google.maps.Marker({
      position: {lat:41.008469, lng:28.980261},
      map: map,
      title:'Hagia Sophia'
  })
  const trexMarker = new google.maps.Marker({
    position: {lat: 37.421903, lng: -122.084674},
    map: map,
    title: 'Stan the T-Rex'
  })
  const londonMarker = new google.maps.Marker({
      position: {lat:51.503323, lng:-0.119543},
      map:map, 
      title:'London'
  })
  const chandigarhMarker = new google.maps.Marker({
      position: {lat:30.733315, lng:76.779419},
      map:map, 
      title:'Chandigarh'
  })*/
function createMap() {
  const locations = [
      ['Hagia Sophia', 41.008469, 28.980261],
      ['Stan the T-Rex', 37.421903, -122.084674],
      ['London', 51.503323, -0.119543],
      ['Chandigarh', 30.733315, 76.779419],
      ['Liandudno', 53.326540, -3.834460]
    ];
  const map = new google.maps.Map(document.querySelector('#map'), {
    zoom: 1.5,
    center: new google.maps.LatLng(0.00000, 0.00000),
    mapTypeId: google.maps.MapTypeId.TERRAIN
  });

  const infowindow = new google.maps.InfoWindow();

  var marker, i;
  
  for (i = 0; i < locations.length; i++) {  
    marker = new google.maps.Marker({
      position: new google.maps.LatLng(locations[i][1], locations[i][2]),
      map: map
      });
    google.maps.event.addListener(marker, 'click', (function(marker, i) {
      return function() {
      infowindow.setContent(locations[i][0]);
      infowindow.open(map, marker);
      }
      })(marker, i));
    }
 }