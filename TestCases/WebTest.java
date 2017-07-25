import java.util.regex.Pattern;
import java.io.File;
import java.util.concurrent.TimeUnit;
import org.junit.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.support.ui.Select;

public class WebTest {
  private WebDriver driver;
  private String baseUrl;
  private boolean acceptNextAlert = true;
  private StringBuffer verificationErrors = new StringBuffer();

  Integer passCount =0;
  Integer totalCount =4;

  @Before
  public void setUp() throws Exception {
	String Xport = System.getProperty("lmportal.xvfb.id", ":20");
		final File firefoxPath = new File(System.getProperty("lmportal.deploy.firefox.path", "/usr/bin/firefox"));
		FirefoxBinary firefoxBinary = new FirefoxBinary(firefoxPath);
		firefoxBinary.setEnvironmentProperty("DISPLAY", Xport);
		driver = new FirefoxDriver(firefoxBinary, null);
//	  driver = new FirefoxDriver();
		baseUrl = "#BASE_URL#";
//		baseUrl = "http://localhost:59256/";
    driver.manage().timeouts().implicitlyWait(500, TimeUnit.MILLISECONDS);
  }

  @Test
  public void testWeb() throws Exception {
	  try {
	    driver.get(baseUrl + "/");
	    
	    try {
	      assertEquals("", driver.findElement(By.id("textbox")).getText());
	      passCount++;
	    } catch (Error e) {
	      verificationErrors.append("Textbox not found with id 'textbox'.\n");
	    }
	    
	    try {	
	      assertEquals("", driver.findElement(By.id("button")).getText());
	      passCount++;
	    } catch (Error e) {
	      verificationErrors.append("Button not found with id 'button'.\n");
	    }
	    
	    driver.findElement(By.id("textbox")).clear();
	    driver.findElement(By.id("textbox")).sendKeys("Ross");
	    driver.findElement(By.id("button")).click();
	    String s = driver.findElement(By.cssSelector("h1")).getText();
	    
	    try {
	      assertTrue(s.contains("Ross"));
	      passCount++;
	    } catch (Error e) {
	      verificationErrors.append("Given name not found in the welcome message.\n");
	    }
	    
	    driver.get(baseUrl + "/");
	    driver.findElement(By.id("textbox")).clear();
	    driver.findElement(By.id("textbox")).sendKeys("James");
	    driver.findElement(By.id("button")).click();
	    String s1 = driver.findElement(By.cssSelector("h1")).getText();
	    
	    try {
	      assertTrue(s1.contains("James"));
	      passCount++;
	    } catch (Error e) {
	      verificationErrors.append("Given name not found in the welcome message.\n");
	    }
	    
	  } catch (IllegalStateException ise) {
	      verificationErrors.append("Testcase Failed - Flow interrupted - (Result Page May not be available or not html page) \n");
	  } catch (Exception exception) {
	  	System.out.println(exception);
	      verificationErrors.append("Testcase Failed \n"+exception);
	  }
  }
  
  @After
  public void tearDown() throws Exception {
	  System.out.println("#"+Math.round	((passCount/(float)totalCount)*100));
    driver.quit();
    String verificationErrorString = verificationErrors.toString();
    if (!"".equals(verificationErrorString)) {
      fail(verificationErrorString);
    }
  }

  private boolean isElementPresent(By by) {
    try {
      driver.findElement(by);
      return true;
    } catch (NoSuchElementException e) {
      return false;
    }
  }

  private boolean isAlertPresent() {
    try {
      driver.switchTo().alert();
      return true;
    } catch (NoAlertPresentException e) {
      return false;
    }
  }

  private String closeAlertAndGetItsText() {
    try {
      Alert alert = driver.switchTo().alert();
      String alertText = alert.getText();
      if (acceptNextAlert) {
        alert.accept();
      } else {
        alert.dismiss();
      }
      return alertText;
    } finally {
      acceptNextAlert = true;
    }
  }
}

