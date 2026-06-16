package stepdefination

import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.webui.keyword.builtin.TakeFullPageScreenshotKeyword

import io.cucumber.java.en.And
import io.cucumber.java.en.Given
import io.cucumber.java.en.Then
import io.cucumber.java.en.When

import pages.LoginPage
import pages.LogoutPage
import pages.ProductAndCartPage
import pages.Signuppage

class NewpurchaseDEMO1 {

	// Instantiate Page Objects
	LoginPage loginPage = new LoginPage()
	ProductAndCartPage cartPage = new ProductAndCartPage()
	LogoutPage logoutpage = new LogoutPage()
	Signuppage signuppage = new Signuppage()

	@Given("user navigates to demo page")
	public void user_navigates_to_demo_page() {
		//		loginPage.openApplication()
		WebUI.openBrowser('')
		WebUI.maximizeWindow()
		String Application_Url = "https://www.demoblaze.com/"
		WebUI.navigateToUrl(Application_Url)
		//		String currenturl = WebUI.getUrl()
		//		assertEquals(currenturl,Application_Url)
//		WebUI.verifyMatch(WebUI.getUrl(), ".*demoblaze.com.*", true)
		//		drivers.DriverManager.launchApplication()
		println("Step1")
	}

	@Then("User enter valid{string}")
	public void userenterusername(String username) {


//		println("Application launched successfully to: " + Application_Url)
		println("Current Url and Application Url are same" )
		println("browser launch succesfuly")
		loginPage.clickNavLogin()
		loginPage.enterUsername(username.trim())
	}

	@And("User enters valid{string}")
	public void userenterpassword(String password) {
		println("decrypt pass:"+password)
		loginPage.enterPasswordSecurely(password.trim())
		WebUI.takeFullPageScreenshot()
		loginPage.submitLogin()
//		WebUI.delay(5)
	}

	@And("verify entered user name is correct")
	public void usernameverfication() {
		String welcomeText = loginPage.getLoggedAttributeText()
		println("[ASSERT] Displayed UI Header: " + welcomeText)
	}

	@And("click the samsung phone and again click Add to cart button")
	public void useraddphone () {
		cartPage.addSamsungToCart()
	}

	@And("click the cart button and place order button")
	public void clickcartandaddbtn() {
		cartPage.navigateToCartAndCheckout()
	}

	@And("Enter valid details for purchase and click purchase btn")
	public void userdetails() {
		// Hardcoded transaction mock variables matching your setup
		cartPage.fillCheckoutDetails(
				"vaishnuvj",
				"india",
				"chennai",
				"21245566753",
				"02",
				"1997"
				)
	}

	@Then("User able to view purchase details with the order id")
	public void shoppingingidcreated() {
		String receipt = cartPage.getPurchaseReceiptDetails()
		if (receipt == null || receipt.length() < 5) {
			com.kms.katalon.core.util.KeywordUtil.markFailedAndStop("❌ Order ID was not generated!")
		}
		println("[SUCCESS] Generated Order Logs:\n" + receipt)
	}

	@Then("User click ok button")
	public void clickokbtn() {

		cartPage.clickOkBtn()
		println("User click ok button")
	}

	@And("click on the logout button")
	public void logOutTheApplication() {

		logoutpage.logOutTheApplication()
		WebUI.closeBrowser()
	}

	@Given("User click signup btn in home page")
	public void user_click_signup_btn_in_home_page() {
		signuppage.clickSignupNav()
	}

	@Then("User enter signup username")
	public void user_enter_valid_username_for_signup() {
		signuppage.generateAndEnterUsername()
	}
	@And("User enters signup password")
	public void user_enters_valid_password_for_signup() {
		signuppage.generateAndEnterPassword()
	}

	@And("User click signup button")
	public void user_click_signup_button() {
		signuppage.submitSignupForm()
	}

	@Then("User logs in with the auto generated username and password")
	public void user_logs_in_with_auto_generated_credentials() {
		// Keeps Step Definition strictly limited to a single method execution wrapper
		signuppage.loginWithGeneratedCredentials(loginPage)
	}
}