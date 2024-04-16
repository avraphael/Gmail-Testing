import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;

public class Account extends HandleWebdriver {
    void goToSignIn() {
        // Go to gmail home
        driver.get("https://www.google.com/gmail/about/");

        // Click the sign in button
        driver.findElement(By.cssSelector("a[data-action=\"sign in\"]")).click();
    }
    void enterUsername(String name) {
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("input[type=\"email\"]")));
        driver.findElement(By.cssSelector("input[type=\"email\"]")).sendKeys(name + "@gmail.com");
        driver.findElement(By.id("identifierNext")).click();
    }
    void enterPassword(String passwd) {
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("input[type='password']")));
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("input[type='password']")));
        driver.findElement(By.cssSelector("input[type=\"password\"]")).sendKeys(passwd);
        driver.findElement(By.id("passwordNext")).click();
    }
    void goToEnterNewGmail(String gmail) {
        driver.get("https://www.google.com/gmail/about/");

        // Navigating to create an account
        driver.findElement(By.cssSelector("div[class=\"dropdown__label\"]")).click();
        driver.findElement(By.cssSelector("a[data-action=\"For my personal use\"]")).click();

        // Entering account info
        driver.findElement(By.id("firstName")).sendKeys("Matthew");
        driver.findElement(By.id("collectNameNext")).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("month")));
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("kPY6ve")));
        Select month = new Select(driver.findElement(By.id("month")));
        month.selectByValue("5");
        driver.findElement(By.id("day")).sendKeys("23");
        driver.findElement(By.id("year")).sendKeys("2005");
        Select gender = new Select(driver.findElement(By.id("gender")));
        gender.selectByValue("3");
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("kPY6ve")));
        driver.findElement(By.id("birthdaygenderNext")).click();

        // If it bring us to choose a gmail account, click create our own gmail account
        WebElement multiChoice = null;
        try {
            Thread.sleep(1000);
            multiChoice = driver.findElement(By.cssSelector("c-wiz[data-is-multi-choice-username=\"true\"]"));
        } catch (Exception ignored) {}

        if (multiChoice != null) {
            driver.findElement(By.xpath("//*[contains(text(), Create your own Gmail address)]")).click();
        }

        // Creating email name
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("input[name=\"Username\"]")));
        driver.findElement(By.cssSelector("input[name=\"Username\"]")).sendKeys(gmail);
        driver.findElement(By.id("next")).click();
    }
    void checkForError() {
        Assert.assertNotNull(driver.findElement(By.cssSelector("div[jsname=\"B34EJ\"]")));
    }

    @Test(priority = 1, enabled = false)
    void createAccount() throws InterruptedException {
        // Inputting info and account name
        goToEnterNewGmail(username);

        // Creating password
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("passwd")));
        Thread.sleep(500);
        driver.findElement(By.cssSelector("input[aria-label=\"Password\"]")).sendKeys(password);
        driver.findElement(By.cssSelector("input[aria-label=\"Confirm\"]")).sendKeys(password);
        driver.findElement(By.id("createpasswordNext")).click();

        // There's a section here where you have to prove you're not a robot
        // I don't believe you can go around this
        // So this will be done manually and the program will wait until it's done
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("phoneNumberId")));
        Thread.sleep(10000);
        driver.findElement(By.id("phoneNumberId")).sendKeys("2399384645");
        driver.findElement(By.cssSelector("button[jsname=\"LgbsSe\"]")).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("recoveryEmailId")));

        // Finalize account
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("button[jsname=\"LgbsSe\"]")));
        Thread.sleep(500);
        driver.findElement(By.cssSelector("button[jsname=\"LgbsSe\"]")).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("span[jsname=\"V67aGc\"]")));
        Thread.sleep(500);
        driver.findElement(By.cssSelector("span[jsname=\"V67aGc\"]")).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("span[jsname=\"V67aGc\"]")));
        Thread.sleep(500);
        driver.findElement(By.cssSelector("span[jsname=\"V67aGc\"]")).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("div[jscontroller=\"VXdfxd\"]")));
        Thread.sleep(500);
        driver.findElement(By.cssSelector("div[jscontroller=\"VXdfxd\"]")).click();

        // Verify it created account and put us in our inbox
        wait.until(ExpectedConditions.titleContains("Inbox"));
        Assert.assertTrue(driver.getTitle().contains("Inbox"));
    }

    @Test(priority = 3, enabled = false)
    void createExistingAccount() {
        // Try to create a gmail account with the same name
        goToEnterNewGmail(username);

        checkForError();
    }

    @Test(priority = 3, enabled = false)
    void createUsingSpecialCharacters() {
        // Try to create an email with special characters in the name
        goToEnterNewGmail(username + "@");

        checkForError();
    }

    @Test(priority = 3)
    void invalidEmail() throws InterruptedException {
         goToSignIn();

        // Enter an incorrect email
        enterUsername(invalid);
        Thread.sleep(1000);

        checkForError();
    }

    @Test(priority = 3)
    void invalidPassword() throws InterruptedException {
         goToSignIn();

         // Enter correct email then incorrect password
        enterUsername(username);
        enterPassword(invalid);
        Thread.sleep(1000);

        checkForError();
    }

    @Test(priority = 4)
    void login() {
         goToSignIn();

        enterUsername(username);
        enterPassword(password);

        // If it bring us to complete stuff instead of home page, clikc not now
        WebElement complete = null;
        try {
            Thread.sleep(1000);
            complete = driver.findElement(By.cssSelector("div[aria-label=\"Complete a few suggestions to get the most out of your Google account\"]"));
        } catch (Exception ignored) {}

        if (complete != null) {
            driver.findElement(By.xpath("//span[text()='Not now']")).click();
        }

        // Verify that we're logged in
        wait.until(ExpectedConditions.titleContains("Inbox"));
        Assert.assertTrue(driver.getTitle().contains("Inbox"));
    }

    @AfterSuite
    public void logout() throws InterruptedException {

        // Click google account
        driver.findElement(By.cssSelector("a[aria-label^=\"Google Account:\"]")).click();
        Thread.sleep(1000);

        // Click on sign out
        // It's in a script, so I can't find it as a web element
        actions.moveToLocation(1750,380);
        actions.click();
        actions.perform();

        driver.quit();
    }
}
