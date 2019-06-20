package selenium;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import junit.framework.Assert;

public class Topic08_Dropdown_CustomDropdown {
	WebDriver driver;

	//, "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"
	String[] monthSelected= {"January", "February", "March"};
	String rootFolderPath;
	@BeforeClass
	public void beforeClass() {
		rootFolderPath = System.getProperty("user.dir");
		System.setProperty("webdriver.chrome.driver", rootFolderPath + "\\lib\\chromedriver.exe");
		driver = new ChromeDriver();
		driver.manage().window().maximize();
//		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}
	/* Access to website "jquery"
	 * choose item last 19
	 * verify item just choosed successfully
	 * 
	 * */
	
	public void TC01_Jquery() throws Exception {
		driver.get("http://jqueryui.com/resources/demos/selectmenu/default.html");
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		
		selectItemInCustomDropdown("//span[@id='number-button']", "//ul[@id='number-menu']/li", "19");
		Thread.sleep(3000);
		Assert.assertTrue(driver.findElement(By.xpath("//span[@id='number-button']/span[@class='ui-selectmenu-text' and text()='19']")).isDisplayed());
		selectItemInCustomDropdown("//span[@id='number-button']", "//ul[@id='number-menu']/li", "12");
		Thread.sleep(3000);
		Assert.assertTrue(driver.findElement(By.xpath("//span[@id='number-button']/span[@class='ui-selectmenu-text' and text()='12']")).isDisplayed());
		selectItemInCustomDropdown("//span[@id='number-button']", "//ul[@id='number-menu']/li", "5");
		Thread.sleep(3000);
		Assert.assertTrue(driver.findElement(By.xpath("//span[@id='number-button']/span[@class='ui-selectmenu-text' and text()='5']")).isDisplayed());

	}

	
	public void TC02_Angular() throws Exception {
		driver.get("https://material.angular.io/components/select/examples");
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		
		selectItemInCustomDropdown("//mat-label[text()='State']/ancestor::span/preceding-sibling::mat-select//div[@class='mat-select-arrow-wrapper']", "//mat-option/span", "Arkansas");
		Thread.sleep(3000);
		Assert.assertTrue(driver.findElement(By.xpath("//mat-label[text()='State']/ancestor::span/preceding-sibling::mat-select//div[@class='mat-select-value']//span[text()='Arkansas']")).isDisplayed());
	}
	
	
	public void TC03_KendoUI() throws Exception {
		driver.get("https://demos.telerik.com/kendo-ui/dropdownlist/index");
		
		selectItemInCustomDropdown("//span[@aria-owns='color_listbox']","//ul[@id='color_listbox']/li","Orange");
		Thread.sleep(3000);
		
		Assert.assertTrue(driver.findElement(By.xpath("//span[@aria-owns='color_listbox']//span[@class='k-input']")).getText().equals("Orange"));
	}
	

	public void TC04_ReactJS() throws Exception {
		driver.get("https://react.semantic-ui.com/modules/dropdown/");
		
		selectItemInCustomDropdown("//div[contains(@class,'fluid selection dropdown')]","//div[contains(@class,'fluid selection dropdown')]//span","Jenny Hess");
		Thread.sleep(3000);
		
		Assert.assertTrue(driver.findElement(By.xpath("//div[contains(@class,'fluid selection dropdown')]/div[@class='text']")).getText().equals("Jenny Hess"));
	}
	
	 @Test
	public void TC07_Multi_Dropdown() throws Exception {
		driver.get("http://multiple-select.wenzhixin.net.cn/examples/#basic.html");
//		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		//switch to iframe
		WebElement iframeElement = driver.findElement(By.xpath("//div[@class='content']/iframe"));
		driver.switchTo().frame(iframeElement); 

		selectMultiItemInDropdown("//div[@class='ms-parent ']//button", "//div[@class='ms-drop bottom']//span", monthSelected);
		
	}
	
	@AfterClass
	public void afterClass() {
	}
	public void selectItemInCustomDropdown (String parentXpath, String allItemXpath, String expectedValueItem) throws Exception {
		// Click vào dropdown cho xổ hết tất cả các giá trị ra
		WebElement parentDropdown = driver.findElement(By.xpath(parentXpath));
		((JavascriptExecutor) driver).executeScript("arguments[0].click();",parentDropdown);
		Thread.sleep(3000);
		
		// Chờ cho tất cả các giá trị trong dropdown được load ra thành công
		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath(allItemXpath)));
		
