Feature: Buy a trip
    As a user
    I want to buy a trip
    So that I can travel

    Scenario: Successful trip purchase
        Given I am on the BlazeDemo homepage
        When I click on the "Buy a trip" button
        And I select the first trip
        And I click on the "Buy" button
        Then I should see the confirmation page with the correct details
