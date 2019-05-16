package selenium;

import org.testng.annotations.Test;

import junit.framework.Assert;

import org.testng.annotations.BeforeClass;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterClass;

public class Topic07_Textbox_Textarea {
	WebDriver driver;

	String usernamelogin, passwordlogin;
	String customername, gender, dateofbirth, address, city, state, phone, pin, email, password;
	String customerID, addrEdit, cityEdit, stateEdit, pinEdit, phoneEdit, emailEdit, dateofbirthverify;

	By userIDTextbox = By.xpath("//input[@name='uid']");
	By passwordIDTextbox = By.xpath("//input[@name='password']");
	By loginButton = By.xpath("//input[@name='btnLogin']");
	By newcustomerButton = By.xpath("//a[text()='New Customer']");

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
	
	//edit page
	By editcustomerButton = By.xpath("//a[text()='Edit Customer']");
	By customerIDeditTextbox = By.xpath("//input[@name='cusid']");
	By submitEditButton = By.xpath("//input[@name='AccSubmit']");
	@BeforeClass
	public void beforeClass() {

		System.setProperty("webdriver.chrome.driver", "D:\\automation\\lib\\chromedriver.exe");

		// 1. Open browser
		driver = new ChromeDriver();
		// Step 01: nhập vào trang guru99.com
		driver.get("http://demo.guru99.com/v4/");
		driver.manage().window().maximize();
		// wait for element visible 30s
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		
		usernamelogin = "mngr194941";
		passwordlogin = "judegYj";
		
		customername = "Tran Xuan Ngoc";
		gender = "male";
		dateofbirth = "01-01-1996";
		address = "234 Nguyen Trai";
		city = "Ha Noi";
		state = "Hoan Kiem";
		phone = "0814279048";
		pin = "789000";
		email = "autotest" + randomEmail() + "@gmail.com";
		password = "123456";
		
		addrEdit = "2 Ho Tung Mau";
		cityEdit = "Nam Dinh";
		stateEdit = "Hai Hau";
		phoneEdit = "0814271212";
		pinEdit = "987000";
		emailEdit = "autotest" + randomEmail() + "@gmail.com";		
		
		dateofbirthverify = "1996-01-01";
	}

	// test case 1: create new customer
	@Test
	public void TC01_Create_New_Customer() {
		// step 02: đăng nhập vào trang vs user và pass
		driver.findElement(userIDTextbox).sendKeys(usernamelogin);
		driver.findElement(passwordTextbox).sendKeys(passwordlogin);
		driver.findElement(loginButton).click();
		// verify homepage được hiển thị thành công
		Assert.assertEquals(driver.getCurrentUrl(), "http://demo.guru99.com/v4/manager/Managerhomepage.php");
		// step -03: chọn Newcustomer
		driver.findElement(newcustomerButton).click();
		// 	step 04 nhập toàn bộ dữ liệu đúng
		driver.findElement(customernameTextbox).sendKeys(customername);
		driver.findElement(maleRadio).click();
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
		Assert.assertEquals(driver.findElement(dateOfBirthRadioRow).getText(), dateofbirthverify);
		Assert.assertEquals(driver.findElement(addressTextareaRow).getText(), address);
		Assert.assertEquals(driver.findElement(cityTextboxRow).getText(), city);
		Assert.assertEquals(driver.findElement(stateTextboxRow).getText(), state);
		Assert.assertEquals(driver.findElement(pinTextboxRow).getText(), pin);
		Assert.assertEquals(driver.findElement(phoneTextboxRow).getText(), phone);
		Assert.assertEquals(driver.findElement(emailTextboxRow).getText(), emailverify);

	}

	// test case 2: edit customer's info
	@Test
	public void TC02_Edit_New_Customer() {
		// step 07: Chọn menu edit customer
		customerID = driver.findElement(By.xpath("//td[text()='Customer ID']/following-sibling::td")).getText();
		System.out.println(customerID);
		driver.findElement(editcustomerButton).click();
		Assert.assertTrue(driver.findElement(customerIDeditTextbox).isDisplayed());
		driver.findElement(customerIDeditTextbox).sendKeys(customerID);
		driver.findElement(submitEditButton).click();
		// step 08: verify 2 giá trị customername và address
		Assert.assertEquals(driver.findElement(customernameTextbox).getAttribute("value"), customername);
		Assert.assertEquals(driver.findElement(addressTextarea).getAttribute("value"), address);
		// step 09: nhập giá trị mới rồi submit
		driver.findElement(addressTextarea).clear();
		driver.findElement(addressTextarea).sendKeys(addrEdit);
		driver.findElement(cityTextbox).clear();
		driver.findElement(cityTextbox).sendKeys(cityEdit);
		driver.findElement(stateTextbox).clear();
		driver.findElement(stateTextbox).sendKeys(stateEdit);
		driver.findElement(pinTextbox).clear();
		driver.findElement(pinTextbox).sendKeys(pinEdit);
		driver.findElement(phoneTextbox).clear();
		driver.findElement(phoneTextbox).sendKeys(phoneEdit);
		driver.findElement(emailTextbox).clear();
		driver.findElement(emailTextbox).sendKeys(emailEdit);
		driver.findElement(summitButton).click();
		// step 10: Verify các giá trị vừa sửa
		String emailverify = emailEdit;
		Assert.assertEquals(driver.findElement(addressTextareaRow).getText(), addrEdit);
		Assert.assertEquals(driver.findElement(cityTextboxRow).getText(), cityEdit);
		Assert.assertEquals(driver.findElement(stateTextboxRow).getText(), stateEdit);
		Assert.assertEquals(driver.findElement(pinTextboxRow).getText(), pinEdit);
		Assert.assertEquals(driver.findElement(phoneTextboxRow).getText(), phoneEdit);
		Assert.assertEquals(driver.findElement(emailTextboxRow).getText(), emailverify);


	}
	@AfterClass
	public void afterClass() {
	}

	public int randomEmail() {
		Random rd = new Random();
		return rd.nextInt(999999);
	}

}
