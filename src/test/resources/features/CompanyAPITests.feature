Feature: Validating Edit Company API Call

  Scenario: Validating create company request with POST API Call
    Given user creates company with POST api call with data
      | Name                             | Patel Patel 123 |
      | MC#                              | random          |
      | DOT#                             | random          |
      | Phone number                     | 5559999999      |
      | Street                           | 123 ABC         |
      | City                             | Chicago         |
      | State                            | IL              |
      | Zip code                         | 60655           |
    When user validates company is created with GET API call
    And user validates get call response having company details
    Then updates created company with PATCH api call with data
      | Name     | API Edited |
      | Street   | 456 DEE            |
    And user validates get call response having company details