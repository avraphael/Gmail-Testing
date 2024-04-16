import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import java.util.List;

public class OpenEmail extends HandleWebdriver {
    void openEmail() {
        // For emails in tabs other than inbox it says it couldn't scroll the emails into view
        // So I'm just moving the mouse to them here
        actions.moveToLocation(800,190);
        actions.click();
        actions.perform();
    }

    @BeforeClass
    void _resetTab() {
        resetTab();
    }

    @Test(priority = 1)
    void readEmailFromInbox() throws InterruptedException {
        // Read an email if there is one
        openEmail();
    }

    @Test(priority = 2)
    void readStarredEmails() throws InterruptedException {
        // Go to starred emails
        driver.findElement(By.cssSelector("a[aria-label=\"Starred\"]")).click();
        wait.until(ExpectedConditions.titleContains("Starred"));

        // Read a starred email if there is one
        openEmail();
    }

    @Test(priority = 2)
    void readSnoozedEmails() throws InterruptedException {
        // Go to snoozed emails
        driver.findElement(By.cssSelector("a[aria-label=\"Snoozed\"]")).click();
        wait.until(ExpectedConditions.titleContains("Snoozed"));

        // Read a snoozed email if there is one
        openEmail();
    }

    @Test(priority = 2)
    void readSentEmails() throws InterruptedException {
        // Go to sent emails
        driver.findElement(By.cssSelector("a[aria-label=\"Sent\"]")).click();
        wait.until(ExpectedConditions.titleContains("Sent"));

        // Read a sent email if there is one
        openEmail();
    }

    @Test(priority = 2)
    void readDrafts() throws InterruptedException {
        // Go to drafts
        driver.findElement(By.cssSelector("a[aria-label^=\"Drafts\"]")).click();
        wait.until(ExpectedConditions.titleContains("Drafts"));

        // Read a draft if there is one
        openEmail();
    }
}
