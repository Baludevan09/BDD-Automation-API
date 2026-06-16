package pages

import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.util.KeywordUtil
import com.kms.katalon.core.exception.StepFailedException

public class LogoutPage extends BasePage {

	private static final String btn_logout = 'Object Repository/Page_React Calculator/DEmo/logOutBtn'

	public void logOutTheApplication() {
		try {
			//            waitForPageToLoad(10)
			WebUI.delay(6)
			click(btn_logout)
		} catch (Exception e) {
			WebUI.takeFullPageScreenshot()
			KeywordUtil.markFailedAndStop("❌ [ERROR] Application logout action encountered a failure: " + e.getMessage())
			throw new StepFailedException("Logout failed", e)
		}
	}
}