package pages

import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.util.KeywordUtil
import com.kms.katalon.core.exception.StepFailedException

public class ProductAndCartPage extends BasePage {

	// Locators mapping
	private static final String itemSamsung    = 'Object Repository/Page_React Calculator/DEmo/samsungphone'
	private static final String btnAddCart     = 'Object Repository/Page_React Calculator/DEmo/addtocart'
	private static final String navCart        = 'Object Repository/Page_React Calculator/DEmo/cartinmainwindow'
	private static final String btnPlaceOrder  = 'Object Repository/Page_React Calculator/DEmo/placeorder'

	// Order Form Elements
	private static final String inName         = 'Object Repository/Page_React Calculator/DEmo/Name1'
	private static final String inCountry      = 'Object Repository/Page_React Calculator/DEmo/country1'
	private static final String inCity         = 'Object Repository/Page_React Calculator/DEmo/city1'
	private static final String inCard         = 'Object Repository/Page_React Calculator/DEmo/creditcard'
	private static final String inMonth        = 'Object Repository/Page_React Calculator/DEmo/month'
	private static final String inYear         = 'Object Repository/Page_React Calculator/DEmo/year1'
	private static final String btnPurchase    = 'Object Repository/Page_React Calculator/DEmo/purchasebtn'
	private static final String txtOrderInfo   = 'Object Repository/Page_React Calculator/DEmo/userdetails'
	private static final String ok_btn         = 'Object Repository/Page_React Calculator/DEmo/Ok'

	public void addSamsungToCart() {
		try {

			scrollToElement(itemSamsung)
			doubleClick(itemSamsung)
			WebUI.delay(5)
		} catch (Exception e) {
			WebUI.takeFullPageScreenshot()
			KeywordUtil.markFailedAndStop("❌ [ERROR] Catalog interaction failed while adding Samsung phone to cart: " + e.getMessage())
			throw new StepFailedException("Cart add failed", e)
		}
	}

	public void navigateToCartAndCheckout() {
		try {
			click(navCart)
			click(btnPlaceOrder)
			WebUI.delay(2)
		} catch (Exception e) {
			WebUI.takeFullPageScreenshot()
			KeywordUtil.markFailedAndStop("❌ [ERROR] Navigation roadblock occurred going to cart checkout pipeline: " + e.getMessage())
			throw new StepFailedException("Checkout initialization failed", e)
		}
	}

	/**
	 * Fills out the checkout form with rigorous mandatory field validation and verification
	 */
	public void fillCheckoutDetails(String name, String country, String city, String card, String month, String year) {
		try {
			// ==========================================
			// LAYER 1: DATA VALIDATION (Mandatory Fields)
			// ==========================================
			if (name == null || name.trim().isEmpty()) {
				com.kms.katalon.core.util.KeywordUtil.markFailedAndStop("❌ [Validation Error] 'Name' is a mandatory field and cannot be empty.")
			}
			if (card == null || card.trim().isEmpty() || card.trim().length() < 10) {
				com.kms.katalon.core.util.KeywordUtil.markFailedAndStop("❌ [Validation Error] 'Credit Card' is mandatory and must be a valid length (min 10 digits). Got: " + card)
			}

			// Fill out the form fields using inherited BasePage methods
			enterText(inName, name)
			enterText(inCountry, country)
			enterText(inCity, city)
			enterText(inCard, card)
			enterText(inMonth, month)
			enterText(inYear, year)


			String typedName = WebUI.getAttribute(com.kms.katalon.core.testobject.ObjectRepository.findTestObject(inName), 'value')
			String typedCard = WebUI.getAttribute(com.kms.katalon.core.testobject.ObjectRepository.findTestObject(inCard), 'value')

			if (!typedName.equals(name)) {
				com.kms.katalon.core.util.KeywordUtil.markFailedAndStop("❌ [Verification Failure] UI Name field value ('" + typedName + "') does not match expected input ('" + name + "').")
			}
			if (!typedCard.equals(card)) {
				com.kms.katalon.core.util.KeywordUtil.markFailedAndStop("❌ [Verification Failure] UI Card field value does not match expected input sequence.")
			}

			com.kms.katalon.core.util.KeywordUtil.logInfo("✔ Form verification passed. Proceeding to purchase.")


			WebUI.takeFullPageScreenshot('Include/Screenshots/userdetails_' + System.currentTimeMillis() + '.png')


			click(btnPurchase)
		} catch (Exception e) {
			WebUI.takeFullPageScreenshot('Include/Screenshots/Error_CheckoutForm_' + System.currentTimeMillis() + '.png')
			com.kms.katalon.core.util.KeywordUtil.markFailedAndStop("❌ [Critical Failure] Interrupted during checkout form execution: " + e.getMessage())
			throw new com.kms.katalon.core.exception.StepFailedException("Checkout form flow broken", e)
		}
	}

	public String getPurchaseReceiptDetails() {
		try {
			WebUI.waitForElementVisible(findTestObject(txtOrderInfo), 5)
			WebUI.takeFullPageScreenshot('Include/Screenshots/purchase_receipt.png')
			return WebUI.getText(findTestObject(txtOrderInfo))
		} catch (Exception e) {
			WebUI.takeFullPageScreenshot()
			KeywordUtil.markFailedAndStop("❌ [ERROR] Failed to capture purchase transaction confirmation: " + e.getMessage())
			throw new StepFailedException("Receipt validation tracking failed", e)
		}
	}

	public void clickOkBtn() {
		try {
			WebUI.delay(4)
			click(ok_btn)
		} catch (Exception e) {
			WebUI.takeFullPageScreenshot()
			KeywordUtil.markFailedAndStop("❌ [ERROR] Failed dismissing the purchase success alert dialog modal: " + e.getMessage())
			throw new StepFailedException("Confirmation cleanup processing failed", e)
		}
	}
}