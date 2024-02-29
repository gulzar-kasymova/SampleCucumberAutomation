package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utilities.Driver;

import java.util.List;

public class EtsyAppMainPage {
    WebDriver driver;

    public EtsyAppMainPage(){
        driver= Driver.getDriver();
        PageFactory.initElements(driver, this);
    }

    @FindBy(id = "global-enhancements-search-query")
    public WebElement searchBar;

    @FindBy(xpath = "//div[@class='wt-grid wt-pl-xs-0 wt-pr-xs-0 search-listings-group']//h3")
    public List<WebElement> items;

    @FindBy(id="search-filter-button")
    public WebElement allFilterBtn;

    @FindBy(id="price-input-4")
    public WebElement overThousandBtn;

    @FindBy(xpath = "//button[@aria-label='Show results (1,000+ items)']")
    public WebElement showResultsBtn;

    @FindBy(xpath = "//ol[@data-results-grid-container]//p[1]//span[@class='currency-value']")
    public List<WebElement> itemPrices;

    @FindBy(xpath = "(//li[@class='wt-mr-xs-3'])[1]")
    public WebElement shopDealsTab;

    @FindBy(xpath = "(//li[@class='wt-mr-xs-3'])[2]")
    public WebElement homeFavoritesTab;

    @FindBy(xpath = "(//li[@class='wt-mr-xs-3'])[3]")
    public WebElement fashionFindsTab;

    @FindBy(xpath = "(//li[@class='wt-mr-xs-3'])[4]")
    public WebElement giftGuidesTab;

    @FindBy(xpath = "(//li[@class='wt-mr-xs-3'])[5]")
    public WebElement registryTab;

}
