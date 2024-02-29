package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utilities.Driver;

public class PorcheAppPage {

    WebDriver driver;

    public PorcheAppPage(){
        driver = Driver.getDriver();
        PageFactory.initElements(driver,this);
    }

    @FindBy(xpath = "//a[@data-testid='card-link-description-982120']/div/div/p-text")
    public WebElement basePrice;

    @FindBy(xpath = "//div[@class='text-md text-right']")
    public WebElement listedPrice;

    @FindBy(id = "model-type-arrow-icon-to-configurator-CAYMAN_982120")
    public WebElement selectPorcheBtn;

    @FindBy(xpath = "//input[@value='P06']")
    public WebElement addPowerSeatsBtn;

    @FindBy(xpath = "//div[@id='category-IIR']/div/div[@class='gap-xs flex flex-col pl-1 last:mb-0'][1]/div/div/div[2]/label/div/p[2]")
    public WebElement powerSeatPrice;




}
