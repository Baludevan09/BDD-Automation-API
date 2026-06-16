package pages

import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.exception.StepFailedException
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.util.KeywordUtil
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

public class BasePage {

	/**
	 * Common Exception Handler
	 */
	protected void handleException(String action, Exception e) {
		WebUI.takeFullPageScreenshot()
		KeywordUtil.markFailedAndStop(
				"❌ [ERROR] " + action + " failed. Reason: " + e.getMessage()
				)
		throw new StepFailedException(action + " failed", e)
	}

	/**
	 * Get Test Object
	 */
	protected TestObject getObject(String objectPath) {
		return findTestObject(objectPath)
	}

	/**
	 * Click Element
	 */
	protected void click(String objectPath) {
		try {
			WebUI.waitForElementClickable(getObject(objectPath), 10)
			WebUI.click(getObject(objectPath))
		} catch (Exception e) {
			handleException("Clicking element [" + objectPath + "]", e)
		}
	}

	/**
	 * Double Click Element
	 */
	/**
	 * Double Click Element with explicit wait and exception handling
	 * @param objectPath The repository path string of the Test Object (e.g., loginNavigationBtn)
	 */
	public void doubleClick(String objectPath) {
		try {
			// 1. Wait up to 5 seconds to ensure the element is completely ready for interaction
			WebUI.waitForElementClickable(findTestObject(objectPath), 5)

			// 2. Perform the native double-click action
			WebUI.doubleClick(findTestObject(objectPath))

			KeywordUtil.logInfo("🎯 Successfully double-clicked element: " + objectPath)
		} catch (Exception e) {
			// 3. Fallback: Trigger your central failure handler (screenshot, logger, and step stopper)
			handleFailure("DoubleClick", objectPath, e)
		}
	}

	/**
	 * Enter Text
	 */
	/**
	 * Enter text into an input field with strict validation, explicit waits, and try-catch handling
	 * @param objectPath The repository path string of the Test Object (e.g., usernameField)
	 * @param text The string data to enter into the field
	 */
	public void enterText(String objectPath, String text) {
		try {
			// 1. Validation Check: Stop the test immediately if the input data itself is missing
			if (text == null || text.trim().isEmpty()) {
				com.kms.katalon.core.util.KeywordUtil.markFailedAndStop("❌ [Validation Error] The text string provided for object path '" + objectPath + "' is completely empty or null.")
				throw new com.kms.katalon.core.exception.StepFailedException("Aborting step: Input text cannot be empty.")
			}

			// 2. Synchronization: Wait up to 10 seconds for the field to safely appear on the webpage
			WebUI.waitForElementVisible(com.kms.katalon.core.testobject.ObjectRepository.findTestObject(objectPath), 10)

			// 3. Clear existing values to prevent character appending issues on retries
			WebUI.clearText(com.kms.katalon.core.testobject.ObjectRepository.findTestObject(objectPath))

			// 4. Execution: Enter the text with strict failure enforcement
			WebUI.setText(com.kms.katalon.core.testobject.ObjectRepository.findTestObject(objectPath), text, com.kms.katalon.core.model.FailureHandling.STOP_ON_FAILURE)

			com.kms.katalon.core.util.KeywordUtil.logInfo("✍ Successfully typed value into field: " + objectPath)
		} catch (Exception e) {
			// 5. Exception Handling: Capture visual proof of why the interaction broke
			String screenshotName = "Error_EnterText_" + System.currentTimeMillis()
			takeScreenshot(screenshotName)

			// 6. Logging: Build a detailed error message inside the Katalon Log Viewer
			String runtimeMessage = "❌ [BasePage Exception] 'enterText' action completely failed on locator: " + objectPath + " | Error Reason: " + e.getMessage()
			com.kms.katalon.core.util.KeywordUtil.markFailedAndStop(runtimeMessage)

			// 7. Termination: Throw a hard step exception to force Cucumber to halt the execution chain immediately
			throw new com.kms.katalon.core.exception.StepFailedException(runtimeMessage, e)
		}
	}

	/**
	 * Enter Encrypted Password
	 */
	protected void enterEncryptedText(String objectPath, String value) {
		try {
			WebUI.waitForElementVisible(getObject(objectPath), 10)
			WebUI.setEncryptedText(getObject(objectPath), value)
		} catch (Exception e) {
			handleException("Entering encrypted text into [" + objectPath + "]", e)
		}
	}

	/**
	 * Enter Masked Text
	 */
	protected void enterMaskedText(String objectPath, String value) {
		try {
			WebUI.waitForElementVisible(getObject(objectPath), 10)
			WebUI.setMaskedText(getObject(objectPath), value)
		} catch (Exception e) {
			handleException("Entering masked text into [" + objectPath + "]", e)
		}
	}

	/**
	 * Get Text
	 */
	protected String getText(String objectPath) {
		try {
			WebUI.waitForElementVisible(getObject(objectPath), 10)
			return WebUI.getText(getObject(objectPath))
		} catch (Exception e) {
			handleException("Reading text from [" + objectPath + "]", e)
			return ""
		}
	}

	/**
	 * Scroll To Element
	 */
	protected void scrollTo(String objectPath) {
		try {
			WebUI.scrollToElement(getObject(objectPath), 5)
		} catch (Exception e) {
			handleException("Scrolling to [" + objectPath + "]", e)
		}
	}

	/**
	 * Wait Utility
	 */
	protected void waitForSeconds(int seconds) {
		WebUI.delay(seconds)
	}

	/**
	 * Screenshot Utility
	 */
	protected void captureScreenshot(String path = null) {
		if(path) {
			WebUI.takeFullPageScreenshot(path)
		} else {
			WebUI.takeFullPageScreenshot()
		}

		/**
		 * Scroll to Element with explicit visibility wait and exception handling
		 */
	}

	/**
	 * Scroll to Element with explicit visibility wait and exception handling
	 */
	/**
	 * Forcefully scrolls an element into view using native JavaScript execution
	 * @param objectPath The repository path string of the Test Object
	 */
	public void scrollToElement(String objectPath) {
		try {
			// 1. Wait up to 5 seconds to ensure the element is actually present in the DOM layout
			WebUI.waitForElementPresent(findTestObject(objectPath), 5)

			// 2. Use Katalon's Driver Factory to send a direct scroll command to the browser
			com.kms.katalon.core.webui.driver.DriverFactory.getWebDriver().executeScript(
					"arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});",
					WebUI.findWebElement(findTestObject(objectPath))
					)

			KeywordUtil.logInfo("📜 Successfully scrolled element into view via JavaScript: " + objectPath)
		} catch (Exception e) {
			// 3. Fallback to your central error handler if it still fails
			handleFailure("ScrollToElementJS", objectPath, e)
		}

		/**
		 * Waits for the entire browser page document structure to finish loading completely
		 * @param timeoutSeconds Max time to wait before throwing an error
		 */
	}
	public void waitForPageToLoad(int timeoutSeconds) {
		try {
			com.kms.katalon.core.util.KeywordUtil.logInfo("⏳ Waiting for page DOM resources to fully settle...")
			WebUI.waitForPageLoad(timeoutSeconds, com.kms.katalon.core.model.FailureHandling.STOP_ON_FAILURE)
		} catch (Exception e) {
			handleFailure("PageLoadTimeout", "Current Page View", e)
		}
	}
}


