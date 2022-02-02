
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.testng.Assert;

class MainDriverTest {
    private static WebDriver driver;

    public static void openPage() throws InterruptedException {
        String path = System.getProperty("user.dir");
        System.setProperty("webdriver.chrome.driver", path.concat("\\Drivers\\chromedriver.exe"));
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://saucedemo.com/");
        driver.findElement(By.cssSelector("#user-name")).sendKeys("standard_user");
        driver.findElement(By.cssSelector("#password")).sendKeys("secret_sauce");
        driver.findElement(By.cssSelector("#login-button")).click();
        Assert.assertEquals(driver.getCurrentUrl(), "https://www.saucedemo.com/inventory.html");

        List<WebElement> itemPrice = driver.findElements(By.cssSelector(".inventory_item_price"));
        List<Double> values = new ArrayList<Double>();
        for (WebElement price : itemPrice) {
            values.add(stringToDouble(price.getText()));
        }
        Double maxValue = Collections.max(values);
        List<WebElement> cart = driver.findElements(By.cssSelector(".btn.btn_primary.btn_small.btn_inventory"));
        cart.get(values.indexOf(maxValue)).click();
        driver.findElement(By.cssSelector(".shopping_cart_link")).click();
        Double inventoryValue = stringToDouble(driver.findElement(By.cssSelector(".inventory_item_price")).getText());
        Assert.assertEquals(inventoryValue, maxValue, "Item with highest price is added to the cart");

        
    }

    public static double stringToDouble(String number) {
        String NewNum = number.replace("$", "");
        return Double.parseDouble(NewNum);

    }

    public static void main(String[] args) throws InterruptedException {
        openPage();
        driver.quit();
    }

}
