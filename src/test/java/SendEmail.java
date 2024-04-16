import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import java.util.List;

public class SendEmail extends HandleWebdriver {
    void addRecipients(List<String> recipients) {
        WebElement recTextbox = driver.findElement(By.cssSelector("input[peoplekit-id=\"BbVjBd\"]"));

        for (String rec : recipients) {
            recTextbox.sendKeys(rec);
        }
    }
    void addRecipient(String recipient)  {
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("input[peoplekit-id=\"BbVjBd\"]")));
        driver.findElement(By.cssSelector("input[peoplekit-id=\"BbVjBd\"]")).sendKeys(recipient);
    }
    void addSubject(String subject) {
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("input[name=\"subjectbox\"]")));
        driver.findElement(By.cssSelector("input[name=\"subjectbox\"]")).sendKeys(subject);
    }
    void addMessage(String message) {
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("div[aria-label=\"Message Body\"]")));
        driver.findElement(By.cssSelector("div[aria-label=\"Message Body\"]")).sendKeys(message);
    }
    void clickSend() {
        driver.findElement(By.cssSelector("div[data-tooltip=\"Send \u202A(Ctrl-Enter)\u202C\"]")).click();
    }
    void checkForError() {
        // Verify there was an error, then close the error
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//span[text()='Error']")));
        Assert.assertNotNull(driver.findElement(By.xpath("//span[text()='Error']")));
        driver.findElement(By.xpath("//span[text()='OK']")).click();
        driver.findElement(By.cssSelector("img[aria-label=\"Save & close\"]")).click();
    }
    void verifyMessageSent() {
        wait.until(ExpectedConditions.presenceOfElementLocated(By.className("aT")));
        Assert.assertNotNull(driver.findElement(By.className("aT")));
    }

    @BeforeMethod
    void _resetTab() {
        resetTab();
    }

    @BeforeMethod
    void composeEmail() {
        // Click on compose an email
        driver.findElement(By.cssSelector("div[jscontroller=\"eIu7Db\"]")).click();

        try {
            // If it gives the popup bubble saying you can switch between signatures dismiss it
            driver.findElement(By.className("J-J5-Ji T-I T-I-JN")).click();
        } catch (Exception ignored) {}

        // Wait until compose email tab has loaded before continuing
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("input[peoplekit-id=\"BbVjBd\"]")));
    }

    @Test
    void sendEmail() {
        addRecipient(email);
        addSubject("test");
        addMessage("test");
        clickSend();
        verifyMessageSent();
    }

    @Test
    void noRecipients() {
        addSubject("test");
        addMessage("test");
        clickSend();
        checkForError();
    }

    @Test
    void invalidRecipient() {
        addRecipient(invalidEmail);
        addSubject("test");
        addMessage("test");
        clickSend();
        verifyMessageSent();
    }

    @Test
    void noSubject() {
        addRecipient(email);
        addMessage("test");
        clickSend();
        verifyMessageSent();
    }

    @Test
    void noMessage() {
        addRecipient(email);
        addSubject("test");
        clickSend();
        verifyMessageSent();
    }

    @Test
    void onlyRecipient() {
        addRecipient(email);
        clickSend();

        // This gives us an alert, switch to it and accept
        Alert alert = driver.switchTo().alert();
        alert.accept();

        verifyMessageSent();
    }
}
