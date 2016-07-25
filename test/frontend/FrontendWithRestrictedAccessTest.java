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

/**
 * Frontend testing.
 */
public class FrontendWithRestrictedAccessTest {

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
    public void unauthorized() throws InterruptedException {
        driver.get("http://asusbook:8080/javaschool/");

        waitForElement(By.id("nav_plans"));
        clickElement(By.id("nav_plans"));

        waitForElement(By.id("nav_options"));
        clickElement(By.id("nav_options"));

        waitForElement(By.id("nav_login"));
        clickElement(By.id("nav_login"));

        waitForElement(By.id("loginCancel"));
        clickElement(By.id("loginCancel"));
    }

    @Test
    public void authorizedUser() throws InterruptedException {
        driver.get("http://asusbook:8080/javaschool/");

        waitForElement(By.id("nav_login"));
        clickElement(By.id("nav_login"));

        waitForElement(By.id("inputEmail"));
        writeSlowly(By.id("inputEmail"), "rweasle@ecare.com", 100);
        writeSlowly(By.id("inputPassword"), "adminer", 50);
        clickElement(By.id("loginSubmit"));
        waitForElement(By.id("inputPassword"));
        writeSlowly(By.id("inputPassword"), "admin", 50);
        clickElement(By.id("loginSubmit"));

        waitForElement(By.id("nav_plans"));
        clickElement(By.id("nav_plans"));

        waitForElement(By.id("nav_options"));
        clickElement(By.id("nav_options"));

        waitForElement(By.id("nav_profile"));
        clickElement(By.id("nav_profile"));

        waitForElement(By.id("lk1"));
        clickElement(By.id("lk1"));
        waitForElement(By.id("lk1"));
        clickElement(By.id("lk1"));

        waitForElement(By.id("trp1"));
        clickElement(By.id("trp1"));

        waitForElement(By.id("bSubmit"));

        clickElement(By.id("pl4"));
        clickElement(By.id("pl1"));
        clickElement(By.id("pl2"));
        clickElement(By.id("bSubmit"));
        waitForElement(By.id("bSubmit"));
        clickElement(By.id("bChange"));

        waitForElement(By.id("bSubmit"));
        clickElement(By.id("pl3"));
        clickElement(By.id("bSubmit"));

        waitForElement(By.id("op2"));
        clickElement(By.id("op2"));

        Thread.sleep(200);
        waitForElement(By.id("op3"));
        clickElement(By.id("op3"));

        Thread.sleep(200);
        waitForElement(By.id("op3"));
        clickElement(By.id("op3"));

        Thread.sleep(200);
        waitForElement(By.id("op4"));
        clickElement(By.id("op4"));

        Thread.sleep(200);
        waitForElement(By.id("op5"));
        clickElement(By.id("op5"));

        waitForElement(By.id("bSubmit"));
        clickElement(By.id("bSubmit"));

        waitForElement(By.id("signout"));
        clickElement(By.id("signout"));
    }

