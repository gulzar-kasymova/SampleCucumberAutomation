package utilities;

import io.cucumber.java.en_old.Ac;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class BrowserUtils {


    /**
     * This method generates a random email using
     * UUID.
     * Example:
     *      .getRandomEmail() -> returns -> username-123gcshcbj-156ghbchskajk-311ca@gmail.com
     * @return String
     */

    public static String getRandomEmail(){
        UUID uuid = UUID.randomUUID();
        return "username"+uuid+"@gmail.com";
    }

    /**
     * This method creates a Select object and selects an option by value
     * @param target
     * @param value
     */

    public static void selectOptionByValue(WebElement target, String value){
        Select select = new Select(target);
        select.selectByValue(value);
    }

    /**
     * This method waits for element to be clickable.
     * @param element
     */
    public static void waitForElementToBeClickable(WebElement element){
        WebDriverWait wait = new WebDriverWait(Driver.getDriver(), 15);
        wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    /**
     * This method waits for text to be present in the webelement.
     * @param target
     * @param text
     */
    public static void waitForTextToBePresent(WebElement target, String text){
        WebDriverWait wait = new WebDriverWait(Driver.getDriver(),15);
        wait.until(ExpectedConditions.textToBePresentInElement(target, text));
    }

    /**
     * This method scrolls the window
     * @param pixels
     */
    public static void scrollByPixels(Integer pixels){
        JavascriptExecutor jse = ((JavascriptExecutor) Driver.getDriver());
        jse.executeScript("window.scrollBy(0,"+pixels+");");
    }

    /**
     * This method moves the cursor to target WebElement and clicks on it
     * @param target
     */

    public static void moveCursorToElement(WebElement target){
        Actions actions = new Actions(Driver.getDriver());
        actions.moveToElement(target).click().perform();
    }

    /**
     * This method takes screenshots of web ui
     * @param testName
     */

    public static void takeScreenshot(String testName) throws IOException {
        WebDriver driver = Driver.getDriver();
        File screenshot = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
        String path = "src\\test\\resources\\screenshots\\" + testName +".png";
        File file = new File(path);
        FileUtils.copyFile(screenshot,file);

    }


}
