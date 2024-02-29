package steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import org.junit.Assert;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.PorcheAppPage;
import utilities.Driver;


public class PorcheAppSteps {

    WebDriver driver = Driver.getDriver();
    PorcheAppPage porcheAppPage = new PorcheAppPage();
    int basePrice;
    int listedPrice;

    @Given("user navigates to {string}")
    public void user_navigates_to(String url) {
        driver.get(url);

    }

    @When("user stores the price and selects the model {int} Cayman")
    public void user_stores_the_price_and_selects_the_model_cayman(Integer int1) {

        WebDriverWait wait = new WebDriverWait(driver,10);
        wait.until(ExpectedConditions.visibilityOf(porcheAppPage.basePrice));
        basePrice = Integer.parseInt(porcheAppPage.basePrice.getText().replace("From $", "").replace("*", "").replace(",",""));
        porcheAppPage.selectPorcheBtn.click();
        listedPrice=Integer.parseInt(porcheAppPage.listedPrice.getText().replace("$","").replace(",",""));
    }
    @Then("user validates Base price is matched with listed price")
    public void user_validates_base_price_is_matched_with_listed_price() {
        Assert.assertEquals(basePrice,listedPrice);
    }



    @When("user adds Power Sport Seats \\(forteen-way) with Memory Package")
    public void user_adds_power_sport_seats_forteen_way_with_memory_package() throws InterruptedException {
        JavascriptExecutor jse = (JavascriptExecutor) driver;
        jse.executeScript("window.scrollBy(0,7000)");
        WebDriverWait wait = new WebDriverWait(driver,30);
        wait.until(ExpectedConditions.elementToBeClickable(porcheAppPage.addPowerSeatsBtn)).click();

        Thread.sleep(3000);  // needs time to renew the listed price
    }


    @Then("user validates that Price For Equipment is added and price matches")
    public void user_validates_that_price_for_equipment_is_added_and_price_matches() {
        int newListedPrice = Integer.parseInt(porcheAppPage.listedPrice.getText().replace("$","").replace(",",""));
        int powerSeatPrice = Integer.parseInt(porcheAppPage.powerSeatPrice.getText().replace("$","").replace(",",""));
        Assert.assertEquals(listedPrice+powerSeatPrice, newListedPrice);

    }




}
