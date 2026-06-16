package drivers

import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import static org.junit.Assert.assertEquals

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.checkpoint.Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testcase.TestCase
import com.kms.katalon.core.testdata.TestData
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import internal.GlobalVariable

public class DriverManager {

	// Made final since the base URL remains constant
	private static final String Application_Url = "https://www.demoblaze.com/"


	//	  Launches the browser, maximizes it, and navigates to the application URL.

	@Keyword
	public static void launchApplication() {
		WebUI.openBrowser('')
		WebUI.maximizeWindow()
		WebUI.navigateToUrl(Application_Url)
		//		String currenturl = WebUI.getUrl()
		//		assertEquals(currenturl,Application_Url)
		WebUI.verifyMatch(WebUI.getUrl(), ".*demoblaze.com.*", true)

		println("Application launched successfully to: " + Application_Url)
		println("Current Url and Application Url are same" )
	}


	//	  Closes the active browser session.

	@Keyword
	public static void quitBrowser() {
		WebUI.closeBrowser()
		println("Browser closed successfully")
	}
}