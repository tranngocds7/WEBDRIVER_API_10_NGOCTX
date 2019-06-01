package selenium;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import junit.framework.Assert;

public class Topic_11_Popup_Iframe_Windows {
	WebDriver driver;
	JavascriptExecutor js; 
	
	// define locator
	// tc_04
	
	@BeforeClass
	public void beforeClass() {
		driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		js = (JavascriptExecutor) driver;
	}

	public void tc_01() throws Exception {
		driver.get("https://www.hdfcbank.com/");
		
		List <WebElement> popupImages = driver.findElements(By.xpath("//div[@id='parentdiv']//img[@class='popupbanner at-element-click-tracking']"));
		System.out.println("Popup displayed = " + popupImages.size());
		
		if (popupImages.size() > 0 && popupImages.get(0).isDisplayed()) {
			System.out.println("Size >= 1 - Visible (Có trong DOM + hiển thị ở trên UI): Displayed");
		} else if (popupImages.size() > 0 && !popupImages.get(0).isDisplayed()) {
			System.out.println("Size >= 1 - Visible (Có trong DOM + ko hiển thị ở trên UI): Undisplayed");
		} else if (popupImages.size() == 0) {
			System.out.println("Size = 0 - Ko có trong DOM + ko hiển thị ở trên UI: Undisplayed");
		}
		
		if (popupImages.size() > 0 && popupImages.get(0).isDisplayed()) {
			driver.findElement(By.xpath("//img[@class='popupCloseButton']")).click();
			Thread.sleep(2000);
			Assert.assertFalse(popupImages.get(0).isDisplayed());
		}
		
		WebElement lookingIframe = driver.findElement(By.xpath("//iframe[starts-with(@id,'viz_iframe')]"));
		driver.switchTo().frame(lookingIframe);
		
		Assert.assertEquals(driver.findElement(By.xpath("//span[@id='messageText']")).getText(), "What are you looking for?");
		
		// Switch qua parrent node
		driver.switchTo().defaultContent();
		
		// Verify right banner has 5 images
		List <WebElement> rightBannerImages = driver.findElements(By.xpath("//div[@id='rightbanner']//div[@class='owl-stage']/div[not(@class='owl-item cloned')]//img"));
		Assert.assertEquals(rightBannerImages.size(), 5);
		
		// Verify flip banner has 8 images
		List <WebElement> flipBannerImages = driver.findElements(By.xpath("//div[@class='flipBanner']//div[contains(@class,'product')]//img[@class='front icon']"));
		Assert.assertEquals(flipBannerImages.size(), 8);
	}

	
	public void tc_02() throws Exception {
		driver.get("https://daominhdam.github.io/basic-form/index.html");
		
		String parentID	= driver.getWindowHandle();
		System.out.println("Parent ID = " + parentID);
		
		WebElement googleLink = driver.findElement(By.xpath("//a[text()='GOOGLE']"));
		WebElement facebookLink = driver.findElement(By.xpath("//a[text()='FACEBOOK']"));
		WebElement tikiLink = driver.findElement(By.xpath("//a[text()='TIKI']"));

		js.executeScript("arguments[0].click();", googleLink);
		
		// Switch qua cửa sổ của Google
		switchToChildWindowsByTitle("Google");		
		Assert.assertEquals(driver.getTitle(), "Google");
		
		// Switch về parent window
		switchToChildWindowsByTitle("SELENIUM WEBDRIVER FORM DEMO");
		
		// Switch qua cửa sổ của Facebook
		facebookLink.click();
		switchToChildWindowsByTitle("Facebook - Đăng nhập hoặc đăng ký");
		
		// Kiểm tra title của window mới
		Assert.assertEquals(driver.getTitle(), "Facebook - Đăng nhập hoặc đăng ký");
		
		
		// Switch về parent window
		switchToChildWindowsByTitle("SELENIUM WEBDRIVER FORM DEMO");
		
		// Switch qua cửa sổ của Tiki
		tikiLink.click();
		switchToChildWindowsByTitle("Mua Hàng Trực Tuyến Uy Tín với Giá Rẻ Hơn tại Tiki.vn");
		Thread.sleep(3000);
		
		// Kiểm tra title của window mới
		Assert.assertEquals(driver.getTitle(), "Mua Hàng Trực Tuyến Uy Tín với Giá Rẻ Hơn tại Tiki.vn");
		
		//Close tất cả cửa sổ/ tab ngoại trừ parent window
		closeAllWindowsWithoutParent(parentID);
		
	}
	
	
	public void tc_03() throws Exception {
		
		WebDriverWait wait = new WebDriverWait(driver, 10);
		
		// Truy cập vào trang https://www.hdfcbank.com/
		driver.get("https://www.hdfcbank.com/");
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		
		//define parentIDget
		String parentID = driver.getWindowHandle();
		
		// Close popup
		WebElement popup;
		popup = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='parentdiv']//img[@class='popupCloseButton']")));
		// WebElement closePopUp = driver.findElement(By.xpath("//div[@id='parentdiv']//img[@class='popupCloseButton']"));
		if (popup.isDisplayed()) {
			popup.click();
			Thread.sleep(3000);
		}		
		
