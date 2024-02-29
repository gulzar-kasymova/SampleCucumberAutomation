@etsy @etsyTitles @ui
Feature: Validating Etsy Titles
  Scenario Outline: Validating titles on each tab
    Given user navigates to etsy application
    When user clicks on "<Tab>" tab
    Then user validates the "<Title>"
    Examples:
      | Tab            | Title                                                   |
      | Shop Deals     | BEST deals—up to 40% off                                |
      | Home Favorites | Home Favorites                                          |
      | Fashion Finds  | Fashion Favorites                                       |
      | Gift Guides    | Gifts for Everyone: Find a Gift for Any Occasion - Etsy |
      | Registry       | Etsy Registry: Create or Find a Gift Registry - Etsy    |


