package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utilities.Driver;

public class ElarAppCompnaiesAddPage {

    public ElarAppCompnaiesAddPage(){
        WebDriver driver = Driver.getDriver();
        PageFactory.initElements(driver,this);
    }

    @FindBy(xpath = "(//div[@class='form-content']/div[2]/p/span/span/input)[1]")
    public WebElement MCInputFiled;

    @FindBy(xpath = "(//div[@class='form-content']/div[2]/p/span/span/input)[2]")
    public WebElement DOTInputFiled;

    @FindBy(xpath = "//span[@class='input-errors']")
    public WebElement actualErrorMessage;
}
