package selenium;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import junit.framework.Assert;

public class Homework {

	WebDriver driver;
	By accountBtn = By.xpath("//a[@class='skip-link skip-account']");
	By myAccountBtn = By.xpath("//div[@class='skip-content skip-active']//a[@title='My Account']");
	By loginBtn = By.xpath("//button[@title='Login']");
	By usernametxt = By.xpath("//input[@name='login[username]']");
	By usernameErrorRequired = By.xpath("//div[@id='advice-required-entry-email']");
	By usernameErrorValidate = By.xpath("//div[@id='advice-validate-email-email']");
	By passwordtxt = By.xpath("//input[@name='login[password]']");
	By passwordError = By.xpath("//div[@id='advice-required-entry-pass']");
	By passwordErrorValidate = By.xpath("//div[@id='advice-validate-password-pass']");
	By validateAccount = By.xpath("//span[contains(text(),'Invalid login or password.')]");
	By createAccountBtn = By.xpath("//a[@title='Create an Account']");
	By firstNametxt = By.xpath("//input[@id='firstname']");
	By middleNametxt = By.xpath("//input[@id='middlename']");
	By lastNametxt = By.xpath("//input[@id='lastname']");
	By emailtxt = By.xpath("//input[@id='email_address']");
	By passtxt = By.xpath("//input[@id='password']");
	By passconfirmtxt = By.xpath("//input[@id='confirmation']");
	By registerBtn = By.xpath("//button[@title='Register']");
	By registerMsgSuccess = By.xpath("//span[contains(text(),\"Thank you for registering with Main Website Store.\")]");
	By logoutBtn = By.xpath("//a[@title='Log Out']");
	
	@BeforeClass
	public void beforeClass() {
		// 1. Open browser
		driver = new FirefoxDriver();
		// 2. Open website
		driver.get("http://live.guru99.com/");
		// wait for element visible 30s
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}

	// Test case 1: Login empty
	@Test
	public void tc_01_Login_Empty() {
		// 2. click "My account" to direct login page
		driver.findElement(accountBtn).click();
		driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		driver.findElement(myAccountBtn).click();

		Assert.assertEquals(driver.getCurrentUrl(), "http://live.guru99.com/index.php/customer/account/login/");
		// 4. Click Login button
		driver.findElement(loginBtn).click();
		// 5. Verify Error message in 2 field "This is a required field."
		Assert.assertEquals(driver.findElement(usernameErrorRequired).getText(), "This is a required field.");
		Assert.assertEquals(driver.findElement(passwordError).getText(), "This is a required field.");
	}

	// Test case 2: Login with email invalid
	@Test
	public void tc_02_Login_With_Email_Invalid() {
		// 2. click "My account" to direct login page
		driver.findElement(accountBtn).click();
		driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		driver.findElement(myAccountBtn).click();
		// 3. Input email invalid 123123@123123123
		driver.findElement(usernametxt).sendKeys("123123@123123123");
		// 4. Click Login button
		driver.findElement(loginBtn).click();
		// 5. Verify error message "Please enter a valid email address. For example
		// johndoe@domain.com."
		Assert.assertEquals(driver.findElement(usernameErrorValidate).getText(),
				"Please enter a valid email address. For example johndoe@domain.com.");
	}

	// Test case 3: Login with password < 6 character
	@Test
	public void tc_03_Login_With_Password_Incorrect() {
		// 3. Input correct username and incorrect password
		driver.findElement(usernametxt).clear();
		driver.findElement(usernametxt).sendKeys("automation@gmail.com");
		driver.findElement(passwordtxt).sendKeys("123");
		// 4. Click Login button
		driver.findElement(loginBtn).click();
		// 5. Verify error message password
		Assert.assertEquals(driver.findElement(passwordErrorValidate).getText(),
				"Please enter 6 or more characters without leading or trailing spaces.");
	}

	// Test case 4: Login with password incorrect
	@Test
	public void tc_04_Login_With_Password_Incorrect() {
		// 3. Input correct username and incorrect password
		driver.findElement(usernametxt).clear();
		driver.findElement(usernametxt).sendKeys("automation@gmail.com");
		driver.findElement(passwordtxt).sendKeys("123123123");
		// 4. Click Login button
		driver.findElement(loginBtn).click();
		// 5. Verify error message password
		Assert.assertEquals(driver.findElement(validateAccount).getText(), "Invalid login or password.");

	}

	// Test case 5: Create an account
	@Test
	public void tc_05_Create_An_Account() {
		String email = "automation" + random() + "@gmail.com";
		// Click 'Create an account'
		driver.findElement(createAccountBtn).click();
		Assert.assertEquals(driver.getCurrentUrl(), "http://live.guru99.com/index.php/customer/account/create/");
		// Fill textbox
		driver.findElement(firstNametxt).sendKeys("Tran");
		driver.findElement(middleNametxt).sendKeys("Xuan");
		driver.findElement(lastNametxt).sendKeys("Ngoc");
		driver.findElement(emailtxt).sendKeys(email);
		driver.findElement(passtxt).sendKeys("123123123");
		driver.findElement(passconfirmtxt).sendKeys("123123123");
		// Register 
		driver.findElement(registerBtn).click();
		
		Assert.assertEquals(driver.findElement(registerMsgSuccess).getText(), "Thank you for registering with Main Website Store.");
		// Log out
		driver.findElement(accountBtn).click();
		driver.findElement(logoutBtn).click();
		// Verify home page
		Assert.assertEquals(driver.getCurrentUrl(), "http://live.guru99.com/index.php/customer/account/logoutSuccess/");
		Assert.assertEquals(driver.getCurrentUrl(), "http://live.guru99.com/index.php/");
	}

	@AfterClass
	public void afterClass() {
		driver.quit();
	}

	public int random() {
		int number;
		Random rd = new Random();
		number = rd.nextInt(999999);
		return number;
	}
}
