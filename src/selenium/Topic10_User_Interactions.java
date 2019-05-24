package selenium;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import junit.framework.Assert;

public class Topic10_User_Interactions {
	WebDriver driver;
	Actions action;
	Alert alert;	
	JavascriptExecutor excutor;
	
	String workingDirectory = System.getProperty("user.dir");
	String jsFilePath = workingDirectory + "\\lib\\drag_and_drop_helper.js";
	String jQueryFilePath = workingDirectory + "\\lib\\jquery_load_helper.js";
	// define locator
	By profileButton = By.xpath("//span[text()='Profile']");
	
	
	@BeforeClass
	public void beforeClass() {
		System.setProperty("webdriver.chrome.driver", "D:\\automation\\lib\\chromedriver.exe");
		driver = new ChromeDriver();
		
		action = new Actions(driver);
		excutor = (JavascriptExecutor) driver;
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}

	@Test
	public void tc01_Move_Mouse_To_Element() throws Exception {
		driver.get("https://www.myntra.com/");
		
		WebElement profileText = driver.findElement(profileButton);
		action.moveToElement(profileText).perform();
		
		WebElement loginButton = driver.findElement(By.xpath("//a[text()='log in']"));
		Assert.assertTrue(loginButton.isDisplayed());
		
		loginButton.click();
		
		Assert.assertTrue(driver.findElement(By.xpath("//div[@class='login-box']")).isDisplayed());
	}

	@Test
	public void tc02_Click_and_Hold_Element() throws Exception {
		driver.get("http://jqueryui.com/resources/demos/selectable/display-grid.html");
		
		List <WebElement> numberItems = driver.findElements(By.xpath("//ol[@id='selectable']/li"));
		
		action.clickAndHold(numberItems.get(0)).moveToElement(numberItems.get(3)).release().perform();
		
		Thread.sleep(3000);
		List <WebElement> numberSeleted = driver.findElements(By.xpath("//ol[@id='selectable']/li[contains(@class,'ui-selected')]"));
		Assert.assertEquals(numberSeleted.size(), 4);
	}
	
	@Test
	public void tc03_Click_and_Select_Random() {
		driver.get("http://jqueryui.com/resources/demos/selectable/display-grid.html");
		
		List <WebElement> numberItems = driver.findElements(By.xpath("//ol[@id='selectable']/li"));
		
		action.keyDown(Keys.CONTROL).perform();
		action.click(numberItems.get(0));
		action.click(numberItems.get(2));
		action.click(numberItems.get(5));
		action.click(numberItems.get(10));
		action.keyUp(Keys.CONTROL).perform();
		
		List <WebElement> numberSeleted = driver.findElements(By.xpath("//ol[@id='selectable']/li[contains(@class,'ui-selected')]"));
		Assert.assertEquals(numberSeleted.size(), 4);
		
	}
	
	@Test
	public void tc04_Double_Click() throws Exception {
		driver.get("http://www.seleniumlearn.com/double-click");
		WebElement doubleclickmeButton = driver.findElement(By.xpath("//button[text()='Double-Click Me!']"));
		excutor.executeScript("arguments[0].scrollIntoView();", doubleclickmeButton);
		Thread.sleep(2000);
		action.doubleClick(doubleclickmeButton).perform();
		Thread.sleep(2000);
		
		Assert.assertEquals(driver.switchTo().alert().getText(), "The Button was double-clicked.");
		
		driver.switchTo().alert().accept();
	}
	
	@Test
	public void tc05_Right_Click() throws Exception {
		driver.get("http://swisnl.github.io/jQuery-contextMenu/demo.html");
		WebElement rightclickmeButton = driver.findElement(By.xpath("//span[text()='right click me']"));
		action.contextClick(rightclickmeButton).perform();
		
		WebElement quickBeforeHover = driver.findElement(By.xpath("//li[contains(@class,'context-menu-icon-quit') and not(contains(@class,'context-menu-hover'))]"));
		action.moveToElement(quickBeforeHover).perform();
		
		WebElement quickAfterHover = driver.findElement(By.xpath("//li[contains(@class,'context-menu-icon-quit') and contains(@class,'context-menu-hover')and contains(@class,'context-menu-visible')]"));
		Assert.assertTrue(quickAfterHover.isDisplayed());
	}
	
	@Test
	public void tc06_Drag_and_Drop() throws Exception {
		driver.get("https://demos.telerik.com/kendo-ui/dragdrop/angular");
		WebElement smallCircle = driver.findElement(By.xpath("//div[@id='draggable']"));
		WebElement bigCircle = driver.findElement(By.xpath("//div[@id='droptarget']"));
		excutor.executeScript("arguments[0].scrollIntoView();", bigCircle);
		Thread.sleep(2000);
		
		action.dragAndDrop(smallCircle, bigCircle).perform();
		Thread.sleep(2000);
		Assert.assertEquals(bigCircle.getText(), "You did great!");
	
	}
	
	@Test
	public void tc07_DragandDropHTML5_Xpath() throws Exception {
		driver.get("https://bestvpn.org/html5demos/drag/");
		
		String oneXpath = "//a[@id='one']";
		String targetXpath = "//div[@id='bin']";
		
		WebElement oneXpathelement = driver.findElement(By.xpath(oneXpath));
		excutor.executeScript("arguments[0].scrollIntoView();", oneXpathelement);
		Thread.sleep(3000);
		
		drag_the_and_drop_html5_by_xpath(oneXpath, targetXpath);
		
		Thread.sleep(5000);
		
	}
	@AfterClass
	public void afterClass() {
//		driver.quit();
	}
	public void drag_the_and_drop_html5_by_xpath(String sourceLocator, String targetLocator) throws AWTException {

		WebElement source = driver.findElement(By.xpath(sourceLocator));
		WebElement target = driver.findElement(By.xpath(targetLocator));

		// Setup robot
		Robot robot = new Robot();
		robot.setAutoDelay(500);

		// Get size of elements
		Dimension sourceSize = source.getSize();
		Dimension targetSize = target.getSize();

		// Get center distance
		int xCentreSource = sourceSize.width / 2;
		int yCentreSource = sourceSize.height / 2;
		int xCentreTarget = targetSize.width / 2;
		int yCentreTarget = targetSize.height / 2;

		Point sourceLocation = source.getLocation();
		Point targetLocation = target.getLocation();
		System.out.println(sourceLocation.toString());
		System.out.println(targetLocation.toString());

		// Make Mouse coordinate center of element
		sourceLocation.x += 20 + xCentreSource;
		sourceLocation.y += 110 + yCentreSource;
		targetLocation.x += 20 + xCentreTarget;
		targetLocation.y += 110 + yCentreTarget;

		System.out.println(sourceLocation.toString());
		System.out.println(targetLocation.toString());

		// Move mouse to drag from location
		robot.mouseMove(sourceLocation.x, sourceLocation.y);

		// Click and drag
		robot.mousePress(InputEvent.BUTTON1_MASK);
		robot.mouseMove(((sourceLocation.x - targetLocation.x) / 2) + targetLocation.x, ((sourceLocation.y - targetLocation.y) / 2) + targetLocation.y);

		// Move to final position
		robot.mouseMove(targetLocation.x, targetLocation.y);

		// Drop
		robot.mouseRelease(InputEvent.BUTTON1_MASK);
	}
}
