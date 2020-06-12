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
