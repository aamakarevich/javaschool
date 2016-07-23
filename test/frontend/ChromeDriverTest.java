package frontend;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.time.LocalTime;
import java.time.temporal.ChronoField;

/**
 * @author Andrei Makarevich
 */
public class ChromeDriverTest {

    private static ChromeDriverService service;
    public static WebDriver driver;

    @BeforeClass
    public static void createAndStartService() throws IOException {
        service = new ChromeDriverService.Builder()
                .usingDriverExecutable(new File("chromedriver.exe"))
                .usingAnyFreePort()
                .build();
        service.start();
    }

    @Before
    public void setUp(){
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        driver = new ChromeDriver(service, options);
    }

    @Test
    public void demonstration() throws InterruptedException {
        driver.get("http://asusbook:8080/javaschool/");

        int begin = LocalTime.now().get(ChronoField.SECOND_OF_DAY);

//        waitForElement(By.id("nav_plans"));
//        Thread.sleep(2000);
//        hoverNavigationElement(By.id("nav_plans"), 2000);
//        clickElement(By.id("nav_plans"));
//
//        Thread.sleep(10000);
//
//        hoverNavigationElement(By.id("nav_options"), 2000);
//        Thread.sleep(2000);
//        clickElement(By.id("nav_options"));
//
//        Thread.sleep(10000);
//
//        clickElement(By.id("nav_login"));
//
//        waitForElement(By.id("inputEmail"));
//        writeSlowly(By.id("inputEmail"), "rweasle@ecare.com", 100);
//        writeSlowly(By.id("inputPassword"), "adminer", 50);
//        clickElement(By.id("loginSubmit"));
//        Thread.sleep(6000);
//        writeSlowly(By.id("inputPassword"), "admin", 50);
//        clickElement(By.id("loginSubmit"));
//
//        waitForElement(By.id("nav_fullname"));
//
//        Thread.sleep(5000);
//
//        hoverNavigationElement(By.id("nav_options"), 2000);
//        clickElement(By.id("nav_options"));
//
//        Thread.sleep(5000);
//
//        hoverNavigationElement(By.id("nav_profile"), 2000);
//        clickElement(By.id("nav_profile"));
//
//        Thread.sleep(10000);
//
//        hoverElement(By.id("lk1"), 2000);
//        clickElement(By.id("lk1"));
//        Thread.sleep(4000);
//        hoverElement(By.id("lk1"), 2000);
//        clickElement(By.id("lk1"));
//
//        waitForElement(By.id("trp1"));
//
//        Thread.sleep(3000);
//        hoverElement(By.id("trp1"), 1000);
//        clickElement(By.id("trp1"));
//
//        waitForElement(By.id("bSubmit"));
//
//        hoverElement(By.id("pl4"), 3000);
//        clickElement(By.id("pl4"));
//
//        hoverElement(By.id("pl1"), 3000);
//        clickElement(By.id("pl1"));
//
//        hoverElement(By.id("pl2"), 3000);
//        clickElement(By.id("pl2"));
//
//        hoverElement(By.id("bSubmit"), 3000);
//        clickElement(By.id("bSubmit"));
//
//        waitForElement(By.id("bSubmit"));
//
//        hoverElement(By.id("bChange"), 3000);
//        clickElement(By.id("bChange"));
//
//        waitForElement(By.id("bSubmit"));
//
//        hoverElement(By.id("pl3"), 3000);
//        clickElement(By.id("pl3"));
//
//        hoverElement(By.id("bSubmit"), 3000);
//        clickElement(By.id("bSubmit"));
//
//        waitForElement(By.id("bSubmit"));
//
//        hoverElement(By.id("op2"), 3000);
//        clickElement(By.id("op2"));
//
//        hoverElement(By.id("op3"), 3000);
//        clickElement(By.id("op3"));
//
//        hoverElement(By.id("op3"), 3000);
//        clickElement(By.id("op3"));
//
//        hoverElement(By.id("op4"), 3000);
//        clickElement(By.id("op4"));
//
//        hoverElement(By.id("op5"), 3000);
//        clickElement(By.id("op5"));
//
//        hoverElement(By.id("bSubmit"), 3000);
//        clickElement(By.id("bSubmit"));
//
//        hoverElement(By.id("signout"), 3000);
//        clickElement(By.id("signout"));

        // manager demo starts here

        waitForElement(By.id("nav_login"));

        hoverNavigationElement(By.id("nav_login"), 2000);
        clickElement(By.id("nav_login"));

        waitForElement(By.id("inputEmail"));
        writeSlowly(By.id("inputEmail"), "hpotter@ecare.com", 100);
        writeSlowly(By.id("inputPassword"), "admin", 50);
        clickElement(By.id("loginSubmit"));

        // demonstraiting plan stuff
        waitForElement(By.id("nav_fullname"));
        Thread.sleep(4000);
        hoverNavigationElement(By.id("nav_plans"), 2000);
        clickElement(By.id("nav_plans"));

        waitForElement(By.id("addNewPlan"));
        Thread.sleep(10000);
        clickElement(By.id("addNewPlan"));

        Thread.sleep(2000);
        waitForElement(By.id("bSubmit"));
        writeSlowly(By.id("inputTitle"), "Fresh plan", 100);
        writeSlowly(By.id("inputMonthlyFee"), "21", 100);

        hoverElement(By.id("bSubmit"), 2000);
        clickElement(By.id("bSubmit"));

        Thread.sleep(4000);
        writeSlowly(By.id("inputDescription"), "Perfect plan for wizards only. Muggles can just go away.", 50);
        hoverElement(By.id("bSubmit"), 2000);
        clickElement(By.id("bSubmit"));

        waitForElement(By.id("editPlan6"));
        hoverElement(By.id("editPlan6"), 4000);
        clickElement(By.id("editPlan6"));

        waitForElement(By.id("bSubmit"));
        Thread.sleep(2000);
        clearElement(By.id("inputTitle"));
        writeSlowly(By.id("inputTitle"), "Updated plan", 100);

        hoverElement(By.id("av1"), 1000);
        clickElement(By.id("av1"));

        hoverElement(By.id("av3"), 1000);
        clickElement(By.id("av3"));

        scrollToElement(By.id("av5"));
        hoverElement(By.id("av5"), 1000);
        clickElement(By.id("av5"));

        scrollToElement(By.id("av6"));
        hoverElement(By.id("av6"), 1000);
        clickElement(By.id("av6"));

        hoverElement(By.id("bSubmit"), 3000);
        clickElement(By.id("bSubmit"));

        waitForElement(By.id("editPlan6"));
        hoverElement(By.id("editPlan6"), 4000);
        clickElement(By.id("editPlan6"));

        waitForElement(By.id("bSubmit"));
        hoverElement(By.id("bCancel"), 4000);
        clickElement(By.id("bCancel"));

        waitForElement(By.id("deletePlan6"));
        hoverElement(By.id("deletePlan6"), 4000);
        clickElement(By.id("deletePlan6"));
        waitForElement(By.id("doDeletePlan"));
        hoverElement(By.id("doDeletePlan"), 2000);
        clickElement(By.id("doDeletePlan"));

        waitForElement(By.id("deletePlan5"));

        // demonstraiting option stuff
//        Thread.sleep(4000);
//        hoverNavigationElement(By.id("nav_options"), 2000);
//        clickElement(By.id("nav_options"));
//
//
//        waitForElement(By.id("addNewOption"));
//        Thread.sleep(2000);
//        hoverElement(By.id("addNewOption"), 2000);
//        clickElement(By.id("addNewOption"));
//
//        waitForElement(By.id("bSubmit"));
//        Thread.sleep(2000);
//        writeSlowly(By.id("inputTitle"), "New option", 100);
//        writeSlowly(By.id("inputAdditionFee"), "5000", 100);
//        writeSlowly(By.id("inputMonthlyFee"), "15", 100);
//        writeSlowly(By.id("inputDescription"), "Some information that makes a lot of sense.", 50);
//
//        hoverElement(By.id("bSubmit"), 2000);
//        clickElement(By.id("bSubmit"));
//
//        Thread.sleep(4000);
//
//        clearElement(By.id("inputAdditionFee"));
//        writeSlowly(By.id("inputAdditionFee"), "50", 100);
//        hoverElement(By.id("bSubmit"), 2000);
//        clickElement(By.id("bSubmit"));
//
//        waitForElement(By.id("editOption7"));
//        hoverElement(By.id("editOption7"), 4000);
//        clickElement(By.id("editOption7"));
//
//        waitForElement(By.id("bSubmit"));
//        hoverElement(By.id("bl1"), 1000);
//        clickElement(By.id("bl1"));
//        hoverElement(By.id("pr1"), 1000);
//        clickElement(By.id("pr1"));
//        hoverElement(By.id("pr2"), 1000);
//        clickElement(By.id("pr2"));
//        hoverElement(By.id("bl2"), 1000);
//        clickElement(By.id("bl2"));
//        hoverElement(By.id("bl3"), 1000);
//        clickElement(By.id("bl3"));
//        hoverElement(By.id("bl3"), 1000);
//        clickElement(By.id("bl3"));
//        hoverElement(By.id("pr3"), 1000);
//        clickElement(By.id("pr3"));
//        hoverElement(By.id("pr3"), 1000);
//        clickElement(By.id("pr3"));
//
//        hoverElement(By.id("bSubmit"), 2000);
//        clickElement(By.id("bSubmit"));
//
//        waitForElement(By.id("editOption7"));
//        hoverElement(By.id("deleteOption7"), 4000);
//        clickElement(By.id("deleteOption7"));
//
//        waitForElement(By.id("doDeleteOption"));
//        hoverElement(By.id("doDeleteOption"), 2000);
//        clickElement(By.id("doDeleteOption"));

        // demonstrating customers stuff
        Thread.sleep(4000);
        hoverNavigationElement(By.id("nav_users"), 2000);
        clickElement(By.id("nav_users"));

        waitForElement(By.id("doSearchCustomers"));
        Thread.sleep(10000);
        writeSlowly(By.id("users_search"), "Rowling", 100);
        clickElement(By.id("doSearchCustomers"));
        Thread.sleep(4000);

        scrollToElement(By.id("pag_2"));
        Thread.sleep(2000);
        hoverElement(By.id("pag_2"), 2000);
        clickElement(By.cssSelector("#pag_2 a"));

        Thread.sleep(2000);
        hoverElement(By.id("doReset"), 2000);
        clickElement(By.id("doReset"));

        Thread.sleep(2000);
        hoverElement(By.id("paging_change"), 2000);
        clickElement(By.id("paging_change"));
        hoverElement(By.id("20"), 2000);
        clickElement(By.id("20"));
        Thread.sleep(4000);
        scrollToElement(By.id("pagination"));



        Thread.sleep(10000);
        System.out.println((LocalTime.now().get(ChronoField.SECOND_OF_DAY) - begin) / 60d);
    }

    @After
    public void tearDown(){
        driver.quit();
    }

    @AfterClass
    public static void createAndStopService() {
        service.stop();
    }

    private static void clearElement(By by) {
        driver.findElement(by).clear();
    }

    private static void clickElement(By by) {
        driver.findElement(by).click();
    }

    private static void hoverElement(By by, long milliseconds) throws InterruptedException {
        Actions action = new Actions(driver);
        action.moveToElement(driver.findElement(by)).perform();
        Thread.sleep(milliseconds);
    }

    private static void hoverNavigationElement(By by, long milliseconds) throws InterruptedException {
        Actions action = new Actions(driver);
        action.clickAndHold(driver.findElement(by)).perform();
        Thread.sleep(milliseconds);
    }

    private static void scrollToElement(By by) {
        ((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView();", driver.findElement(by));
    }

    private static void waitForElement(By by) {
        (new WebDriverWait(driver, 10)).until(ExpectedConditions.elementToBeClickable(by));
    }

    private static void writeSlowly(By by, String value, long milliseconds) throws InterruptedException {
        WebElement element = driver.findElement(by);
        for (char c : value.toCharArray()) {
            element.sendKeys(String.valueOf(c));
            Thread.sleep(milliseconds);
        }
    }
}
