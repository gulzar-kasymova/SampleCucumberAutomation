@regression @smartbear @orderCreation @ui
Feature: Validating Oreder Creation

  Background: Order creation setup
    Given user navigates to smartbear application
    And user logs in with username "Tester" and password "test"
    And user clicks on Order tab

  @discountCalculator @ui
  Scenario Outline: Applying discount to total amount
    When user selects product "<Product>" and quantity <Quantity>
    Then user validates the price is calculated correctly for <Quantity>
    Examples:
      | Product     | Quantity |
      | MyMoney     | 9        |
      | FamilyAlbum | 10       |
      | ScreenSaver | 11       |


    @placeOrder @ui
    Scenario: Placing an order and validating
      When user places an orrder with data and validates with success message "New order has been successfully added."
        | PRODUCT     | QUANTITY | CUSTOMER NAME | STREET      | CITY     | STATE | ZIP   | CARD       | CARD NUM  | EXP DATE |
        | MyMoney     | 5        | John Doe      | 123 Abc st. | Chicago  | IL    | 60655 | Visa       | 123456789 | 12/25    |
        | FamilyAlbum | 10       | Harsh Patel   | 456 Qwe st. | New York | NY    | 54321 | AmEx       | 123456789 | 03/26    |
        | ScreenSaver | 12       | Lisa Morgan   | 567 Zxc st. | Houston  | Texas | 65432 | MasterCard | 123456789 | 02/25    |

      Then user validates created order is in the list of all orders