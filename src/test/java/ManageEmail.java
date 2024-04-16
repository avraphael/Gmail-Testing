import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class ManageEmail extends HandleWebdriver {
    void verifyActionCompleted() {
        wait.until(ExpectedConditions.presenceOfElementLocated(By.className("aT")));
        Assert.assertNotNull(driver.findElement(By.className("aT")));
    }

    @BeforeMethod
    void _resetTab() {
        resetTab();
    }

    @BeforeMethod
    void selectEmail() {
        // If there is an already selected email, unselect it and select a different one
        try {
            WebElement checkedBox = driver.findElement(By.cssSelector("div[aria-checked=\"true\"]"));
            driver.findElement(By.cssSelector("div[aria-checked=\"false\"]")).click();
            checkedBox.click();
        } catch (Exception e) {
            // Select an email
            driver.findElement(By.cssSelector("div[role=\"checkbox\"]")).click();
        }
    }

    @Test
    void archive() {
        driver.findElement(By.cssSelector("div[aria-label=\"Archive\"]")).click();
        verifyActionCompleted();
    }

    @Test
    void reportSpam() {
        driver.findElement(By.cssSelector("div[aria-label=\"Report spam\"]")).click();
        // If it asks us if we want to block them say no
        try {
            driver.findElement(By.cssSelector("button[data-mdc-dialog-action=\"doNotBlockSender\"]")).click();
        } catch (Exception ignored) {}
        verifyActionCompleted();
    }

    @Test
    void delete() {
        driver.findElement(By.cssSelector("div[aria-label=\"Delete\"]")).click();
        verifyActionCompleted();
    }

    @Test
    void markAs() {
        // Gmail doesn't store the read status of an email in a way that can be easily viewed using automation
        // So this searches for "Mark as" and will do "Mark as read" or "Mark as unread"
        driver.findElement(By.cssSelector("div[aria-label^=\"Mark as\"]")).click();
        verifyActionCompleted();
    }

    @Test
    void snooze() {
        driver.findElement(By.cssSelector("div[aria-label=\"Snooze\"]")).click();

        // It brings up a popup menu that you can't use inspect element on
        // Moves mouse to correct location on screen and clicks
        actions.moveToLocation(550,150);
        actions.click();
        actions.perform();
        verifyActionCompleted();
    }

    @Test
    void addToTasks() {
        driver.findElement(By.cssSelector("div[aria-label=\"Add to Tasks\"]")).click();

        // Verify that it got added
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("iframe[title=\"Tasks\"]")));
        Assert.assertNotNull(driver.findElement(By.cssSelector("iframe[title=\"Tasks\"]")));

        // The tasks tab is an iframe so I have to switch to it to close it
        WebElement iframeElement = driver.findElement(By.cssSelector("iframe[title=\"Tasks\"]"));
        driver.switchTo().frame(iframeElement);

        // Close the tasks tab
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("[aria-label=\"Close Tasks\"]")));
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(".KL4X6e.TuA45b")));
        driver.findElement(By.cssSelector("[aria-label=\"Close Tasks\"]")).click();
        driver.switchTo().defaultContent();
    }

    @Test
    void starEmail() {
        driver.findElement(By.cssSelector("span[aria-label=\"Not starred\"]")).click();
    }
}