		// Click Agri link 
		WebElement agriButton = driver.findElement(By.xpath("//a[text()='Agri']"));
		agriButton.click();
		Thread.sleep(3000);
		
		// Mở ra tab/ window mới -> Switch qua tab mới
		switchToChildWindowsByTitle("HDFC Bank Kisan Dhan Vikas e-Kendra");		
		Assert.assertEquals(driver.getTitle(), "HDFC Bank Kisan Dhan Vikas e-Kendra");
		
		// Click Account Details link
		WebElement accountDetailsButton = driver.findElement(By.xpath("//ul[@class='grid_list clearfix']//p[text()='Account Details']"));
		accountDetailsButton.click();
		Thread.sleep(3000);
		
		// Mở ra tab/ window mới -> Switch qua tab mới
		switchToChildWindowsByTitle("Welcome to HDFC Bank NetBanking");		
		Assert.assertEquals(driver.getTitle(), "Welcome to HDFC Bank NetBanking");
		
		// Click Privacy Policy link
		WebElement frameFooter = driver.findElement(By.xpath("//frame[@src='footer.html']"));
		driver.switchTo().frame(frameFooter);
		Thread.sleep(3000);
		
		WebElement privaciPolicyLink = driver.findElement(By.xpath("//a[text()='Privacy Policy']"));
		privaciPolicyLink.click();
		Thread.sleep(3000);
		
		// Mở ra tab/ window mới -> Switch qua tab mới
		switchToChildWindowsByTitle("HDFC Bank - Leading Bank in India, Banking Services, Private Banking, Personal Loan, Car Loan");		
		Assert.assertEquals(driver.getTitle(), "HDFC Bank - Leading Bank in India, Banking Services, Private Banking, Personal Loan, Car Loan");
		
		// Click Privacy Policy link
		WebElement csrButton = driver.findElement(By.xpath("//div[@class='hygeinenav']//a[text()='CSR']"));
		csrButton.click();
		
		// Mở ra tab/ window mới -> Switch qua tab mới
		switchToChildWindowsByTitle("HDFC Bank - CSR - Homepage");		
		Assert.assertEquals(driver.getTitle(),"HDFC Bank - CSR - Homepage");
		
