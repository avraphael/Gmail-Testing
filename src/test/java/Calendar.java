import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Calendar extends HandleWebdriver {

    @BeforeClass
    void _resetTab() {
        resetTab();
    }

    @BeforeClass
    void openCalendar() throws InterruptedException {
        Thread.sleep(500);
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("div[aria-label=\"Calendar\"]")));
        driver.findElement(By.cssSelector("div[aria-label=\"Calendar\"]")).click();

        // Switching to the tasks tab inside an iframe
        WebElement iframeElement = driver.findElement(By.cssSelector("iframe[title=\"Calendar\"]"));
        driver.switchTo().frame(iframeElement);
        Thread.sleep(2000);

        // There's a popup the first time you ever open calendar, click the button to close it if it's there
        try {
            driver.findElement(By.xpath("//button[contains(text(), 'Got it')]")).click();
        } catch (Exception ignored) {}
    }

    @Test(priority = 1)
    void selectEvent() throws InterruptedException {
        Thread.sleep(1000);
        driver.findElement(By.xpath("//span[@class='PnMPye' and text()='Daylight Saving Time ends']")).click();
        Thread.sleep(1000);
        driver.findElement(By.cssSelector(".Wvuztd.kS7zqd")).click();
    }

    @Test(priority = 2)
    void selectDay() {
        driver.findElement(By.cssSelector("div[data-datekey=\"27820\"]")).click();
    }

    @Test(priority = 2)
    void selectDayFromDropdown() throws InterruptedException {
        driver.findElement(By.className("VfPpkd-vQzf8d")).click();
        Thread.sleep(500);
        driver.findElement(By.cssSelector("div[aria-label=\"4\"]")).click();
    }

    @Test(priority = 2)
    void selectToday() {
        driver.findElement(By.cssSelector(".VfPpkd-LgbsSe.VfPpkd-LgbsSe-OWXEXe-INsAgc.Rj2Mlf.OLiIxf.PDpWxe.P62QJc.LQeN7.ddoZCd.xYvThe.j9Fkxf")).click();
    }

    @Test(priority = 3)
    void selectDayMode() throws InterruptedException {
        driver.findElement(By.cssSelector("button[aria-label=\"Options\"]")).click();
        Thread.sleep(500);
        driver.findElement(By.xpath("//span[@class='VfPpkd-StrnGf-rymPhb-b9t22c' and text()='Day']")).click();
    }

    @Test(priority = 3)
    void selectScheduleMode() throws InterruptedException {
        driver.findElement(By.cssSelector("button[aria-label=\"Options\"]")).click();
        Thread.sleep(500);
        driver.findElement(By.xpath("//span[@class='VfPpkd-StrnGf-rymPhb-b9t22c' and text()='Schedule']")).click();
    }

    @Test(priority = 4)
    void createEvent() throws InterruptedException {
        driver.findElement(By.xpath("//span[@class='VfPpkd-vQzf8d' and text()='Create an event']")).click();
        Thread.sleep(500);
        driver.findElement(By.cssSelector("input[aria-label=\"Add title\"]")).sendKeys("Sample Event");
        driver.findElement(By.cssSelector("button[aria-label=\"Save\"]")).click();
    }

    @AfterClass
    void closeCalendar() {
        driver.findElement(By.cssSelector("button[aria-label=\"Close\"]")).click();
        driver.switchTo().defaultContent();
    }
}
