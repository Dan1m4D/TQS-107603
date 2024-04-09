package tqs.bus.unitTests;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import tqs.bus.controllers.TicketController;

class ValidationTest {
    
    private static TicketController controller;

    @Test
    @DisplayName("Test email validation")
    void testEmailValidation() {
        assertTrue(controller.validateEmail("daniel.madureira@ua.pt"));
    }

    @Test
    @DisplayName("Test invalid email validation")
    void testInvalidEmailValidation() {
        assertFalse(controller.validateEmail("daniel.madureiraua.pt"));
        assertFalse(controller.validateEmail("daniel.madureira@uapt"));
    }
    
}
