package selenium;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import junit.framework.Assert;

public class Topic09_Button_Radio_Checkbox_Alert {
	WebDriver driver;
	JavascriptExecutor executor;

	/*
	 * define locator
	 * 
	 */
	// locator testcase 01
	By myaccountbutton = By.xpath("//div[@class='footer']//a[@title='My Account']");
	By createAccountButton = By.xpath("//a[@title='Create an Account']");
	// locator testcase 02
	By duazoneCheckbox = By.xpath("//label[text()='Dual-zone air conditioning']/preceding-sibling::input");
	By createAccountButton2 = By.xpath("//a[@title='Create an Account']");
	// locator testcase 03
	By engineRadio = By.xpath("//label[text()='2.0 Petrol, 147kW']/preceding-sibling::input");
	// locator testcase 04
	By jsAlertButton = By.xpath("//button[@onclick='jsAlert()']");
	By resultText = By.xpath("//p[@id='result']");
	// locator testcase 06
	By jsConfirmButton = By.xpath("//button[@onclick='jsConfirm()']");
	// locator testcase 07
	By jsPromptButton = By.xpath("//button[@onclick='jsPrompt()']");

	@BeforeClass
	public void beforeClass() {
		driver = new FirefoxDriver();

		executor = (JavascriptExecutor) driver;
	}

//==================================================================================================================
	// Test case 1: Javascript Executor
	// Step 01: Truy cập trang http://live.guru99.com/
	// Step 02: Click vào link "My account" dưới footer ( Sử dụng Javascript Executor code )
	// Step 03: Kiểm tra url của page sau khi click là: http://live.guru99.com/index.php/customer/account/login/
	// Step 04: Click vào button CREATE AN ACCOUNT ( Sử dụng Javascript Executorcode)
	// Step 05:Kiểm tra url của page sau khi click là:http://live.guru99.com/index.php/customer/account/create/
	@Test
	public void tc01_JavascriptExecutor() throws Exception {
		openPage("http://live.guru99.com/");
		
		WebElement myaccountElement = driver.findElement(myaccountbutton);
		executor.executeScript("arguments[0].click();", myaccountElement);
		
		Assert.assertEquals(driver.getCurrentUrl(), "http://live.guru99.com/index.php/customer/account/login/");
		
		Thread.sleep(3000);
		
		WebElement createAccountElement = driver.findElement(createAccountButton);
		executor.executeScript("arguments[0].click();", createAccountElement);
		
		Assert.assertEquals(driver.getCurrentUrl(), "http://live.guru99.com/index.php/customer/account/create/");
	}
//==================================================================================================================
	//Test case 2: Javascript Executor Step 01: Truy cập trang
	// http://live.guru99.com/ 
	// Step 02: Click vào checkbox: Dual-zone air conditioning (Thẻ input ko được sử dụng thuộc tính id) 
	// Step 03: Kiểm tra checkbox đó đã chọn 
	// Step 04: Sau khi checkbox đã được chọn - bỏ chọn nó và kiểm tra nó chưa được chọn
	@Test
	public void tc02_Checkbox() throws Exception {
		openPage("https://demos.telerik.com/kendo-ui/styling/checkboxes");
		
		WebElement checkboxElement = driver.findElement(duazoneCheckbox);
		executor.executeScript("arguments[0].click();", checkboxElement);
		
		Assert.assertTrue(driver.findElement(duazoneCheckbox).isSelected());
		
		executor.executeScript("arguments[0].click();", checkboxElement);
		
		Assert.assertFalse(driver.findElement(duazoneCheckbox).isSelected());

	}

	// ==================================================================================================================
	// Test case 3: Javascript Executor
	// Step 01: Truy cập trang http://live.guru99.com/
	// Step 02: Click vào checkbox: Dual-zone airconditioning (Thẻ input ko được sử
	// dụng thuộc tính id)
	// Step 03: Kiểm tra checkbox đó đã chọn
	// Step 04: Sau khi checkbox đã được chọn - bỏ chọn nó và kiểm tra nó chưa được
	// chọn
	@Test
	public void tc03_RadioButton() throws Exception {
		openPage("https://demos.telerik.com/kendo-ui/styling/radios");
		
		WebElement checkboxElement = driver.findElement(engineRadio);
		
		if(driver.findElement(engineRadio).isSelected()) {
			Assert.assertTrue(driver.findElement(engineRadio).isSelected());
		} else {//driver.findElement(engineRadio).isSelected() == false 
			executor.executeScript("arguments[0].click();", checkboxElement);
			Assert.assertTrue(driver.findElement(engineRadio).isSelected());
		}
	}

