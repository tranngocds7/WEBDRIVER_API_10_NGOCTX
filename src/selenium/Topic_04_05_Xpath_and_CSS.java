package selenium;

import org.testng.annotations.Test;

import junit.framework.Assert;

import org.testng.annotations.BeforeClass;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterClass;

public class Topic_04_05_Xpath_and_CSS {
	WebDriver driver;

	// define locator
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

	//==================================================================================================================
	@BeforeClass
	public void beforeClass() {
		// 1. Open browser
		driver = new FirefoxDriver();
		// 2. Open website
		driver.get("http://live.guru99.com/");
		// wait for element visible 30s
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}
	// ==================================================================================================================

	/* Test case 1: Login empty Step 01: truy cập vào trang http://live.guru99.com/
	 * Step 02: Click vào link "My account" để tới trang đăng nhập Step 03: Để trống
	 * Username/Password Step 04: Click Login button Step 05: Verify error message
	 * xuất hiện tại 2 field: This is a required field
	 */
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

	// ==================================================================================================================
	/*
	 * Test case 2: Login with email invalid Step 01: Truy cập vào trang
	 * http://live.guru99.com/ Step 02: Click vào link "My Account" để tới trang
	 * đăng nhập Step 03: Nhập email invalid: 123123123@1241231 Step 04: Click Login
	 * button Step 05: Verify error message xuất hiện: Please enter a valid email
	 * address. For example johndoe@domain.com
	 */
	@Test
	public void tc_02_Login_With_Email_Invalid() {
		// Step 02: Click vào link "My Account" để tới trang đăng nhập
		driver.findElement(accountBtn).click();
		driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		driver.findElement(myAccountBtn).click();
		// Step 03: Nhập email invalid: 123123123@1241231
		driver.findElement(usernametxt).sendKeys("123123@123123123");
		// Step 04: Click Login button
		driver.findElement(loginBtn).click();
		// Step 05: Verify error message xuất hiện: Please enter a valid email address.
		// For example johndoe@domain.com
		Assert.assertEquals(driver.findElement(usernameErrorValidate).getText(), "Please enter a valid email address. For example johndoe@domain.com.");
	}

	// ==================================================================================================================
	/*
	 * Test case 3: Login with password < 6 character Step 01: Truy cập vào trang
	 * http://live.guru99.com/ Step 02: Click vào link "My Account" để tới trang
	 * đăng nhập Step 03: Nhập email correct and password incorrect:
	 * automation@gmail.com/ 123 Step 04: Click Login button Step 05: Verify error
	 * message xuất hiện: Please enter 6 or more character without leading or
	 * trailing spaces
	 */
	@Test
	public void tc_03_Login_With_Password_Incorrect() {
		// 3. Input correct username and incorrect password
		driver.findElement(usernametxt).clear();
		driver.findElement(usernametxt).sendKeys("automation@gmail.com");
		driver.findElement(passwordtxt).sendKeys("123");
		// 4. Click Login button
		driver.findElement(loginBtn).click();
		// 5. Verify error message password
		Assert.assertEquals(driver.findElement(passwordErrorValidate).getText(), "Please enter 6 or more characters without leading or trailing spaces.");
	}
	
	// ==================================================================================================================
	/*
	 * Test case 4: Login with password incorrect Step 01: Truy cập vào trang
	 * http://live.guru99.com/ Step 02: Click vào link "My Account" để tới trang
	 * đăng nhập Step 03: Nhập email correct and password incorrect:
	 * automation@gmail.com/ 123123123 Step 04: Click Login button Step 05: Verify
	 * error message xuất hiện: Invalid login or password
	 */
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
	//==================================================================================================================
	/*
	 * Test case 5: Create an account Step 01: Truy cập vào trang
	 * http://live.guru99.com/ Step 02: Click vào link "My Account" để tới trang
	 * đăng nhập Step 03: Click CREATE AN ACCOUNT button để tới trang đăng ký tài
	 * khoản Step 04: Nhập thông tin hợp lệ vào tất cả các field: Frist Name/ Last
	 * Name/ Email Address/ Password/ Confirm Password Step 05: Click Register
	 * button Step 06: Verify message xuất hiện khi đăng ký thành công: Thank you
	 * for registering with Main Website Store. Step 07: Logout khỏi hệ thống Step
	 * 08: Kiểm tra hệ thống navigate về Home page sau khi logout thành công
	 */
	@Test
	public void tc_05_Create_An_Account() throws Exception {
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
		Thread.sleep(10000);
		Assert.assertEquals(driver.getCurrentUrl(), "http://live.guru99.com/index.php/");
	}
	//==================================================================================================================

	@AfterClass
	public void afterClass() {
	}
	//==================================================================================================================

	public int random() {
		int number;
		Random rd = new Random();
		number = rd.nextInt(999999);
		return number;
	}
}
