@regression @login @smartbear @ui
Feature: Login Functionality

  Background: Navigating to SmartBear application
    Given user navigates to smartbear application

  @loginPositive @ui
  Scenario: Validating login functionality with valid credentials
    When user logs in with username "Tester" and password "test"
    Then user is successfully logged in and the title is "Web Orders"

  @loginNegative @ui
  Scenario: Validating login functionality with invalid credentials
    When user logs in with username "Test" and password "tester"
    Then user is not logged in and sees the error message "Invalid Login or Password."

