package Utilities;

import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.twilio.Twilio;
import com.twilio.base.ResourceSet;
import com.twilio.rest.api.v2010.account.Message;

public class OTP 
{
	public static final String 	ACCOUNT_SID="AC456364750bbce87f7d7c1a512983447e";
	public static final String AUTH_TOKEN="f78423be00a87fe36934fad275b55bd6";

	public static void main(String[] args) 
	{
		System.setProperty("webdriver.chrome.driver", "./chromedriver.exe");
		WebDriver driver= new ChromeDriver();
		driver.manage().window().maximize();
		driver.get("https://www.amazon.in");
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		driver.findElement(By.xpath("//span[contains(@class,'nav-line-2')][contains(text(),'Account & Lists')]")).click();
		WebDriverWait wait = new WebDriverWait(driver, 20);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='nav-flyout-ya-newCust']//a[@class='nav-a'][contains(text(),'Start here.')]")));
		driver.findElement(By.xpath("//div[@id='nav-flyout-ya-newCust']//a[@class='nav-a'][contains(text(),'Start here.')]")).click();
		driver.findElement(By.xpath("//input[@id='ap_customer_name']")).sendKeys("pravinkumar");
		driver.findElement(By.id("auth-country-picker-container")).click();

		driver.findElement(By.xpath("//ul[@role='application']//li/a[contains(text(),'United States +1')]")).click();
		driver.findElement(By.id("ap_phone_number")).sendKeys("7347269137");
		driver.findElement(By.id("ap_password")).sendKeys("TestAutomation@123");
		driver.findElement(By.id("continue")).click();
		
		// for getting OTP using Twilio APIS

		Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
		String smsBody=getMessage();
		System.out.println(smsBody);
		String OTpNumber=smsBody.replaceAll("[^0-9]", " ");
		System.out.println(OTpNumber);
		driver.findElement(By.id("auth-pv-enter-code")).sendKeys(OTpNumber);

		
		
	}
	public static String getMessage() {
		return getMessages().filter(m -> m.getDirection().compareTo(Message.Direction.INBOUND) == 0)
				.filter(m -> m.getTo().equals("+17347269137")).map(Message::getBody).findFirst()
				.orElseThrow(IllegalStateException::new);
	}

	private static Stream<Message> getMessages() {
		ResourceSet<Message> messages = Message.reader(ACCOUNT_SID).read();
		return StreamSupport.stream(messages.spliterator(), false);
	}

}
