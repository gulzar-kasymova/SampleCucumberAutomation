package steps;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import pages.SmartBearLoginPage;
import pages.SmartBearMainPage;
import pages.SmartBearOrderPage;
import utilities.BrowserUtils;
import utilities.ConfigReader;
import utilities.Driver;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SmartBearSteps {

    WebDriver driver = Driver.getDriver();
    SmartBearLoginPage smartBearLoginPage = new SmartBearLoginPage();
    SmartBearOrderPage smartBearOrderPage = new SmartBearOrderPage();
    SmartBearMainPage smartBearMainPage = new SmartBearMainPage();
    List<Map<String, Object>> data = new ArrayList<>();

    @Given("user navigates to smartbear application")
    public void user_navigates_to_smartbear_application() {
        driver.get(ConfigReader.getProperty("smartbearUrl"));
    }

    @When("user logs in with username {string} and password {string}")
    public void user_logs_in_with_username_and_password(String username, String password) {
        smartBearLoginPage.loginInput.sendKeys(username);
        smartBearLoginPage.passwordInput.sendKeys(password);
        smartBearLoginPage.loginBtn.click();
    }
    @Then("user is successfully logged in and the title is {string}")
    public void user_is_successfully_logged_in_and_the_title_is(String title) {
        Assert.assertEquals(title, driver.getTitle());
    }

    @Then("user is not logged in and sees the error message {string}")
    public void user_is_not_logged_in_and_sees_the_error_message(String errorMessage) {
        Assert.assertEquals(errorMessage, smartBearLoginPage.errorMessage.getText());
    }



    @Given("user clicks on Order tab")
    public void user_clicks_on_order_tab() {
        smartBearMainPage.orderTab.click();
    }

    @When("user selects product {string} and quantity {int}")
    public void user_selects_product_and_quantity(String product, Integer quantity) {
        BrowserUtils.selectOptionByValue(smartBearOrderPage.productDropdown,product);
        smartBearOrderPage.quantityInput.sendKeys(quantity.toString());
        smartBearOrderPage.calculateBtn.click();

    }

    @Then("user validates the price is calculated correctly for {int}")
    public void user_validates_the_price_is_calculated_correctly_for(Integer quantity) throws IOException {
        int pricePerUnit = Integer.parseInt(smartBearOrderPage.pricePerUnit.getAttribute("value"));
        int discount = Integer.parseInt(smartBearOrderPage.discount.getAttribute("value"));
        int actualTotal = Integer.parseInt(smartBearOrderPage.totalAmount.getAttribute("value"));
        int expectedTotal;

        if(quantity>=10) expectedTotal = (pricePerUnit - (pricePerUnit * discount /100)) * quantity;
        else expectedTotal = (quantity*pricePerUnit);
        BrowserUtils.takeScreenshot("discountCalculator");
        Assert.assertEquals(expectedTotal,actualTotal);
    }

    @When("user places an orrder with data and validates with success message {string}")
    public void user_places_an_orrder_with_data_and_validates_with_success_message(String successMsg, DataTable dataTable) {
        data = dataTable.asMaps(String.class, Object.class);

        for(int i=0; i<data.size(); i++){
            BrowserUtils.selectOptionByValue(smartBearOrderPage.productDropdown, data.get(i).get("PRODUCT").toString());
            smartBearOrderPage.quantityInput.sendKeys(Keys.BACK_SPACE + data.get(i).get("QUANTITY").toString());
            smartBearOrderPage.customerNameInput.sendKeys(data.get(i).get("CUSTOMER NAME").toString());
            smartBearOrderPage.streetInput.sendKeys(data.get(i).get("STREET").toString());
            smartBearOrderPage.cityInput.sendKeys(data.get(i).get("CITY").toString());
            smartBearOrderPage.stateInput.sendKeys(data.get(i).get("STATE").toString());
            smartBearOrderPage.zipCodeInput.sendKeys(data.get(i).get("ZIP").toString());
            switch (data.get(i).get("CARD").toString()) {
                case "Visa" -> smartBearOrderPage.visaCardBtn.click();
                case "AmEx" -> smartBearOrderPage.americanExpressCardBtn.click();
                case "MasterCard" -> smartBearOrderPage.masterCardBtn.click();
            }
            smartBearOrderPage.cardNumberInput.sendKeys(data.get(i).get("CARD NUM").toString());
            smartBearOrderPage.expirDateInput.sendKeys(data.get(i).get("EXP DATE").toString());
            smartBearOrderPage.processBtn.click();
            Assert.assertEquals(successMsg, smartBearOrderPage.actualSuccessMsg.getText());


        }


    }

    @Then("user validates created order is in the list of all orders")
    public void user_validates_created_order_is_in_the_list_of_all_orders() {
        smartBearOrderPage.viewOrdersBtn.click();
        Assert.assertEquals(data.get(2).get("CUSTOMER NAME").toString(), smartBearMainPage.firstRowCustomerName.getText());


    }


}