		List <WebElement> allItems = driver.findElements(By.xpath(allItemXpath));
		System.out.println("Tat ca phan tu trong dropdown = " + allItems.size());
		
		// Duyệt qua hết tất cả các phẩn từ cho đến khi thoả mãn điều kiện
		for (WebElement childElement : allItems) {
			System.out.println("Text moi lan get =" + childElement.getText());
			
			if (childElement.getText().equals(expectedValueItem)) {
				// Click vào item cần chọn
				if (childElement.isDisplayed()) {
					System.out.println("Click by Selenium =" + childElement.getText());
					childElement.click();
				} else {
					((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);",childElement);
					Thread.sleep(1000);
					System.out.println("Click by JS =" + childElement.getText());
					((JavascriptExecutor) driver).executeScript("arguments[0].click();",childElement);
				}
				Thread.sleep(1000);
				break;
			}
		}
	}

	public void selectMultiItemInDropdown(String parentXpath, String allItemXpath, String[] expectedValueItem) throws Exception {
		// 1. click vào cái dropdown cho nó xổ hết tất cả các giá trị ra
		WebElement parentDropdown = driver.findElement(By.xpath(parentXpath));
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", parentDropdown);
		
		// 2. Chờ cho tất cả các giá trị trong dropdown được load thành công
		WebDriverWait waitExplicit = new WebDriverWait(driver, 30);
		waitExplicit.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath(allItemXpath)));
		
		List<WebElement> allItems = driver.findElements(By.xpath(allItemXpath));
		System.out.println("Tat ca phan tu trong dropdown = " + allItems.size());
		
		// Duyệt qua hết tất cả các phần tử cho đến khi thoả mãn điều kiện
		for(WebElement childElement : allItems) {
			// "January, April", "July
			for(String item : expectedValueItem) {
				if(childElement.getText().contains(item)) {
					// 3. scroll đến item cần chọn ( nếu như item có thể nhìn thấy thì không cần scroll
					((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", childElement);
					Thread.sleep(3000);
					
					// 4. Click vào item cần chọn
					((JavascriptExecutor) driver).executeScript("arguments[0].click();", childElement);
//					childElement.click();
					Thread.sleep(3000);
					
					List<WebElement> itemSeleted = driver.findElements(By.xpath("//li[@class='seleted']//input"));
					System.out.println("Item selected = " + itemSeleted.size());
					if(expectedValueItem.length == itemSeleted.size()) {
						break;
					}
				}
			}
		}
	}
	
	public boolean checkItemSelected(String[] itemSelectedText) {

		List<WebElement> itemSelected = driver.findElements(By.xpath("//li[@class='selected']//input"));

		int numberItemSelected = itemSelected.size();

		String allItemsSelectedText = driver.findElement(By.xpath("//div[@class='ms-drop bottom']//span")).getText();
		System.out.println("Text đã chọn: " + allItemsSelectedText);

		if (numberItemSelected <= 3 && numberItemSelected > 0) {
			for (String item : itemSelectedText) {
				if (allItemsSelectedText.contains(item)) {
					break;
				}
			}
			return true;
		} else {
			return driver.findElement(By.xpath("//button[@class='ms-choice']/span[text()='" + numberItemSelected + " of 12 selected']")).isDisplayed();
		}

	}
}
