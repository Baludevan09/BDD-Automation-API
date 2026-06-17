package pages

import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.exception.StepFailedException
import com.kms.katalon.core.util.KeywordUtil
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.util.CryptoUtil

public class LoginPage extends BasePage {

	// Locators mapping
	private static final String loginNavigationBtn = 'Object Repository/DEMO/loginbtn'
	private static final String usernameField      = 'Object Repository/Page_React Calculator/DEmo/name'
	private static final String passwordField      = 'Object Repository/Page_React Calculator/DEmo/password'
	private static final String submitLoginBtn     = 'Object Repository/Page_React Calculator/DEmo/loginbtn_inaddcart'
	private static final String welcomeUserDisplay = 'Object Repository/Page_React Calculator/DEmo/loginusername'

	public void clickNavLogin() {
		try {
			//			WebUI.delay(3)
			doubleClick(loginNavigationBtn)
		} catch (Exception e) {
			WebUI.takeFullPageScreenshot()
			KeywordUtil.markFailedAndStop("❌ [ERROR] Failed to double-click the navigation login button: " + e.getMessage())
			throw new StepFailedException("Navigation login failed", e)
		}
	}

	public void enterUsername(String username) {
    try {
        enterText(usernameField, username)
    } catch (com.kms.katalon.core.exception.StepFailedException e) {
        
        throw e;
    } catch (Exception e) {
        
        try { WebUI.takeScreenshot(); } catch(Exception ex){}
        KeywordUtil.markFailedAndStop("❌ [ERROR] Failed to input username '" + username + "': " + e.getMessage());
        throw new com.kms.katalon.core.exception.StepFailedException("Username entry failed", e);
    }
}

	public void enterPasswordSecurely(String encryptedPassword) {
		try {
						WebUI.delay(5)
			 doubleClick(passwordField)
//			enterEncryptedText(passwordField, encryptedPassword)
			def decryptedText = (CryptoUtil.decode(CryptoUtil.getDefault(encryptedPassword)))
			WebUI.setText(findTestObject(passwordField), decryptedText)
		} catch (Exception e) {
			captureScreenshot()
			KeywordUtil.markFailedAndStop("❌ [ERROR] Failed to securely input encrypted password: " + e.getMessage())
			throw new StepFailedException("Password entry failed", e)
		}
	}

	public void submitLogin() {
		try {
			//			WebUI.delay(5)
			click(submitLoginBtn)
		} catch (Exception e) {
			captureScreenshot()
			KeywordUtil.markFailedAndStop("❌ [ERROR] Failed clicking the login submit button: " + e.getMessage())
			throw new StepFailedException("Login submission failed", e)
		}
	}

	public String getLoggedAttributeText() {
		try {
			scrollToElement(welcomeUserDisplay)

			return WebUI.getText(findTestObject(welcomeUserDisplay))
			println(welcomeUserDisplay)
			//			return getText(welcomeUserDisplay)
		} catch (Exception e) {
			captureScreenshot()
			KeywordUtil.markFailedAndStop("❌ [ERROR] Failed to read welcome text banner: " + e.getMessage())
			throw new StepFailedException("Verification read failed", e)
		}
	}
}