Feature: purchase a phone

 Background: Setup Browser Environment
    Given user navigates to demo page

@validdetails @positive @smoke @combo
  Scenario Outline: Purchase a phone using valid details and logout from the application
    
    Then User enter valid"<username>"
    And User enters valid"<password>"
    And verify entered user name is correct
    And click the samsung phone and again click Add to cart button
    And click the cart button and place order button
    And Enter valid details for purchase and click purchase btn
    Then User able to view purchase details with the order id
    And User click ok button
    #And click on the logout button

    Examples: 
      | username          | password               |
      | vaishnuvj         |/ylYYRUa4DIpCOGbpOf4bQ==|
      
      
  @invaliddetails @negative 
  
  Scenario Outline: Authentication should fail with missing or incorrect credentials
  
   # Given user navigates to demo page
    Then User enter valid"<username>"
    And User enters valid"<password>"
    And verify entered user name is correct
    And click the samsung phone and again click Add to cart button
    And click the cart button and place order button
    And Enter valid details for purchase and click purchase btn
    Then User able to view purchase details with the order id
    And User click ok button
    And click on the logout button

    Examples: 
       | username          | password               |
       |                   |/ylYYRUa4DIpCOGbpOf4bQ==|
          
      
    @validdetails2  @combo
  Scenario Outline: Purchase a phone using valid details and logout from the application
    
    Then User enter valid"<username>"
    And User enters valid"<password>"
    And verify entered user name is correct
    And click the samsung phone and again click Add to cart button
    And click the cart button and place order button
    And Enter valid details for purchase and click purchase btn
    Then User able to view purchase details with the order id
    And User click ok button
    #And click on the logout button

    Examples: 
      | username          | password               |
      | maluventhivaishnu |umo2YAq0jdK6FP1VPvIxvg==|
      
      
        
  
 