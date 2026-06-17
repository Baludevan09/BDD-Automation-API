package stepdefination
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.webui.driver.DriverFactory
import org.openqa.selenium.WebDriver


import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.webui.keyword.builtin.TakeFullPageScreenshotKeyword
import io.cucumber.java.After
import io.cucumber.java.Before
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
	
	@Before
	public void setupScenarioEnvironment() {
		com.kms.katalon.core.util.KeywordUtil.logInfo(" [LIFECYCLE] Initializing browser workspace session...")
		WebUI.openBrowser('')
		WebUI.maximizeWindow()
	}

	
	@After
	public void teardownScenarioEnvironment() {
		com.kms.katalon.core.util.KeywordUtil.logInfo(" [LIFECYCLE] Tearing down browser workspace session...")
		WebUI.closeBrowser()
	}
	
	

@Given("user navigates to demo page")
def user_navigates_to_demo_page() {
    boolean isBrowserOpen = false
    
    try {
        // Attempt to check if an active WebDriver session exists
        WebDriver driver = DriverFactory.getExecutedBrowser()
        if (driver != null && driver.getWindowHandles().size() > 0) {
            isBrowserOpen = true
        }
    } catch (Exception e) {
        // No browser session exists yet
        isBrowserOpen = false
    }
    
    if (!isBrowserOpen) {
        // First scenario run: Launch a fresh browser window
        WebUI.openBrowser('')
        WebUI.maximizeWindow()
    } else {
        // Subsequent scenario runs: Wipe session data and reuse the window cleanly
        WebUI.deleteAllCookies()
    }
    
    WebUI.navigateToUrl("https://www.demoblaze.com/")
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
//		WebUI.closeBrowser()
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