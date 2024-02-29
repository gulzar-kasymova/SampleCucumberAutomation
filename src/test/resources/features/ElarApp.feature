@elarLogistics @addCompany
Feature: Validating adding the company

  Background: Navigation to the ElarLogisticsApp  and add company page
    Given user navigates to the elar Logistic application
    When user login with user name "student1@mindtekbootcamp.com" and password "mindtek109"
    Then user clicks on Bag Icon button
    And user clicks on + Add Company button


  @TC-0074 @ui
  Scenario Outline: Validating the "MC #" field is accepting digits
    When user inputs <userDigitInput> digits
    Then user validating digits is <userDigitInputExpectedResult>
    Examples:
      | userDigitInput | userDigitInputExpectedResult |
  #    | 12345678902    | 1234567890                   |
      | 1234           | 1234                         |
      | 1234567890     | 1234567890                   |
      | 12345678       | 12345678                     |


   @TC-0075 @ui
   Scenario: Validating "MC #" field's error message while providing less than min digits
     When user inputs 123 digits
     Then user validates error message is "Min length is 4 characters, currently it is 3"

   @TC-0076 @ui
   Scenario: Validating "MC #" field is required
     When user inputs 1 digits
     And user clears the field
     Then user validates error message is "This field is required."

  @TC-00781 @ui
  Scenario: Validating letters are not valid characters for "MC #" field
    When user inputs "abdcgh"
    Then user validates error message is "Enter only digits."
