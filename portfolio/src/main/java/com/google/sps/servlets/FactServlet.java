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

package com.google.sps.servlets;

import com.google.gson.Gson;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/** Servlet that returns a fun fact about me.*/
@WebServlet("/fact")
public class FactServlet extends HttpServlet {
    
  private List<String> funFacts;

  @Override
  public void init() {
    funFacts = new ArrayList<>();
    funFacts.add("I play three musical instruments.");
    funFacts.add("I've travelled to five countries in three different continents.");
    funFacts.add("I've launched a solid motored rocket which reached around 200 ft high.");
  }

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    String json = new Gson().toJson(funFacts);
    response.setContentType("text/html");
    response.getWriter().println(json);
  }
}
