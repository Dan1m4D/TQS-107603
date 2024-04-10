Feature: Buying tickets
    Scenario: User buys a ticket from Aveiro to Mirandela
        Given User is on the homepage
        When User selects Aveiro as origin and Mirandela as destination
        Then User should see available trips
        When User selects a trip
        And User selects a seat
        And User enters name and email
        Then User buys the ticket
        
