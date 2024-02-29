@etsy @etsySearch @ui
Feature: Etsy Search Functionality

  # Background runs steps before every scenario
  Background: Etsy setup
    Given user navigates to etsy application
    When user searches for keyword "carpet"

  @etsySearchValidation @ui
  Scenario: Validating search result contains searched item

    Then user validates search result contains
      | potato | banana |
      | tomato | pear   |
    # The values between pipes are in DataTable

  @etsyPriceRange @ui
  Scenario: Validating price range filter for searched item
    And user selects price range over thousand dollars
    Then user validates price range for items over 1000.00