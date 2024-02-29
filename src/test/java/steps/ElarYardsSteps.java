package steps;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;
import pages.ElarAppHomePage;
import pages.ElarAppYardsPage;
import utilities.ConfigReader;
import utilities.Driver;
import utilities.JDBCUtils;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class ElarYardsSteps {

    WebDriver driver = Driver.getDriver();
    ElarAppHomePage elarAppHomePage = new ElarAppHomePage();
    ElarAppYardsPage elarAppYardsPage = new ElarAppYardsPage();
    Map<String,Object> yardData;
    String yardName;

    @When("user clicks on Yards tab")
    public void user_clicks_on_yards_tab() {
        elarAppHomePage.yardsTab.click();
    }

    @When("user clicks on add yard button")
    public void user_clicks_on_add_yard_button() {
        elarAppYardsPage.addYardButton.click();
    }

    @When("user creates yards with data")
    public void user_creates_yards_with_data(io.cucumber.datatable.DataTable dataTable) {
        yardData=dataTable.asMap(String.class,Object.class);
        yardName=yardData.get("Name").toString() + new Random().nextInt(100000);
        elarAppYardsPage.name.sendKeys(yardName);
        elarAppYardsPage.address.sendKeys(yardData.get("Street").toString());
        elarAppYardsPage.city.sendKeys(yardData.get("City").toString());
        Select select = new Select(elarAppYardsPage.state);
        select.selectByValue(yardData.get("State").toString());
        elarAppYardsPage.zipCode.sendKeys(yardData.get("Zip code").toString());
        elarAppYardsPage.spots.sendKeys(yardData.get("Spots").toString());
        elarAppYardsPage.addButton.click();
    }

    @Then("user validates success message {string}")
    public void user_validates_success_message(String successMessage) {
       Assert.assertEquals(yardName+"\n"+successMessage, elarAppYardsPage.successMessage.getText());
    }

    @Then("user validates yard is persisted in DB")
    public void user_validates_yard_is_persisted_in_db() throws SQLException {
        JDBCUtils.establishDBConnection(
                ConfigReader.getProperty("ElarDBURL"),
                ConfigReader.getProperty("ElarDBUsername"),
                ConfigReader.getProperty("ElarDBPassword")
        );
        List<Map<String, Object>> dataFromDB = JDBCUtils.executeQuery("select * from core_yard where location='"+yardName+"'");

        Assert.assertEquals(yardName, dataFromDB.get(0).get("location"));
        Assert.assertEquals(yardData.get("Street").toString(), dataFromDB.get(0).get("address"));
        Assert.assertEquals(yardData.get("City").toString(), dataFromDB.get(0).get("city"));
        Assert.assertEquals(yardData.get("State").toString(), dataFromDB.get(0).get("state"));
        Assert.assertEquals(yardData.get("Zip code").toString(), dataFromDB.get(0).get("zip_code"));
        Assert.assertEquals(yardData.get("Spots").toString(), dataFromDB.get(0).get("spots"));
    }

    @When("user goes to created yard page and clicks on edit button")
    public void user_goes_to_created_yard_page_and_clicks_on_edit_button() {
        elarAppYardsPage.goToCurrentPageButton.click();
        elarAppYardsPage.editButton.click();

    }

    @When("user updates name with {string}")
    public void user_updates_name_with(String name) throws InterruptedException {
        Thread.sleep(5000);
        yardName=name+new Random().nextInt(100000);
        elarAppYardsPage.name.clear();
        elarAppYardsPage.name.sendKeys(yardName);
        elarAppYardsPage.saveButton.click();
    }

}
