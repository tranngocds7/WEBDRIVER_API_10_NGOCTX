package selenium;

import static org.junit.Assert.assertTrue;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.server.handler.SendKeys;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_12_Javascript_Executor {
	WebDriver driver;
	JavascriptExecutor js;
	
	String customername, gender, dateofbirth, address, city, state, phone, pin, email, password, customerID;

	// new customer
	By customernameTextbox = By.xpath("//input[@name='name']");
	By maleRadio = By.xpath("//input[@name='rad1']");
	By dateOfBirthRadio = By.xpath("//input[@name='dob']");
	By addressTextarea = By.xpath("//textarea[@name='addr']");
	By cityTextbox = By.xpath("//input[@name='city']");
	By stateTextbox = By.xpath("//input[@name='state']");
	By pinTextbox = By.xpath("//input[@name='pinno']");
	By phoneTextbox = By.xpath("//input[@name='telephoneno']");
	By emailTextbox = By.xpath("//input[@name='emailid']");
	By passwordTextbox = By.xpath("//input[@name='password']");
	By summitButton = By.xpath("//input[@name='sub']");
		
	// Verify customer info
	By customernameTextboxRow = By.xpath("//td[text()='Customer Name']/following-sibling::td");
	By maleRadioRow = By.xpath("//td[text()='Gender']/following-sibling::td");
	By dateOfBirthRadioRow = By.xpath("//td[text()='Birthdate']/following-sibling::td");
	By addressTextareaRow = By.xpath("//td[text()='Address']/following-sibling::td");
	By cityTextboxRow = By.xpath("//td[text()='City']/following-sibling::td");
	By stateTextboxRow = By.xpath("//td[text()='State']/following-sibling::td");
	By pinTextboxRow = By.xpath("//td[text()='Pin']/following-sibling::td");
	By phoneTextboxRow = By.xpath("//td[text()='Mobile No.']/following-sibling::td");
	By emailTextboxRow = By.xpath("//td[text()='Email']/following-sibling::td");
		
	@BeforeClass
	public void beforeClass() {
		System.setProperty("webdriver.chrome.driver", "..\\lib\\chromedriver.exe");
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		
		js = (JavascriptExecutor) driver;
		
		customername = "Tran Xuan Ngoc";
		gender = "male";
		dateofbirth = "1996-01-01";
		address = "234 Nguyen Trai";
		city = "Ha Noi";
		state = "Hoan Kiem";
		phone = "0814279048";
		pin = "789000";
		email = "autotest" + randomEmail() + "@gmail.com";
		password = "123456";
	}

	
	public void tc_01() {
		navigateToUrlByJS("http://live.guru99.com/");
		
		// Sử dụng JE để get domain của page và verify domain
		String homePageUrl = (String) executeForBrowser("return document.URL");
		Assert.assertEquals(homePageUrl, "http://live.guru99.com/");
		
		// Sử dụng JE để get URL của page và verify URL
		String homePageDomain = (String) executeForBrowser("return document.domain");		
		Assert.assertEquals(homePageDomain, "live.guru99.com");
		
		// Open mobile page (Sử dụng JE)
		WebElement mobileLink = driver.findElement(By.xpath("//a[text()='Mobile']"));
		highlightElement(mobileLink);
		clickToElementByJS(mobileLink);
		
		// Add sản phẩm Samsung Galaxy vào Cart
		WebElement samsungAddToCart = driver.findElement(By.xpath("//a[text()='Samsung Galaxy']/parent::h2/following-sibling::div//span[text()='Add to Cart']"));
		clickToElementByJS(samsungAddToCart);
		
		// Verify message được hiển thị
		String samsungSucessMessage = (String) executeForBrowser("return document.documentElement.innerText");
		Assert.assertTrue(verifyTextInInnerText("Samsung Galaxy was added to your shopping cart."));

		// Open Privacy Policy page
		clickToElementByJS(driver.findElement(By.xpath("//a[text()='Privacy Policy']")));
		
		// Verify title của page
		String privacyTitle = (String) executeForBrowser("return document.title");
		Assert.assertEquals(privacyTitle, "Privacy Policy");
		
		// Scroll xuống cuối page
		scrollToBottomPage();
		
		// 
		WebElement wishListRow = driver.findElement(By.xpath("//th[text()='WISHLIST_CNT']/following-sibling::td[text()='The number of items in your Wishlist.']"));
		Assert.assertTrue(wishListRow.isDisplayed());
		
		// Navigate tới domain khác
		navigateToUrlByJS("http://demo.guru99.com/v4/");
		
		// Verify domain sau khi navigate
		String demoGuruDomain = (String) executeForBrowser("return document.domain");		
		Assert.assertEquals(demoGuruDomain, "demo.guru99.com");

	}

	public void tc_02_Remove_Attribute() throws Exception {
		// Assert vào trang
		navigateToUrlByJS("http://demo.guru99.com/v4/");
		
		// Đăng nhập thông tin: User và Password
		driver.findElement(By.xpath("//input[@name='uid']")).sendKeys("mngr200208");
		driver.findElement(By.xpath("//input[@name='password']")).sendKeys("uhejuqA");
		driver.findElement(By.xpath("//input[@name='btnLogin']")).click();

		Thread.sleep(3000);
		// Chọn menu New Customer
		driver.findElement(By.xpath("//a[text()='New Customer']")).click();
		
		// Nhập dữ liệu đúng
		driver.findElement(customernameTextbox).sendKeys(customername);
		driver.findElement(maleRadio).click();
		removeAttributeInDOM(driver.findElement(dateOfBirthRadio), "type");
		driver.findElement(dateOfBirthRadio).sendKeys(dateofbirth);
		driver.findElement(addressTextarea).sendKeys(address);
		driver.findElement(cityTextbox).sendKeys(city);
		driver.findElement(stateTextbox).sendKeys(state);
		driver.findElement(pinTextbox).sendKeys(pin);
		driver.findElement(phoneTextbox).sendKeys(phone);
		driver.findElement(emailTextbox).sendKeys(email);
		driver.findElement(passwordTextbox).sendKeys(password);
		driver.findElement(summitButton).click();

		// Step 05: Get ra thông tin customerID
		customerID = driver.findElement(By.xpath("//td[text()='Customer ID']/following-sibling::td")).getText();
		System.out.println(customerID);
		// Step 06: Verify tất cả thông tin vừa mới tạo
		String emailverify = email;
		Assert.assertEquals(driver.findElement(customernameTextboxRow).getText(), customername);
		Assert.assertEquals(driver.findElement(maleRadioRow).getText(), gender);
		Assert.assertEquals(driver.findElement(dateOfBirthRadioRow).getText(), dateofbirth);
		Assert.assertEquals(driver.findElement(addressTextareaRow).getText(), address);
		Assert.assertEquals(driver.findElement(cityTextboxRow).getText(), city);
		Assert.assertEquals(driver.findElement(stateTextboxRow).getText(), state);
		Assert.assertEquals(driver.findElement(pinTextboxRow).getText(), pin);
		Assert.assertEquals(driver.findElement(phoneTextboxRow).getText(), phone);
		Assert.assertEquals(driver.findElement(emailTextboxRow).getText(), emailverify);
	}
	
	@Test
	public void tc_03_Create_an_Account() {
		// Vào page
		navigateToUrlByJS("http://live.guru99.com/");
		
		// Click vào My Account
		clickToElementByJS(driver.findElement(By.xpath("//a/span[text()='Account']")));
		clickToElementByJS(driver.findElement(By.xpath("//div[@id='header-account']//a[text()='My Account']")));
		
		// Click "CREATE AN ACCOUNT" button để tới trang đăng ký tài khoản
		clickToElementByJS(driver.findElement(By.xpath("//span[text()='Create an Account']")));
		
		// Nhập thông tin hợp lệ vào tất cả các field		
		driver.findElement(By.xpath("//input[@id='firstname']")).sendKeys("Tran");
		driver.findElement(By.xpath("//input[@id='middlename']")).sendKeys("Xuan");
		driver.findElement(By.xpath("//input[@id='lastname']")).sendKeys("Ngoc");
		driver.findElement(By.xpath("//input[@id='email_address']")).sendKeys(email);
		driver.findElement(By.xpath("//input[@id='password']")).sendKeys("123123123");
		driver.findElement(By.xpath("//input[@id='confirmation']")).sendKeys("123123123");
		
		// Click "REGISTER" button 
		clickToElementByJS(driver.findElement(By.xpath("//button[@title='Register']")));
		
		// Verify message xuất hiện khi đăng ký thành công
		Assert.assertEquals(driver.findElement(By.xpath("//span[contains(text(),\"Thank you for registering with Main Website Store.\")]")).getText(), "Thank you for registering with Main Website Store.");
	}
	@AfterClass
	public void afterClass() {
	}
	
	public void clickAddToCart (String productName) {
		js.executeScript("arguments[0].click();", driver.findElement(By.xpath("//a[text()='" + productName + "']/parent::h2/following-sibling::div//span[text()='Add to Cart']")));
	}
	
	public boolean verifyProductAddToCartSuccess(String productName) {
		WebElement element = driver.findElement(By.xpath("//li[@class='success-msg']//span[text()='" + productName + " was added to your shopping cart.']"));
		return element.isDisplayed();
	}
	
	public boolean verifyTextInInnerText(String textExpected) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		String textActual = (String) js.executeScript("return document.documentElement.innerText.match('" + textExpected + "')[0]");
		System.out.println("Text actual = " + textActual);
		return textActual.equals(textExpected);
	}

	public void highlightElement(WebElement element) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		
		// Get ra giá trị Element trước khi set
		String originalStyle = element.getAttribute("style");
		// Set CSS mới cho Element
		js.executeScript("arguments[0].setAttribute(arguments[1], arguments[2])", element, "style", "border: 5px solid red; border-style: dashed;");
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		js.executeScript("arguments[0].setAttribute(arguments[1], arguments[2])", element, "style", originalStyle);
	}
	
	public Object executeForBrowser(String javaScript) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		return js.executeScript(javaScript);
	}
	
	public Object clickToElementByJS(WebElement element) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		return js.executeScript("arguments[0].click();", element);
	}
	
	public Object sendkeyToElementByJS(WebElement element, String value) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		return js.executeScript("arguments[0].setAttribute('value','" + value +"')", element);
	}
	
	public Object removeAttributeInDOM(WebElement element, String attributeRemove) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		return js.executeScript("arguments[0].removeAttribute('" + attributeRemove +"')", element);
	}
	
	public Object scrollToBottomPage() {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		return js.executeScript("window.scrollBy(0,document.body.scrollHeight)");
	}
	
	public Object navigateToUrlByJS(String url) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		return js.executeScript("window.location = '" + url + "'");
	}

	public int randomEmail() {
		Random rd = new Random();
		return rd.nextInt(999999);
	}

}
