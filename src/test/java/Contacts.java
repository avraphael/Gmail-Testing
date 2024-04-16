import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Contacts extends HandleWebdriver {
    void addFirstName(String name) {
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("input[aria-label=\"First name\"]")));
        driver.findElement(By.cssSelector("input[aria-label=\"First name\"]")).sendKeys(name);
    }
    void addLastName(String name) {
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("input[aria-label=\"Last name\"]")));
        driver.findElement(By.cssSelector("input[aria-label=\"Last name\"]")).sendKeys(name);
    }
    void addEmail(String email) {
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("input[aria-label=\"Email\"]")));
        driver.findElement(By.cssSelector("input[aria-label=\"Email\"]")).sendKeys(email);
    }
    void addPhone(String phone) {
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("input[aria-label=\"Phone\"]")));
        driver.findElement(By.cssSelector("input[aria-label=\"Phone\"]")).sendKeys(phone);
    }

    @BeforeClass
    void _resetTab() {
        resetTab();
    }

    @BeforeClass
    void openContacts() throws InterruptedException {
        Thread.sleep(500);
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("div[aria-label=\"Contacts\"]")));
        driver.findElement(By.cssSelector("div[aria-label=\"Contacts\"]")).click();

        // Switching to the tasks tab inside an iframe
        WebElement iframeElement = driver.findElement(By.cssSelector("iframe[title=\"Contacts\"]"));
        driver.switchTo().frame(iframeElement);
        Thread.sleep(2000);
    }

    @Test(priority = 1)
    void createContact() throws InterruptedException {
        // Click create contact
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[text()='Create contact']")));
        driver.findElement(By.xpath("//span[text()='Create contact']")).click();
        Thread.sleep(500);

        // Add contact info
        addFirstName("Anthony");
        addLastName("Raphael");
        addEmail("avraphael4623@eagle.fgcu.edu");

        // Click save and confirm that it was saved
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button[aria-label=\"Save\"]")));
        driver.findElement(By.cssSelector("button[aria-label=\"Save\"]")).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[text()='New contact created']")));

        // Return to main contacts page
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button[aria-label=\"Back\"]")));
        driver.findElement(By.cssSelector("button[aria-label=\"Back\"]")).click();
    }

    @Test(priority = 2)
    void searchContacts() {
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("button[aria-label=\"Search\"]")));
        driver.findElement(By.cssSelector("button[aria-label=\"Search\"]")).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("input[aria-label=\"Search for contacts\"]")));
        driver.findElement(By.cssSelector("input[aria-label=\"Search for contacts\"]")).sendKeys("Anthony");
    }

    @Test(priority = 3)
    void selectContact() {
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("div[jscontroller=\"ldsgJf\"]")));
        driver.findElement(By.cssSelector("div[jscontroller=\"ldsgJf\"]")).click();
    }

    @Test(priority = 4)
    void editContact() throws InterruptedException {
        // Click edit contact
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button[aria-label=\"Edit contact\"]")));
        driver.findElement(By.cssSelector("button[aria-label=\"Edit contact\"]")).click();

        addPhone("2399384645");

        // Click save and confirm that it was saved
        Thread.sleep(1000);
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(".zyTWof-Ng57nc.NiKupb.zyTWof-Ng57nc-OWXEXe-FNFY6c")));
        driver.findElement(By.cssSelector("button[aria-label=\"Save\"]")).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[text()='Contact details saved']")));
    }

    @Test(priority = 4)
    void addToFavorites() {
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("button[aria-label=\"Add to favorites\"]")));
        driver.findElement(By.cssSelector("button[aria-label=\"Add to favorites\"]")).click();
    }

    @Test(priority = 5)
    void deleteContact() {
        // Click delete contact
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("button[aria-label=\"Delete from contacts\"]")));
        driver.findElement(By.cssSelector("button[aria-label=\"Delete from contacts\"]")).click();

        // Confirm deletion
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("button[data-mdc-dialog-action=\"delete\"]")));
        driver.findElement(By.cssSelector("button[data-mdc-dialog-action=\"delete\"]")).click();
    }

    @AfterClass
    void closeContacts() {
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("[aria-label=\"Close side panel\"]")));
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(".KL4X6e.TuA45b")));
        driver.findElement(By.cssSelector("[aria-label=\"Close side panel\"]")).click();
        driver.switchTo().defaultContent();
    }
}
