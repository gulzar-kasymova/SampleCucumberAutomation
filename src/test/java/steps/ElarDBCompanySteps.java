package steps;

import io.cucumber.java.en.Then;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;
import pages.ElarAppAddCompanyPage;
import utilities.ConfigReader;
import utilities.Driver;
import utilities.JDBCUtils;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class ElarDBCompanySteps {
    WebDriver driver = Driver.getDriver();
    ElarAppAddCompanyPage elarAppAddCompanyPage = new ElarAppAddCompanyPage();
    Map<String,Object> compnayData;
    Map<String,Object> compnayEditData;

    @Then("user creates company with data")
    public void user_creates_company_with_data(io.cucumber.datatable.DataTable dataTable) throws InterruptedException {
        compnayData = dataTable.asMap(String.class,Object.class);
        elarAppAddCompanyPage.nameField.sendKeys(compnayData.get("Name").toString());
        Integer randomMC = new Random().nextInt(666666);
        elarAppAddCompanyPage.mcNumberField.sendKeys(randomMC.toString());
        Thread.sleep(1000);
        Integer randomDOT = new Random().nextInt(999999999);
        elarAppAddCompanyPage.dotNumberField.sendKeys(randomDOT.toString());
        Thread.sleep(1000);
        elarAppAddCompanyPage.phoneNumberField.sendKeys(compnayData.get("Phone number").toString());
        elarAppAddCompanyPage.streetField.sendKeys(compnayData.get("Street").toString());
        elarAppAddCompanyPage.cityField.sendKeys(compnayData.get("City").toString());
        Select select = new Select(elarAppAddCompanyPage.stateDropdown);
        select.selectByVisibleText(compnayData.get("State").toString());
        elarAppAddCompanyPage.zip.sendKeys(compnayData.get("Zip code").toString());
        elarAppAddCompanyPage.email.sendKeys(compnayData.get("Email").toString());
        elarAppAddCompanyPage.insurance.sendKeys(compnayData.get("Insurance(producer company name)").toString());
        elarAppAddCompanyPage.policyExpDate.click();
        Thread.sleep(1000);
        elarAppAddCompanyPage.feb22.click();
        elarAppAddCompanyPage.policyExpDate.click();
        elarAppAddCompanyPage.policyNum.sendKeys(compnayData.get("Policy number").toString());
        elarAppAddCompanyPage.addCompanyBtn.click();

    }


    @Then("user validates success message of creating {string}")
    public void user_validates_success_message_of_creating(String succesMessage) {
        Assert.assertEquals(compnayData.get("Name")+"\n"+succesMessage, elarAppAddCompanyPage.successAddedMsg.getText());
    }
    @Then("user validates created company persists in DB")
    public void user_validates_created_company_persists_in_db() throws SQLException {
        JDBCUtils.establishDBConnection(
                ConfigReader.getProperty("ElarDBURL"),
                ConfigReader.getProperty("ElarDBUsername"),
                ConfigReader.getProperty("ElarDBPassword")
        );
        List<Map<String,Object>> DBData = JDBCUtils.executeQuery("select * from core_company where company_name = 'MindtekOnline'");
        Assert.assertEquals(compnayData.get("Name"), DBData.get(0).get("company_name"));
    }

    @Then("user clicks on {string} button")
    public void user_clicks_on_button(String string) {
        elarAppAddCompanyPage.goToCurrentProfileCompanyBtn.click();
    }
    @Then("user edits created company with data")
    public void user_edits_created_company_with_data(io.cucumber.datatable.DataTable dataTable) {
        compnayEditData = dataTable.asMap(String.class,Object.class);
        elarAppAddCompanyPage.editBtn.click();
        elarAppAddCompanyPage.nameField.clear();
        elarAppAddCompanyPage.nameField.sendKeys(compnayEditData.get("Name").toString());
        elarAppAddCompanyPage.email.clear();
        elarAppAddCompanyPage.email.sendKeys(compnayEditData.get("Email").toString());
        elarAppAddCompanyPage.saveBtn.click();
    }


    @Then("user validates success message of editing {string}")
    public void user_validates_success_message_of_editing(String editSuccessMessage) {
        Assert.assertEquals(compnayEditData.get("Name")+"\n"+ editSuccessMessage, elarAppAddCompanyPage.successAddedMsg.getText());
    }
    @Then("user validates edited company is updated in DB")
    public void user_validates_edited_company_is_updated_in_db() throws SQLException {
        JDBCUtils.establishDBConnection(
                ConfigReader.getProperty("ElarDBURL"),
                ConfigReader.getProperty("ElarDBUsername"),
                ConfigReader.getProperty("ElarDBPassword")
        );
        String query = "select * from core_company where company_name =" + "'" + compnayEditData.get("Name") + "'";
        System.out.println(query);
        List<Map<String,Object>> DBData = JDBCUtils.executeQuery(query);
        Assert.assertEquals(compnayEditData.get("Name"), DBData.get(0).get("company_name"));
    }
}