    @Test
    public void authorizedManager() throws InterruptedException {
        driver.get("http://asusbook:8080/javaschool/");

        waitForElement(By.id("nav_login"));
        hoverNavigationElement(By.id("nav_login"), 2000);
        clickElement(By.id("nav_login"));

        waitForElement(By.id("inputEmail"));
        writeSlowly(By.id("inputEmail"), "hpotter@ecare.com", 100);
        writeSlowly(By.id("inputPassword"), "admin", 50);
        clickElement(By.id("loginSubmit"));

        // demonstraiting plan stuff
        waitForElement(By.id("nav_plans"));
        clickElement(By.id("nav_plans"));

        waitForElement(By.id("addNewPlan"));
        clickElement(By.id("addNewPlan"));

        waitForElement(By.id("bSubmit"));
        writeSlowly(By.id("inputTitle"), "Fresh plan", 100);
        writeSlowly(By.id("inputMonthlyFee"), "21", 100);

        clickElement(By.id("bSubmit"));

        waitForElement(By.id("bSubmit"));
        writeSlowly(By.id("inputDescription"), "Perfect plan for wizards only. Muggles can just go away.", 50);
        clickElement(By.id("bSubmit"));

        waitForElement(By.id("editPlan6"));
        clickElement(By.id("editPlan6"));

        waitForElement(By.id("bSubmit"));
        clearElement(By.id("inputTitle"));
        writeSlowly(By.id("inputTitle"), "Updated plan", 100);
        clickElement(By.id("av1"));
        clickElement(By.id("av3"));

        scrollToElement(By.id("av5"));
        clickElement(By.id("av5"));

        scrollToElement(By.id("av6"));
        clickElement(By.id("av6"));

        clickElement(By.id("bSubmit"));

        waitForElement(By.id("editPlan6"));
        clickElement(By.id("editPlan6"));

        waitForElement(By.id("bSubmit"));
        clickElement(By.id("bCancel"));

        waitForElement(By.id("deletePlan6"));
        clickElement(By.id("deletePlan6"));
        waitForElement(By.id("doDeletePlan"));
        clickElement(By.id("doDeletePlan"));

        waitForElement(By.id("deletePlan5"));

        // demonstraiting option stuff
        Thread.sleep(500);
        waitForElement(By.id("nav_options"));
        clickElement(By.id("nav_options"));

        waitForElement(By.id("addNewOption"));
        clickElement(By.id("addNewOption"));

        waitForElement(By.id("bSubmit"));
        writeSlowly(By.id("inputTitle"), "New option", 100);
        writeSlowly(By.id("inputAdditionFee"), "5000", 100);
        writeSlowly(By.id("inputMonthlyFee"), "15", 100);
        writeSlowly(By.id("inputDescription"), "Some information that makes a lot of sense.", 50);

        clickElement(By.id("bSubmit"));

        clearElement(By.id("inputAdditionFee"));
        writeSlowly(By.id("inputAdditionFee"), "50", 100);
        clickElement(By.id("bSubmit"));

        waitForElement(By.id("editOption7"));
        clickElement(By.id("editOption7"));

        waitForElement(By.id("bSubmit"));
        clickElement(By.id("bl1"));
        clickElement(By.id("pr1"));
        clickElement(By.id("pr2"));
        clickElement(By.id("bl2"));
        clickElement(By.id("bl3"));
        clickElement(By.id("bl3"));
        clickElement(By.id("pr3"));
        clickElement(By.id("pr3"));

        clickElement(By.id("bSubmit"));

        waitForElement(By.id("deleteOption7"));
        clickElement(By.id("deleteOption7"));

        waitForElement(By.id("doDeleteOption"));
        clickElement(By.id("doDeleteOption"));

        // demonstrating customers stuff
        Thread.sleep(2000);
        waitForElement(By.id("nav_users"));
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
        writeSlowly(By.id("users_search"), "914", 100);
        clickElement(By.id("doSearchCustomers"));
        Thread.sleep(4000);

        hoverElement(By.id("doReset"), 2000);
        clickElement(By.id("doReset"));

        Thread.sleep(2000);
        hoverElement(By.id("paging_change"), 2000);
        clickElement(By.id("paging_change"));
        hoverElement(By.id("20"), 2000);
        clickElement(By.id("20"));
        Thread.sleep(4000);
        scrollToElement(By.id("pagination"));

        Thread.sleep(4000);

        scrollToElement(By.id("nav_fullname"));
        Thread.sleep(4000);
        writeSlowly(By.id("users_filter"), "8914", 1000);

        Thread.sleep(4000);
        clearElement(By.id("users_filter"));
        writeSlowly(By.id("users_filter"), "harry granger 666", 500);
        Thread.sleep(4000);
        clickElement(By.id("doReset"));

        Thread.sleep(8000);

        waitForElement(By.id("signout"));
        clickElement(By.id("signout"));
    }

    @Test
    public void authorizedAdmin() throws InterruptedException {
        driver.get("http://asusbook:8080/javaschool/");

        waitForElement(By.id("nav_login"));
        clickElement(By.id("nav_login"));

        waitForElement(By.id("inputEmail"));
        writeSlowly(By.id("inputEmail"), "adumble@ecare.com", 100);
        writeSlowly(By.id("inputPassword"), "admin", 50);
        clickElement(By.id("loginSubmit"));

        waitForElement(By.id("nav_users"));
        clickElement(By.id("nav_users"));

        waitForElement(By.id("editCustomer4"));
        clickElement(By.id("editCustomer4"));

        waitForElement(By.id("rl1"));
        scrollToElement(By.id("rl1"));
        clickElement(By.id("rl1"));
        clickElement(By.id("rl1"));

        waitForElement(By.id("rl2"));
        clickElement(By.id("rl2"));

        waitForElement(By.id("signout"));
        clickElement(By.id("signout"));
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
