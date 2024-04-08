package tqs.bus.services;

import org.springframework.stereotype.Service;

@Service
public class TicketFieldValidator {

    public boolean validateEmail(String email) {
        return email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");
    }

    public boolean validatePhone(int phone) {
        return String.valueOf(phone).matches("^[0-9]{9}$");
    }

}
