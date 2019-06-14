package demotest;

import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import junit.framework.Assert;

public class Topic_14_UploadFiles {

	WebDriver driver;
	String root_Folder_Path, image_01_Path, image_02_Path, image_03_Path;
	String image_01_Name = "image_01.jpg";
	String image_02_Name = "image_02.jpg";
	String image_03_Name = "image_03.jpg";
	String chromePath, firefoxPath;
	@BeforeClass
	public void beforeClass() {
		root_Folder_Path = System.getProperty("user.dir");
		System.setProperty("webdriver.chrome.driver", root_Folder_Path + "\\lib\\chromedriver.exe");
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		
		image_01_Path = root_Folder_Path + "\\uploadFiles\\" + image_01_Name;
		image_02_Path = root_Folder_Path + "\\uploadFiles\\" + image_02_Name;
		image_03_Path = root_Folder_Path + "\\uploadFiles\\" + image_03_Name;
		
		System.out.println(image_01_Path);
		System.out.println(image_02_Path);
		System.out.println(image_03_Path);

		chromePath = root_Folder_Path + "\\uploadFiles\\chrome.exe";
		firefoxPath = root_Folder_Path + "\\uploadFiles\\firefox.exe";

	}

	
	public void tc_01() throws Exception {
		driver.get("http://blueimp.github.io/jQuery-File-Upload/");
		
		WebElement uploadFiles = driver.findElement(By.xpath("//input[@name='files[]']"));
		
		// Handle upload multiple files
		uploadFiles.sendKeys(image_01_Path + "\n" + image_02_Path + "\n" + image_03_Path);
		
		// Handle wait for 3 files upload success
		List<WebElement> fileLoaded = driver.findElements(By.xpath("//tbody[@class='files']//p[@class='name']"));
		for (WebElement file : fileLoaded) {
			System.out.println("File name = " +  file.getText());
			Assert.assertTrue(file.isDisplayed());
		}
		
		// Click start upload for each file
		List<WebElement> startButtons = driver.findElements(By.xpath("//tbody[@class='files']//button[@class='btn btn-primary start']"));
		for (WebElement start :startButtons) {
			start.click();
			Thread.sleep(2000);
		}
		
		Assert.assertTrue(driver.findElement(By.xpath("//a[text()='" + image_01_Name + "']")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//a[text()='" + image_02_Name + "']")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//a[text()='" + image_03_Name + "']")).isDisplayed());

	}

	public void tc_02_AutoIT() throws Exception {
		driver.get("http://blueimp.github.com/jQuery-File-Upload/");
		
		// click to 'Add file' to open the dialog
		WebElement uploadChrome = driver.findElement(By.cssSelector(".fileinput-button"));
		uploadChrome.click();
		
		Thread.sleep(3000);
		
		// Handle upload file by AutoIT
		if(driver.toString().contains("chrome")) {
			Runtime.getRuntime().exec(new String[] {chromePath, image_01_Path});			
		} else {
			Runtime.getRuntime().exec(new String[] {firefoxPath, image_01_Path});			
		}
		
		Thread.sleep(3000);
		
		Assert.assertTrue(driver.findElement(By.xpath("//tbody[@class='files']//p[@class='name' and text()='" + image_01_Name + "']")).isDisplayed());
	}

	
	public void tc_03_Robot() throws Exception {
		driver.get("http://blueimp.github.com/jQuery-File-Upload/");

		// click to 'Add file' to open the dialog
		WebElement uploadChrome = driver.findElement(By.cssSelector(".fileinput-button"));
		uploadChrome.click();

		Thread.sleep(1000);
		
		StringSelection select = new StringSelection(image_01_Path);
		
		
		// Copy to clipboard
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(select, null);
		
		// Thao tác vs Desktop App (AWT)
		Robot robot = new Robot();
		Thread.sleep(1000);
		
		// Nhấn phím Enter
		robot.keyPress(KeyEvent.VK_ENTER);
		robot.keyRelease(KeyEvent.VK_ENTER);
		
		// Nhấn xuống Ctrl + V
		robot.keyPress(KeyEvent.VK_CONTROL);
		robot.keyPress(KeyEvent.VK_V);
		
		// Nhả Ctrl + V
		robot.keyRelease(KeyEvent.VK_CONTROL);
		robot.keyRelease(KeyEvent.VK_V);
		Thread.sleep(1000);
		
		// Nhấn Enter
		robot.keyPress(KeyEvent.VK_ENTER);
		robot.keyRelease(KeyEvent.VK_ENTER);

	}
	
	@Test
	public void tc_04() throws Exception {
		driver.get("https://encodable.com/uploaddemo/");
		
		WebElement uploadFile = driver.findElement(By.xpath("//input[@type='file']"));
		
		// Choose file to upload
		uploadFile.sendKeys(image_01_Path);
		
		// Select dropdown
		Select uploadDropdown = new Select(driver.findElement(By.xpath("//select[@name='subdir1']")));
		uploadDropdown.selectByVisibleText("/uploaddemo/files/");
		
		// Input random folder
		String folderName = "seleniumTest" + random();
		driver.findElement(By.xpath("//input[@name='newsubdir1']")).sendKeys(folderName);
		
		// Input email and first name
		String emailAddress = "automationtest@gmail.com";
		String firstName = "Automation Test";
		driver.findElement(By.xpath("//input[@name='email_address']")).sendKeys(emailAddress);
		driver.findElement(By.xpath("//input[@name='first_name']")).sendKeys(firstName);
		
		// Click Begin Upload
		driver.findElement(By.xpath("//input[@id='uploadbutton']")).click();
		
		waitForElement(20, "//div[@id='uploadDoneContainer']//dd[text()='Email Address: " + emailAddress + "']");
		Assert.assertTrue(driver.findElement(By.xpath("//div[@id='uploadDoneContainer']//dd[text()='Email Address: " + emailAddress + "']")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//div[@id='uploadDoneContainer']//dd[text()='First Name: " + firstName + "']")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//div[@id='uploadDoneContainer']//a[text()='" + image_01_Name + "']")).isDisplayed());
		
		// Click 'View Uploaded Files' link
		driver.findElement(By.xpath("//div[@id='uploaderpage']//a[text()='View Uploaded Files']")).click();
		
		waitForElement(20, "//table[@id='filelist']//a[text()='uploads']");
		driver.findElement(By.xpath("//table[@id='filelist']//a[text()='" + folderName + "']")).click();
		String replaceImage01Name = replaceString(image_01_Name);
		waitForElement(20, "//a[text()='" + folderName + "']");
		Assert.assertTrue(driver.findElement(By.xpath("//a[text()='" + replaceImage01Name + "']")).isDisplayed());
		
		driver.findElement(By.xpath("//a[text()='" + replaceImage01Name + "']")).click();
		Thread.sleep(2000);
		
		Assert.assertTrue(driver.getCurrentUrl().contains(image_01_Name));
		Assert.assertTrue(driver.findElement(By.xpath("//a[text()='" + image_01_Name + "']")).isDisplayed());
	}
	
	public int random() {
		Random rg = new Random();
		return rg.nextInt(99999);
	}

	public void waitForElement(int seconds, String waitConditionLocator) {
		WebDriverWait wait = new WebDriverWait(driver, seconds);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(waitConditionLocator)));
	}

	public String replaceString(String stringReplace) {	 
		String replaceString = stringReplace.replace('_', ' ');// thay thế tất cả ký tự '_' thành ' '
		System.out.println(replaceString);
		return replaceString;
	}

	@AfterClass
	public void afterClass() {
	}

}
