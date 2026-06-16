package operations

import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import io.cucumber.java.*
import io.cucumber.java.Scenario;
import drivers.DriverManager

public class Hooks {

	@Before
	public void setup(Scenario scenario) {
		println("🚀 Starting Scenario: " + scenario.getName())
//		DriverManager.launchApplication()
	}

	@After
	public void tearDown(Scenario scenario) {
		/*		if (scenario.isFailed()) {
			try {
				// Inject screenshot directly into the Cucumber scenario context for Extent to pick up
				final byte[] screenshot = ((org.openqa.selenium.TakesScreenshot) com.kms.katalon.core.webui.driver.DriverFactory.getWebDriver()).getScreenshotAs(org.openqa.selenium.OutputType.BYTES)
				scenario.embed(screenshot, "image/png")
				println("📸 Failure Screenshot successfully bound to Extent Report step trace.")
			} catch (Exception e) {
				println("⚠️ Failed to take Extent screenshot embed: " + e.getMessage())
			}
		}

		DriverManager.quitBrowser()*/
		println("🏁 Ending Scenario: " + scenario.getName())
	}
}