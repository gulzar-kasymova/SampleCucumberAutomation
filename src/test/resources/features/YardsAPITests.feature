Feature: Validating Yards API calls

  @regression @smoke @api
  Scenario: Validating post yard api call
    Given user creates yard with post yard api call with data
      | name     | Gulzar Yard 7 |
      | address  | 123 ABC st    |
      | city     | Chicago       |
      | state    | IL            |
      | zip_code | 60555         |
      | spots    | 500           |
    When user gets created yard with get api call
    Then user validates get call response having right yard details


  @regression @smoke @api @TC-210
  Scenario:  Validating patch yard api call
    Given user creates yard with post yard api call with data
      | name     | Gulzar Yard 7 |
      | address  | 123 ABC st    |
      | city     | Chicago       |
      | state    | IL            |
      | zip_code | 60555         |
      | spots    | 500           |
    When user updates created yatd with patch api call with data
      | name  | Mindtek Yard 10 |
      | city  | Miami           |
      | state | IL              |
    And user gets created yard with get api call
    Then user validates get call response having updated yard details
    And user validates data is updated in DB


  @regression @smoke @api @TC-211
  Scenario Outline: Validating post yard api call - negative scenario
    Given user creates yard with post yard api call with data
      | name     | <name>     |
      | address  | 123 ABC st |
      | city     | Chicago    |
      | state    | IL         |
      | zip_code | 60555      |
      | spots    | 500        |
    Then user validates status code 400
    And user validates "<errorMessage>" error message
    And user validates that yard is not persisted in DB
    Examples:
      | name                                                                  | errorMessage                                                     |
      | abc!                                                                  | Enter the correct data (leters, digits and '- & \| . ()' symbols |
      | bcsbcakcKCNLKACNSJCBDAHVBDVIBDVAkncsdjvdjvbvbjdvbskddjskdknjidhiuhuih | Ensure this field has no more than 50 characters.                |


  @regression @api @TC-212
  Scenario Outline: Validating query parameters of Get Yards api call
    Given user creates 20 yards if there is less than 20 yards in database
    When user gets yards with get api call with query parameters
      | status   | <status>   |
      | ordering | <ordering> |
      | offset   | <offset>   |
      | limit    | <limit>    |
    Then user validates api response having right data
    And user validates data with DB
    Examples:
      | status     | ordering | offset | limit |
      | active     | -id      | 15     | 2     |
      | active     | -id      | 1      | 20    |
      | active     | id       | 3      | 4     |
      | non-active | -id      | 0      | 1     |


  Scenario: Validating query parameters of Get Yards api call - negative scenario(offset is more than number of yards)
    Given user gets total number of yards
    When user gets yards with query parameter offset more than total yards
    Then user validates 0 number of yards in response


  Scenario: Validating query parameters of Get Yards api call - negative scenario(with offset negative number)
    Given user creates 20 yards if there is less than 20 yards in database
    When user sends get yards api call with negative number -5 for query param offset
    Then user validates "offset can not be negative number" query parameter error message

  Scenario: Validating get yards api call - negative scenario (getting non existing yard)
    Given user checks what yard id doesn't exist in DB
    When user sends get call for non existing yard
    Then user validates status code 404