	// ==================================================================================================================
	// Test case 4: Javascript Executor
	// Step 01: Truy cập trang https://daominhdam.github.io/basic-form/index.html
	// Step 02: Click vào button: Click for JS Alert
	// Step 03: Verify message hiển thị trong alert là: I am a JS Alert
	// Step 04: Accept alert và verify message hiển thị tại Result là: You clicked
	// an alert successfully
	@Test
	public void tc04_Accept_Alert() throws Exception {
		openPage("https://daominhdam.github.io/basic-form/index.html");
		
		WebElement jsAlertElement = driver.findElement(jsAlertButton);
		executor.executeScript("arguments[0].click();", jsAlertElement);

		Alert alert = driver.switchTo().alert();
		String alertMessage = driver.switchTo().alert().getText();
		System.out.println(alertMessage);

		Assert.assertEquals(alertMessage, "I am a JS Alert");

		alert.accept();

		Assert.assertEquals(driver.findElement(resultText).getText(), "You clicked an alert successfully");
	}

	// ==================================================================================================================
	// Test case 5: Confirm Alert
	// Step 01: Truy cập trang https://daominhdam.github.io/basic-form/index.html
	// Step 02: Click vào button: Click for JS Confirm
	// Step 03: Verify message hiển thị trong alert là: I am a JS Confirm
	// Step 04: Accept alert và verify message hiển thị tại Result là: You clicked:
	// cancel
	@Test
	public void tc05_Confirm_Alert() throws Exception {
		openPage("https://daominhdam.github.io/basic-form/index.html");
		
		WebElement jsConfirmElement = driver.findElement(jsConfirmButton);
		executor.executeScript("arguments[0].click();", jsConfirmElement);

		Alert alert = driver.switchTo().alert();
		String alertMessage = driver.switchTo().alert().getText();
		System.out.println(alertMessage);

		Assert.assertEquals(alertMessage, "I am a JS Confirm");

		driver.switchTo().alert().dismiss();

		Assert.assertEquals(driver.findElement(resultText).getText(), "You clicked: Cancel");
	}

	// ==================================================================================================================
	// Test case 6: Prompt Alert
	// Step 01: Truy cập trang https://daominhdam.github.io/basic-form/index.html
	// Step 02: Click vào button: Click for JS Prompt
	// Step 03: Verify message hiển thị trong alert là: I am a JS Prompt
	// Step 04: Accept alert và verify message hiển thị tại Result là: You entered:
	// cancel
	@Test
	public void tc06_Prompt_Alert() throws Exception {
		openPage("https://daominhdam.github.io/basic-form/index.html");
		
		WebElement jsPromptElement = driver.findElement(jsPromptButton);
		executor.executeScript("arguments[0].click();", jsPromptElement);

		Alert alert = driver.switchTo().alert();
		String alertMessage = driver.switchTo().alert().getText();
		System.out.println(alertMessage);

		Assert.assertEquals(alertMessage, "I am a JS prompt");

		alert.sendKeys("daominhdam");
		
		alert.accept();

		Assert.assertEquals(driver.findElement(resultText).getText(), "You entered: daominhdam");
	}
	// ==================================================================================================================
		// Test case 7: Prompt Alert
		// Step 01: Truy cập trang https://daominhdam.github.io/basic-form/index.html
		// Step 02: Click vào button: Click for JS Prompt
		// Step 03: Verify message hiển thị trong alert là: I am a JS Prompt
		// Step 04: Accept alert và verify message hiển thị tại Result là: You entered:
		// cancel
		@Test
		public void tc07_Authentication_Alert() throws Exception {			
			String username = "admin";
			String password = "admin";
			
			openPage("http://the-internet.herokuapp.com/");
			
			WebElement basicAuthenLink = driver.findElement(By.xpath("//a[text()='Basic Auth']"));
			String url = basicAuthenLink.getAttribute("href");
			
			driver.get(byPassAuthentication_Alert(url, username, password));
			
			Thread.sleep(5000);
			
			Assert.assertTrue(driver.findElement(By.xpath("//p[contains(text(),'Congratulations! You must have the proper credentials.')]")).isDisplayed());
		}
	@AfterClass
	public void afterClass() {
		driver.quit();
	}

	public void openPage(String urlPage) {
		driver.get(urlPage);
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}
	public String byPassAuthentication_Alert(String url, String username, String password) {
		String[] splitUrl = url.split("//");
		url = splitUrl[0] + "//" + username + ":" + password + "@" + splitUrl[1];
		System.out.println("New Url" + url);
		return url;
	}
}
