package pages

import com.kms.katalon.core.exception.StepFailedException
import com.kms.katalon.core.util.KeywordUtil
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.model.FailureHandling

public class Signuppage extends BasePage {

	// Object Repository Locator Paths
	private static final String Signupbtn        = 'Object Repository/Page_React Calculator/DEmo/Sighnup'
	private static final String SubmitSignupbtn  = 'Object Repository/Page_React Calculator/DEmo/SighnUpbtn_2'
	private static final String Signuppassword   = 'Object Repository/Page_React Calculator/DEmo/SighnupPassword'
	private static final String Signupusername   = 'Object Repository/Page_React Calculator/DEmo/SighnUpusername'

	// Thread-Safe Storage for Automatically Generated Credentials
	private static final ThreadLocal<String> generatedUsername = new ThreadLocal<>()
	private static final ThreadLocal<String> generatedPassword = new ThreadLocal<>()

	// ✔️ FIXED: Explicitly declared as static methods to align with ThreadLocal variable lifecycle
	public static String retrieveUsername() {
		return generatedUsername.get()
	}

	public static String retrievePassword() {
		return generatedPassword.get()
	}

	public void clickSignupNav() {
		try {
			WebUI.delay(2)
			doubleClick(Signupbtn)
			KeywordUtil.logInfo("✔ Clicked Home Page SignUp Anchor Element")
		} catch (Exception e) {
			KeywordUtil.markFailedAndStop("❌ [ERROR] Failed to open signup modal: " + e.getMessage())
			throw new StepFailedException("Signup navigation failed", e)
		}
	}

	public void generateAndEnterUsername() {
		try {
			String dynamicUser = "User_" + System.currentTimeMillis()
			generatedUsername.set(dynamicUser)
			KeywordUtil.logInfo(" Automatically Generated Signup Username: " + dynamicUser)
			enterText(Signupusername, dynamicUser)
		} catch (Exception e) {
			handleException("Automatically generating and entering username", e)
		}
	}

	public void generateAndEnterPassword() {
		try {
			List<String> poolChars = []
			poolChars.addAll('A'..'Z')
			poolChars.addAll('a'..'z')
			poolChars.addAll('0'..'9')

			String characterPool = poolChars.join()
			String dynamicPassword = (1..12).collect { characterPool[(int) (Math.random() * characterPool.length())] }.join()
			generatedPassword.set(dynamicPassword)

			KeywordUtil.logInfo(" Automatically Generated Signup Password: " + dynamicPassword)
			enterText(Signuppassword, dynamicPassword)
		} catch (Exception e) {
			handleException("Automatically generating and entering password", e)
		}
	}

	/**
	 * Submits the registration form and dismisses the native "Sign up successful" browser alert dialog
	 */
	public void submitSignupForm() {
		try {
			click(SubmitSignupbtn)
			KeywordUtil.logInfo("Form submitted. Waiting for registration confirmation alert modal...")

			// Wait up to 5 seconds for the native browser confirmation alert to pop up
			int retryCounter = 0
			boolean alertHandled = false

			while (retryCounter < 5 && !alertHandled) {
				if (WebUI.verifyAlertPresent(5, FailureHandling.OPTIONAL)) {
					String alertMessageText = WebUI.getAlertText()
					KeywordUtil.logInfo("✔ Native browser verification pop-up intercepted: " + alertMessageText)

					WebUI.acceptAlert()
					alertHandled = true
				} else {
					WebUI.delay(1)
					retryCounter++
				}
			}

			println("💾 [DATABASE RECORD] Account Form Committed: Username=[" + retrieveUsername() + "] Password=[" + retrievePassword() + "]")
		} catch (Exception e) {
			KeywordUtil.markFailedAndStop("❌ [ERROR] Failed to submit registration form: " + e.getMessage())
			throw new StepFailedException("Signup form submission failed", e)
		}
	}

	/**
	 * Automates the login flow using the dynamically auto-generated credentials saved in memory
	 */
	public void loginWithGeneratedCredentials(LoginPage loginPage) {
		try {
			// ✔️ FIXED: Accessing through explicit static reference hooks
			String runtimeUser = retrieveUsername()
			String runtimePassword = retrievePassword()

			if (runtimeUser == null || runtimePassword == null) {
				KeywordUtil.markFailedAndStop("❌ [Data Error] Cannot log in. Runtime credential strings are empty!")
			}

			KeywordUtil.logInfo("🔑 Executing UI Login Step for User: " + runtimeUser)

			loginPage.clickNavLogin()
			loginPage.enterUsername(runtimeUser)

			// Safe plaintext text insertion into the login page password object field
			WebUI.setText(com.kms.katalon.core.testobject.ObjectRepository.findTestObject('Object Repository/Page_React Calculator/DEmo/password'), runtimePassword)

			WebUI.takeFullPageScreenshot()
			loginPage.submitLogin()
		} catch (Exception e) {
			WebUI.takeFullPageScreenshot()
			KeywordUtil.markFailedAndStop("❌ [ERROR] Failed to execute login using auto-generated credentials: " + e.getMessage())
			throw new StepFailedException("Login with auto-generated credentials failed", e)
		}
	}
}