		// Close tất cả các windows/ tabs khác - chỉ giữ lại parent window 
		closeAllWindowsWithoutParent(parentID);
		Assert.assertEquals(driver.getTitle(), "HDFC Bank: Personal Banking Services");
		
	}
	
	@Test
	public void tc_04() throws Exception {
		driver.get("http://live.guru99.com/index.php/");
	
		// Click vào Mobile tab
		driver.findElement(By.xpath("//div[@id='header-nav']//a[text()='Mobile']")).click();
		String parentID = driver.getWindowHandle();
		// Add sản phẩm Sony Xperia vào để Compare 
		clickAddToCompare("Sony Xperia");
		Thread.sleep(3000);
		verifyProductAddToCompareSuccess("Sony Xperia");
		
		// Add sản phẩm Samsung Galaxy vào để Compare 
		clickAddToCompare("Samsung Galaxy");
		Thread.sleep(3000);
		verifyProductAddToCompareSuccess("Samsung Galaxy");
		
		// Click to Compare button
		driver.findElement(By.xpath("//button[@title='Compare']")).click();
		
		// Switch qua cửa sổ compare
		switchToChildWindowsByTitle("Products Comparison List - Magento Commerce");

		// Verify product number = 2
		List <WebElement> productNames = driver.findElements(By.xpath("//h2[@class='product-name']//a"));
		Assert.assertEquals(productNames.size(), 2);
		
		Assert.assertEquals(driver.getTitle(), "Products Comparison List - Magento Commerce");
		
		// Close tab và chuyển về Parent Window
		closeAllWindowsWithoutParent(parentID);
		
		
		
	}
	@AfterClass
	public void afterClass() {
	}
	
	// Nếu như có từ 2 tab/windows trở lên thì nó ko work đúng nữa
	public void switchToChildWindowByID(String parent) {
		// Get ra tất cả cácID của cửa sổ/ tab đang có
		Set<String> allWindows = driver.getWindowHandles();
		
		//Dùng vòng lặp để duyệt qua từng ID
		for (String runWindow : allWindows) {
			
			// Kiểm tra điều kiện: Nếu như ID nào # vs ID của parent thì nó sẽ switch qa và thoá vòng lặp
			if (!runWindow.equals(parent)) {
				driver.switchTo().window(runWindow);
				break;
			}
		}
	}

	public void switchToChildWindowsByTitle(String title) {
		// Get ra tất cả các ID của cửa sổ/ tab đang có
		Set<String> allWindows = driver.getWindowHandles();

		// Dùng vòng lặp để duyệt qua từng ID
		for (String runWindow : allWindows) {
			// Switch vào từng cửa sổ luôn
			driver.switchTo().window(runWindow);

			// Get ra title của tab/ window đó
			String currentWin = driver.getTitle();

			// Kiểm tra nếu actual title = expected title thì break
			if (currentWin.equals(title)) {
				break;
			}
		}
	}
	
	public boolean closeAllWindowsWithoutParent(String parentID) {
		// Get ra tất cả các ID của cửa sổ/ tab đang có
		Set<String> allWindows = driver.getWindowHandles();
		
		// Duyệt qua từng cái ID
		for (String runWindows : allWindows) {
			
			// Kiểm tra điều kiện: nếu như ID nào # ID của parent thì nó sẽ switch qa cửa sổ/ tab đó
			if(!runWindows.equals(parentID)) {
				
				//Switch qa cửa sổ or tab đó
				driver.switchTo().window(runWindows);
				
				// Close tab hiện tại đang active
				driver.close();
			}
		}
		
		// Switch vào parent
		driver.switchTo().window(parentID);
		
		// Kiểm tra còn lại duy nhất 1 cửa sổ
		if (driver.getWindowHandles().size() == 1)
			return true;
		else
			return false;
	}
	
	public void clickAddToCompare (String productName) {
		js.executeScript("arguments[0].click();", driver.findElement(By.xpath("//a[text()='" + productName + "']/parent::h2/following-sibling::div//a[text()='Add to Compare']")));
	}

	public boolean verifyProductAddToCompareSuccess(String productName) {
		WebElement element = driver.findElement(By.xpath("//li[@class='success-msg']//span[text()='The product " + productName + " has been added to comparison list.']"));
		return element.isDisplayed();
	}
}
