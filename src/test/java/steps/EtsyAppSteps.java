package steps;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import org.junit.Assert;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import pages.EtsyAppMainPage;
import utilities.ConfigReader;
import utilities.Driver;

import java.util.List;

public class EtsyAppSteps {
    WebDriver driver = Driver.getDriver();
    EtsyAppMainPage etsyAppMainPage = new EtsyAppMainPage();

    @Given("user navigates to etsy application")
    public void user_navigates_to_etsy_application() {
       driver.get(ConfigReader.getProperty("etsyUrl"));
    }

    @When("user searches for keyword {string}")
    public void user_searches_for_keyword(String keyword) {
        etsyAppMainPage.searchBar.sendKeys(keyword+ Keys.ENTER);
    }

    @Then("user validates search result contains")
    public void user_validates_search_result_contains(DataTable dataTable) {
        List<String> keyword = dataTable.asList();

        for (WebElement item : etsyAppMainPage.items){
            boolean isFound=false;
            String wordNotFound = "";
            String itemDescription = item.getText();
            for( int i=0; i<keyword.size(); i++){
                if(itemDescription.toLowerCase().contains(keyword.get(i))){
                    isFound=true;
                    System.out.println(keyword.get(i));
                } else {
                    wordNotFound = keyword.get(i);
                    System.out.println(keyword.get(i) + " else statement");
                }
            }
            Assert.assertTrue("[" + itemDescription + "] does not contain keyword: [" + wordNotFound+"]",isFound);
        }

    }


    @When("user selects price range over thousand dollars")
    public void user_selects_price_range_over_thousand_dollars() throws InterruptedException {
        etsyAppMainPage.allFilterBtn.click();
        Actions actions = new Actions(driver);
        actions.moveToElement(etsyAppMainPage.overThousandBtn).click().perform();
        etsyAppMainPage.showResultsBtn.click();
        Thread.sleep(2000);
    }

    @Then("user validates price range for items over {double}")
    public void user_validates_price_range_for_items_over(Double priceRange) {
        driver.navigate().refresh();
        for (WebElement itemPrice : etsyAppMainPage.itemPrices){
            String itemPrice1 = itemPrice.getText().replace(",","");
            System.out.println(itemPrice1);
            double priceDouble = Double.parseDouble(itemPrice1);
            Assert.assertTrue(priceDouble>=priceRange);
        }

    }

    @When("user clicks on {string} tab")
    public void user_clicks_on_tab(String tab) {
        switch (tab) {
            case "Shop Deals" -> etsyAppMainPage.shopDealsTab.click();
            case "Home Favorites" -> etsyAppMainPage.homeFavoritesTab.click();
            case "Fashion Finds" -> etsyAppMainPage.fashionFindsTab.click();
            case "Gift Guides" -> etsyAppMainPage.giftGuidesTab.click();
            case "Registry" -> etsyAppMainPage.registryTab.click();
        }

    }

    @Then("user validates the {string}")
    public void user_validates_the(String title) {
        Assert.assertEquals("Title is incorrect",title, driver.getTitle());

    }
}
