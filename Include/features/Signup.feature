@Signupall
Feature: Signup for a new user

  Background: Setup Browser Environment
    Given user navigates to demo page

  @signup @combo
  Scenario: Purchase a phone using valid details and logout from the application
    Given User click signup btn in home page
    Then User enter signup username
    And User enters signup password
    And User click signup button
    Then User logs in with the auto generated username and password
    And verify entered user name is correct
    And click the samsung phone and again click Add to cart button
    And click the cart button and place order button
    And Enter valid details for purchase and click purchase btn
    Then User able to view purchase details with the order id
    Then User click ok button

  @signup1
  Scenario: Purchase a phone using valid details and logout from the application with explicit logout
   Given User click signup btn in home page
    Then User enter signup username
    And User enters signup password
    And User click signup button
    Then User logs in with the auto generated username and password
    And verify entered user name is correct
    And click the samsung phone and again click Add to cart button
    And click the cart button and place order button
    And Enter valid details for purchase and click purchase btn
    Then User able to view purchase details with the order id
    Then User click ok button
    And click on the logout button