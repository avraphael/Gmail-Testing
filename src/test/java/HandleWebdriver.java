import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeSuite;
import java.time.Duration;

public class HandleWebdriver {
    protected static WebDriver driver;
    protected static WebDriverWait wait;
    protected static Actions actions;

    protected String username = "leaf0784";
    protected String password = "Back in 2017, I saw a horse on the side of the road.";
    protected String invalid = username + "invalid";
    protected String email = "leaf0784@gmail.com";
    protected String invalidEmail = invalid + "@gmail.com";

    public void resetTab() {
        // Go to inbox and wait until it's loaded
        driver.get("https://mail.google.com/mail/u/0/#inbox");
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("div[jscontroller=\"eIu7Db\"]")));
    }

    @BeforeSuite
    public void setUp() {
        // Set up webdriver
        System.setProperty("webdriver.gecko.driver", "C:\\Users\\tonyr\\Downloads\\geckodriver-v0.34.0-win64\\geckodriver.exe");
        driver = new FirefoxDriver();

        // Set up a WebDriverWait instance with a timeout of 10 seconds
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // Set up an Actions object
        actions = new Actions(driver);

        // Go to gmail and maximize tab
        driver.get("https://www.google.com/gmail/about/");
        driver.manage().window().maximize();
    }

    @AfterMethod
    public void sleep() throws InterruptedException {
        Thread.sleep(500);
    }
}