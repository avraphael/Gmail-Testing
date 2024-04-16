import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Task extends HandleWebdriver {

    @BeforeClass
    void _resetTab() {
        resetTab();
    }

    @BeforeClass
    void openTasks() throws InterruptedException {
        Thread.sleep(500);
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("div[aria-label=\"Tasks\"]")));
        driver.findElement(By.cssSelector("div[aria-label=\"Tasks\"]")).click();

        // Switching to the tasks tab inside an iframe
        WebElement iframeElement = driver.findElement(By.cssSelector("iframe[title=\"Tasks\"]"));
        driver.switchTo().frame(iframeElement);
        Thread.sleep(2000);
    }

    @Test(priority = 1)
    void addTask() throws InterruptedException {
        // Click on add task
        wait.until(ExpectedConditions.elementToBeClickable(By.className("VfPpkd-Jh9lGc")));
        driver.findElement(By.className("VfPpkd-Jh9lGc")).click();

        // Enter a title
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//textarea[@placeholder='Title']")));
        driver.findElement(By.xpath("//textarea[@placeholder='Title']")).sendKeys("Sample Task");

        // Click off to confirm
        driver.findElement(By.className("Kqic2d")).click();
        Thread.sleep(500);
    }

    @Test(priority = 2)
    void starTask() {
        driver.findElement(By.cssSelector("button[aria-label=\"Add to Starred\"]")).click();
    }

    @Test(priority = 2)
    void createSubtask() throws InterruptedException {
        driver.findElement(By.cssSelector("button[tabindex=\"0\"][aria-label=\"Task options\"]")).click();
        Thread.sleep(500);
        driver.findElement(By.xpath("//*[contains(text(), \"Add a subtask\")]")).click();

        // Enter a title
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//textarea[@placeholder='Title']")));
        Thread.sleep(500);
        driver.findElement(By.xpath("//textarea[@placeholder='Title']")).sendKeys("Sample Subtask");

        // Click off to confirm
        driver.findElement(By.className("Kqic2d")).click();
    }

    @Test(priority = 3)
    void unindentTask() throws InterruptedException {
        driver.findElement(By.cssSelector("button[tabindex=\"-1\"][aria-label=\"Task options\"]")).click();
        Thread.sleep(500);
        driver.findElement(By.xpath("//*[contains(text(), \"Unindent\")]")).click();
    }

    @Test(priority = 4)
    void moveTaskToTop() throws InterruptedException {
        driver.findElement(By.cssSelector("button[tabindex=\"0\"][aria-label=\"Task options\"]")).click();
        Thread.sleep(500);
        driver.findElement(By.xpath("//*[contains(text(), \"Move to top\")]")).click();
    }

    @Test(priority = 5)
    void indentTask() throws InterruptedException {
        driver.findElement(By.cssSelector("button[tabindex=\"-1\"][aria-label=\"Task options\"]")).click();
        Thread.sleep(500);
        driver.findElement(By.xpath("//*[contains(text(), \"Indent\")]")).click();
    }

    @Test(priority = 6)
    void deleteTask() throws InterruptedException {
        driver.findElement(By.cssSelector("button[tabindex=\"0\"][aria-label=\"Task options\"]")).click();
        Thread.sleep(500);
        driver.findElement(By.xpath("//div[@class='jO7h3c' and text()='Delete']")).click();
    }

    @Test(priority = 7)
    void completeTask() {
        driver.findElement(By.cssSelector("button[aria-label=\"Mark completed\"]")).click();
    }

    @Test(priority = 8)
    void viewCompletedTasks() throws InterruptedException {
        Thread.sleep(7500);
        driver.findElement(By.cssSelector("button.VfPpkd-Bz112c-LgbsSe.yHy1rc.eT1oJ.mN1ivc.DpT2Ud.iIDMFf")).click();
    }

    @Test(priority = 9)
    void deleteCompletedTask() {
        driver.findElement(By.cssSelector("button[aria-label=\"Delete task\"]")).click();
    }

    @AfterClass
    void closeTasks() {
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("[aria-label=\"Close Tasks\"]")));
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(".KL4X6e.TuA45b")));
        driver.findElement(By.cssSelector("[aria-label=\"Close Tasks\"]")).click();
        driver.switchTo().defaultContent();
    }
}
