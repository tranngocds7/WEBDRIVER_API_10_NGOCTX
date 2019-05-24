package selenium;

import org.testng.annotations.Test;

import junit.framework.Assert;

import org.testng.annotations.BeforeClass;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.server.handler.FindActiveElement;
import org.testng.annotations.AfterClass;

public class Topic06_WebBrowser_WebElement_Commands {
	WebDriver driver;

	// define locator
	// locator enabled
	By emailTextbox = By.xpath("//input[@name='user_email']");
	By under18Radio = By.xpath("//input[@id='under_18']");
	By interesCheckbox = By.xpath("//input[@name='user_interest']");
	By educationTextbox = By.xpath("//textarea[@name='user_edu']");
	By jobrole1Dropdown = By.xpath("//select[@name='user_job1']");
	By slider1Slide = By.xpath("//input[@name='slider-1']");
	By buttonenableButton = By.xpath("//button[@id='button-enabled']");
	// locator disable
	By passwordTextbox = By.xpath("//input[@type='password']");
	By buttonedisnableButton = By.xpath("//button[@id='button-disabled']");
	By biographyTextarea = By.xpath("//textarea[@id='bio']");
	By jobrole2Dropdown = By.xpath("//select[@name='user_job2']");
	By interesDisableCheckbox = By.xpath("//input[@id='check-disbaled']");
	By slider2Slide = By.xpath("//input[@name='slider-2']");
	By buttondisableButton = By.xpath("//button[@id='button-disabled']");

	@BeforeClass
	public void beforeClass() {
		driver = new FirefoxDriver();
		driver.get("https://daominhdam.github.io/basic-form/index.html");
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}

	// ==================================================================================================================

	/*
	 * Test case 1: Kiểm tra phần tử displayed Step 01: Truy cập vào trang:
	 * https://daominhdam.github.io/basic-form/index.html Step 02: Kiểm tra các phần
	 * tử sau hiển thị trên trang: Email/Age (Under 18)/ Education Step 03: Nếu có
	 * nhập giá trị: Automation Testing vào 2 field Email/ Education và chọn Age
	 * Under 18
	 */

	@Test
	public void Check_Item_Display() {
		boolean checkemail = driver.findElement(emailTextbox).isDisplayed();
		boolean checkage = driver.findElement(under18Radio).isDisplayed();

		Assert.assertTrue(checkemail);
		Assert.assertTrue(checkage);

		if (checkemail && checkage) {
			driver.findElement(emailTextbox).sendKeys("Automation Testing");
			driver.findElement(educationTextbox).sendKeys("Automation Testing");
			driver.findElement(under18Radio).click();
		}
	}

	// ==================================================================================================================
	/*
	 * Test case 2: Kiểm tra phần tử enable/displayed Step 01: Truy cập vào trang:
	 * https://daominhdam.github.io/basic-form/index.html Step 02: Kiểm tra các phần
	 * tử sau enable trên trang: Email/Age (Under 18)/ Education/ Job Role 01/
	 * Interests (Development)/ Slider 01/ Button is enable Step 03: Kiểm tra các
	 * phần tử sau disable trên trang: Password/ Age (Radiobutton is displayed)/
	 * Biography/ Job Role 02/ Interests (Checkbox is disabled)/ Slider 02/ Button
	 * is disabled Step 04: Nếu có in ra Element is enabled/ ngược lại Element is
	 * disabled
	 */
	@Test
	public void Check_Item_Enable_Disable() {
		// item enable
		checkStatusItem(emailTextbox);
		checkStatusItem(under18Radio);
		checkStatusItem(educationTextbox);
		checkStatusItem(interesCheckbox);
		checkStatusItem(jobrole1Dropdown);
		checkStatusItem(slider1Slide);
		checkStatusItem(buttonenableButton);
		// item disable
		checkStatusItem(passwordTextbox);
		checkStatusItem(buttonedisnableButton);
		checkStatusItem(biographyTextarea);
		checkStatusItem(jobrole2Dropdown);
		checkStatusItem(interesDisableCheckbox);
		checkStatusItem(slider2Slide);
		checkStatusItem(buttondisableButton);
	}

	// ==================================================================================================================
	/*
	 * Test case 3: Kiểm tra phần tử enable/displayed 
	 * Step 01: Truy cập vào trang: https://daominhdam.github.io/basic-form/index.html 
	 * Step 02: Click chọn Age (Under 18) - Radiobutton/ Interests (Development) - Checkbox
	 * Step 03: Kiểm tra các phần tử tại Step 02 đã được chọn
	 * Step 04: Click để bỏ chọn Interests (Development) checkbox
	 * Step 05: Kiểm tra các phần tử tại Step 04 đã được chọn
	 */
	@Test
	public void Check_Item_Seleted() {
		// Step 02: Click chọn Age (Under 18) - Radiobutton/ Interests (Development) - Checkbox
		driver.findElement(under18Radio).click();
		driver.findElement(interesCheckbox).click();
		// Step 03: Kiểm tra các phần tử tại Step 02 đã được chọn
		Assert.assertTrue(driver.findElement(under18Radio).isSelected());
		Assert.assertTrue(driver.findElement(interesCheckbox).isSelected());
		// Step 04: Click để bỏ chọn Interests (Development) checkbox
		driver.findElement(interesCheckbox).click();
		// Step 05: Kiểm tra các phần tử tại Step 04 đã được chọn
		Assert.assertFalse(driver.findElement(interesCheckbox).isSelected());

		
	}
	
	@AfterClass
	public void afterClass() {
	}

	public void checkStatusItem(By elementlocator) {
		if (driver.findElement(elementlocator).isEnabled()) {
			System.out.println("Element is enabled");
		} else { // driver.findElement(By.xpath(elementlocator)).isEnabled() == false
			System.out.println("Element is disable");
		}
	}
}
