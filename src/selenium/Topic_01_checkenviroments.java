package selenium;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import junit.framework.Assert;

public class Topic_01_checkenviroments {
	WebDriver	driver;
	/*
	 1 - Mở cái brower lên
	 2 - Mở cái app lên 
	 3 - Kiểm tra cái url của home có đúng với mong muốn hay ko 
	 4 - kiểm tra cái title của page có đúng với title mong muốn hay ko ?
	 5 - đóng browser
	 * */
  @BeforeClass
  public void beforeClass() {
	  // 1 - mở cái browser lên
	  driver = new FirefoxDriver();
	  // 2 - mở cái app lên
	  driver.get("http://live.guru99.com/");
	  // wait for element visible 30s
	  driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
  }
  
  // Action phase
  @Test
  public void TC_01_CheckUrl() {
	  //  3 - Kiểm tra cái url của home có đúng với mong muốn hay ko 
	  
	  String homePageUrl = driver.getCurrentUrl();
	  System.out.print("Home page Url = " + homePageUrl);
	  Assert.assertEquals(homePageUrl, "http://live.guru99.com/");
  }
  
  @Test
  public void TC_02_CheckTitle() {
	  // 4 - kiểm tra cái title của page có đúng với title mong muốn hay ko ?
	  String homePageTitle = driver.getTitle();
	  System.out.print("Home page Title = " + homePageTitle);
	  Assert.assertEquals(homePageTitle, "Home page");
  }
  
  
  @AfterClass
  public void afterClass() {
	  //  5 - đóng browser
	  driver.quit();
  }

}
