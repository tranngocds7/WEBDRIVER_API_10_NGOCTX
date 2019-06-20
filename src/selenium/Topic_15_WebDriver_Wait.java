package selenium;

import java.sql.Timestamp;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.google.common.base.Function;

import junit.framework.Assert;

public class Topic_15_WebDriver_Wait {
	WebDriver driver;
	WebDriverWait waitExplicit;
	String rootFolderPath;
	
	By startButton = By.xpath("//div[@id='start']/button");
	By loadingIcon = By.xpath("//div[@id='loading']/img");
	By helloWorldText = By.xpath("//div[@id='finish']/h4");
	
	By dateTimePicker = By.xpath("//div[@class='RadCalendar RadCalendar_Silk']");
	By seletedDates = By.xpath("//div[@class='datesContainer']//span");
	@BeforeMethod
	public void beforeMethod() {
		rootFolderPath = System.getProperty("user.dir");
		System.setProperty("webdriver.chrome.driver", rootFolderPath + "\\lib\\chromedriver.exe");
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		
	}


	public void tc_01_ImplicitWait() {
		driver.get("http://the-internet.herokuapp.com/dynamic_loading/2");
		
		driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		
		driver.findElement(By.xpath("//div[@id='start']/button")).click();
		
		driver.manage().timeouts().implicitlyWait(8, TimeUnit.SECONDS);
		
		Assert.assertTrue(driver.findElement(By.xpath("//div[@id='finish']/h4")).isDisplayed());
		
		driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);

		
		Assert.assertEquals(driver.findElement(By.xpath("//div[@id='finish']/h4")).getText(), "Hello World!");
	}
	
	public void tc_02_EXplicitWait_Invisible() {
		waitExplicit = new WebDriverWait(driver, 5);
		driver.get("http://the-internet.herokuapp.com/dynamic_loading/2");
		
		waitExplicit.until(ExpectedConditions.elementToBeClickable(startButton));
		driver.findElement(startButton).click();
		
		// Điều kiện wait: Loading icon biến mất
		waitExplicit.until(ExpectedConditions.invisibilityOfElementLocated(loadingIcon));
		
		Assert.assertTrue(driver.findElement(By.xpath("//div[@id='finish']/h4")).isDisplayed());

		Assert.assertEquals(driver.findElement(By.xpath("//div[@id='finish']/h4")).getText(), "Hello World!");
	}
	

	public void tc_03_EXplicitWait_Visible() {
		waitExplicit = new WebDriverWait(driver, 2);
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		
		driver.get("http://the-internet.herokuapp.com/dynamic_loading/2");
		
		driver.findElement(startButton).click();

		waitExplicit.until(ExpectedConditions.visibilityOfElementLocated(helloWorldText));
	
		Assert.assertTrue(driver.findElement(helloWorldText).isDisplayed());
	}
	

	public void tc_04_EXplicitWait_Visible() {
		waitExplicit = new WebDriverWait(driver, 5);
		driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);
		driver.get("http://the-internet.herokuapp.com/dynamic_loading/2");
		// Hello vs loading icon ko có trong DOM
		// Hello invisible
		System.out.println("Start time Hello invisible = " + getDateTimeSecond());
		waitExplicit.until(ExpectedConditions.invisibilityOfElementLocated(helloWorldText));		
		System.out.println("End time Hello invisible = " + getDateTimeSecond());
		
		// Loading icon invisible
		System.out.println("Start time Loading icon invisible = " + getDateTimeSecond());
		waitExplicit.until(ExpectedConditions.invisibilityOfElementLocated(loadingIcon));		
		System.out.println("End time Loading icon invisible = " + getDateTimeSecond());
		
		// Click start button
		System.out.println("Start time Start button invisible = " + getDateTimeSecond());
		driver.findElement(startButton).click();
		System.out.println("End time Start button invisible = " + getDateTimeSecond());
		
		// Hello vs loading icon có trong DOM	
		// Loading icon invisible
		System.out.println("Start time Loading icon invisible = " + getDateTimeSecond());
		waitExplicit.until(ExpectedConditions.invisibilityOfElementLocated(loadingIcon));
		System.out.println("End time Loading icon invisible = " + getDateTimeSecond());
		// Start button invisible
		System.out.println("Start time Start button invisible = " + getDateTimeSecond());
		waitExplicit.until(ExpectedConditions.invisibilityOfElementLocated(startButton));		
		System.out.println("End time Start button invisible = " + getDateTimeSecond());
	}
	
	public void tc_05_EXplicitWait_Visible() {
		waitExplicit = new WebDriverWait(driver, 5);
		driver.get("https://demos.telerik.com/aspnet-ajax/ajaxloadingpanel/functionality/explicit-show-hide/defaultcs.aspx");
	
		// Wait for "Date Time Picker" visibility
		waitExplicit.until(ExpectedConditions.visibilityOfElementLocated(dateTimePicker));
		
		// Print "No Seleted Dates to display"
		System.out.println("Seletec Dates : " + driver.findElement(seletedDates).getText());
		
		// Choose current date
		driver.findElement(By.xpath("//table[@class='rcMainTable']//td[contains(@title,'18')]")).click();
		
		// wait for Loader ajax invisibile
		waitExplicit.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//div[@class='raDiv']")));
		
		// wait for selected date visibile
		waitExplicit.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//td[@class='rcSelected']")));
		
		Assert.assertEquals(driver.findElement(By.xpath("//td[@class='rcSelected']")).getAttribute("title"), driver.findElement(By.xpath("//div[@class=\"RadAjaxPanel\"]/span")).getText());
	}
	
	@Test
	public void tc_06_EXplicitWait_Visible() {
		waitExplicit = new WebDriverWait(driver, 5);
		driver.get("https://daominhdam.github.io/fluent-wait/");
		
		WebElement coundount = driver.findElement(By.xpath("//div[@id='javascript_countdown_time']"));
		// Wait for "Date Time Picker" visibility
		waitExplicit.until(ExpectedConditions.visibilityOf(coundount));
		
		new FluentWait<WebElement>(coundount)
			// Tổng time wait là 15s
			.withTimeout(15,TimeUnit.SECONDS)
			// Tần số mỗi 1s check 1 lần
			.pollingEvery(1, TimeUnit.SECONDS)
			// Nếu gặp exception là find ko thấy element sẽ bỏ qua
			.ignoring(NoSuchFieldException.class)
			// Kiểm tra điều kiện
			.until(new Function<WebElement, Boolean>() {
				public Boolean apply(WebElement element) {
					// Kiểm tra điều kiện countdount = 00
					boolean flag = element.getText().endsWith("00");
					System.out.println("Time " + element.getText());
					// return giá trị cho function apply
					return flag;
				}
			});
	}
	
	@AfterMethod
	public void afterMethods() {
	}

	public Date getDateTimeSecond() {
		Date date = new Date();
		date =  new Timestamp(date.getTime());
		return date;
		
	}
}
