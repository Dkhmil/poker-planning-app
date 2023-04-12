# poker-planning-app


Feature: Session management
1) Scenario: New session
   Given I want create a place to make a poker planning session
   When I type a title
   And select the deck type
   And summit the form
   Then I enter in a new poker planning session
   And I have a link to invite to other people to join
   ==========================================================

   ?? //think about retreiving from jwt userId;

   Create a new poker planning session:

   URL: POST /sessions
   Parameters:
   - title (string, required): the title of the session
   - deck_type (string, required): the type of the deck to be used in the session
   Response:
   status: 201 Created
   body:
    - session_id (string): the ID of the created session
    - link (string): the link to invite other people to join the session
      ===========================================================
2) Scenario: Enter session
   Given I receive an invitation link to join in a poker planning session
   And I navigate to it
   When I type my name/nickname
   And summit the form
   Then I enter in the poker planning session
   And I see the title of the session
   And I able to see the user stories list to vote
   And I able to see the members list joined in the session

   ============================================================

   Enter a poker planning session:

   URL: POST /sessions/{session_id}/enter
   Parameters:
   - name (string, required): the name/nickname of the user joining the session
   Response:
   status: 200 OK
   body:
   - title (string): the title of the session
   - user_stories (array): an array of user stories in PENDING status
   - members (array): an array of members joined in the session

   ============================================================


3) Scenario: Destroy poker planing session
   Given I'm in as poker planning session
   And I want to destroy the session
   When I push "Destroy Session" button
   And double check my intend
   Then All data are destroyed
   And I'm redirected to a confirmation page

   ===============================================================
   Destroy a poker planning session:

   URL: DELETE /sessions/{session_id}
   Parameters:
   confirmation (boolean, required): a boolean value confirming the intent to destroy the session
   Response:
   status: 204 No Content

   
===============================================================


Feature: Votes management
4)  Scenario: Start voting a user story
    Given There are PENDING or VOTED user stories
    When I push the button "Start" of the user story
    Then the user story move to VOTING status
    And Card/Vote options are enable to select for all the connected members


	=================================================================
    Start voting a user story:
    URL: POST /user_stories/{user_story_id}/start

    Request body: None

    Response:

    HTTP status code 200 OK if the request was successful.
    HTTP status code 404 Not Found if the user story with the specified ID was not found.
	
	=================================================================


5) Scenario: Vote a user story
   Given There is a user story in VOTING status
   When I select a card/vote option for it
   Then the user story "emitted votes" increase in 1
   And I'm marked as vote emmited
   And the other members are not able to see my vote


==================================================================
Vote a user story:
URL: PUT /user_stories/{user_story_id}/vote
Parameters:
card (string, required): the selected card/vote option

* also need to pass UserId?

Response:
status: 200 OK
body:
emitted_votes (integer): the number of emitted votes for the user story
vote_emitted (boolean): a boolean value indicating if the user has already emitted a vote

    
==================================================================


6) Scenario: Listen to the votation status
   Given There is a user story in VOTING status
   When someone emits a vote
   Then all the members see who has emited the vote
   And number of emited votes in the user story
   But Nobody is able to see the value of the vote from another member.

   ======================================================================   
   URL: GET /user_stories/{user_story_id}/status
   Response:
   status: 200 OK
   body:
   emitted_votes (integer): the number of emitted votes for the user story
   voted_members (array): an array of members who have emitted a vote

   ==================================================================

7) Scenario: Finish voting a user story
   Given There is a user story in VOTING status
   When I push the button "Stop" of the user story
   Then the user story move to VOTED status
   And no more votes are accepted for the user story
   And the votes of all members are revealed
   And summary of voted values are shown in the user story

   =====================================================================
   URL: DELETE /user_stories/{user_story_id}/stop
   Response:
   status: 200 OK
   body:
   status (string): the status of the user story, which should be set to VOTED
   votes (array): an array of votes revealed for the user story
   summary (object): an object containing a summary of voted values for the user story

   ======================================================================

Feature: User stories management

8) Scenario: Add a user story
   Given I'm in as poker planning session
   And I want to add a user story
   When I fill user story Id and description
   Then The user story is added in the user story list

   ========================================================================
   Add a user story:
   URL: POST /user_stories
   Parameters:
   session_id (string, required): the ID of the session where the user story should be added
   user_story_id (string, required): the ID of the user story
   description (string, required): the description of the user story

Response:
status: 201 Created
body:
user_story_id (string): the ID of the added user story
   
=========================================================================

9) Scenario: Delete a user story
   Given I'm in as poker planning session
   And I want to delete a user story
   And the user story is PENDING
   When I push the option to delete the user story
   Then the user story disappears from the user story list

   =========================================================================
   Delete a user story:
   URL: DELETE /user_stories/{user_story_id}

   Request body: None

   Response:

   HTTP status code 204 No Content if the user story was successfully deleted.
   HTTP status code 404 Not Found if the user story with the specified ID was not found.
   =========================================================================

   questions:
   db relations?
   how to store user votes?
    * (Session - UserStory => 1-2-m) +
    * (Session - User => 1-2-m) +
    *  Pass userId in the jwt token +
    *  (UserStory -> User + Vote) +

    *  Vote entity (UserId and chosen option)? that have 1-2-m with UserStory


Entities:

     1) UserStory (UserStory - Votes => 1-2-m)
     2) Session   (Session - UserStory => 1-2-m), (Session - User => 1-2-m)
     3) User      (User- Votes => 1-2-m)
     4) Vote

Task 1.
Implement Entities;
Task 2
Repository and Service layer
Task 3 Representation layer
Task 4 Test Data, Postman collection
+++++++++++++++++++++++++++++++++++++++++++++++++++++++
Task 5 Mappers, Requests(With Validations), Responses, Swagger Spec
Taks 6 Unit tests
Task 7 Spec yaml
Task 8 Logging, Exception Handling, Readme file with flow