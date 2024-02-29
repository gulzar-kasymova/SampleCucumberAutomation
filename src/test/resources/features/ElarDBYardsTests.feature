Feature: Yards database validation

  Background:
    Given user navigates to the elar Logistic application
    When user login with user name "student1@mindtekbootcamp.com" and password "mindtek109"
    And user clicks on Yards tab
    And user clicks on add yard button

  @regression @smoke @db @TC-100
  Scenario Outline: Add yard database validation
    And user creates yards with data
      | Name     | random      |
      | Street   | 123 abc st. |
      | City     | <City>      |
      | State    | <State>     |
      | Zip code | 60173       |
      | Spots    | 1123        |
    Then user validates success message "successfully created"
    And user validates yard is persisted in DB
    Examples:
      | City        | State |
      | Chicago     | IL    |
      | Los Angeles | CA    |




   Scenario: Edit yard database validation
     And user creates yards with data
       | Name     | random      |
       | Street   | 123 abc st. |
       | City     | Chicago      |
       | State    | IL    |
       | Zip code | 60173       |
       | Spots    | 1123        |
     And user goes to created yard page and clicks on edit button
     And user updates name with "random"
     Then user validates success message "successfully changed"
     And user validates yard is persisted in DB


