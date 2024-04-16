import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Keep extends HandleWebdriver {

    @BeforeClass
    void _resetTab() {
        resetTab();
    }

    @BeforeClass
    void openKeep() throws InterruptedException {
        Thread.sleep(500);
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("div[aria-label=\"Keep\"]")));
        driver.findElement(By.cssSelector("div[aria-label=\"Keep\"]")).click();

        // Switching to the tasks tab inside an iframe
        WebElement iframeElement = driver.findElement(By.cssSelector("iframe[title=\"Keep\"]"));
        driver.switchTo().frame(iframeElement);
        Thread.sleep(2000);
    }

    @Test(priority = 1)
    void addNote() {
        // Click on create a note
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[text()='Take a note…']")));
        driver.findElement(By.cssSelector(".RmniWd-h1U9Be-TdyTDe-Bz112c")).click();

        // Add a title and message
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//textarea[@placeholder='Title']")));
        driver.findElement(By.xpath("//textarea[@placeholder='Title']")).sendKeys("Sample Note");
        driver.findElement(By.xpath("//textarea[@placeholder='Take a note…']")).sendKeys("This is a test.");

        // Click done
        driver.findElement(By.xpath("//div[text()='Done']")).click();
    }

    @Test(priority = 1)
    void addList() {
        // Click on create a list
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("div[aria-label=\"New list\"]")));
        driver.findElement(By.cssSelector("div[aria-label=\"New list\"]")).click();

        // Add a title
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//textarea[@placeholder='Title']")));
        driver.findElement(By.xpath("//textarea[@placeholder='Title']")).sendKeys("Sample List");

        // Add list items
        driver.findElement(By.xpath("//textarea[@placeholder='List item']")).sendKeys("List Item 1");
        driver.findElement(By.xpath("//textarea[@aria-label='List item' and @rows='1']")).sendKeys("List Item 3");

        // Click done
        driver.findElement(By.xpath("//div[text()='Done']")).click();
    }

    @Test(priority = 2)
    void searchForNote() throws InterruptedException {
        driver.findElement(By.cssSelector("div[aria-label=\"Search\"]")).click();
        driver.findElement(By.cssSelector("input[placeholder=\"Search Keep...\"]")).sendKeys("List");
        Thread.sleep(500);
    }

    @Test(priority = 3)
    void completeListItem() {
        driver.findElement(By.cssSelector("div[aria-checked=\"false\"]")).click();
    }

    @Test(priority = 3)
    void deleteListItem() throws InterruptedException {
        Thread.sleep(500);
        driver.findElement(By.cssSelector("div[aria-label=\"Delete list item\"]")).click();
    }

    @Test(priority = 4)
    void clearSearchBar() throws InterruptedException {
        driver.findElement(By.cssSelector("div[aria-label=\"Cancel\"]")).click();
        Thread.sleep(500);
    }

    @Test(priority = 5)
    void pinNote() throws InterruptedException {
        Thread.sleep(500);
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(".VIpgJd-j7LFlb.VIpgJd-j7LFlb-sn54Q")));
        driver.findElement(By.cssSelector("div[aria-label=\"Pin note\"]")).click();
    }

    @Test(priority = 6)
    void archiveNote() throws InterruptedException {
        driver.findElement(By.cssSelector("div[aria-label=\"Menu\"]")).click();
        Thread.sleep(500);
        driver.findElement(By.xpath("//div[text()='Archive']")).click();
    }

    @Test(priority = 6)
    void deleteNote() throws InterruptedException {
        // For some reason with lists specifically it refuses to click the options menu
        // It keeps claiming it's out of view despite being in the same position locationally and in code as for a regular note
        // So I just click the exact position here
        actions.moveToLocation(245,130);
        actions.click();
        actions.perform();
        Thread.sleep(500);
        driver.findElement(By.xpath("//div[text()='Delete']")).click();
    }

    @AfterClass
    void closeKeep() {
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("[aria-label=\"Close\"]")));
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(".KL4X6e.TuA45b")));
        driver.findElement(By.cssSelector("[aria-label=\"Close\"]")).click();
        driver.switchTo().defaultContent();
    }
}
