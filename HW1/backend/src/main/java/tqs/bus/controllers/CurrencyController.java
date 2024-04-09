package tqs.bus.controllers;

import org.springframework.web.bind.annotation.RestController;

import tqs.bus.services.CurrencyService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Set;

@RestController
@RequestMapping("/currencies")
@CrossOrigin("*")
public class CurrencyController {

    private static final Logger logger = LoggerFactory.getLogger(CurrencyController.class);

    @Autowired
    private CurrencyService currencyService;

    @GetMapping("/list")
    public ResponseEntity<Set<String>> listCurrencies() {
        try {
            logger.info("Fetching list of currencies");
            return ResponseEntity.ok(currencyService.listCurrencies());
        } catch (Exception e) {
            logger.error("Error occurred while fetching currencies: {}", e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error occurred while fetching currencies");
        }
    }

    @GetMapping("/exchange")
    public ResponseEntity<Double> exchange(String from, String to) {
        try {
            logger.info("Exchanging currency from {} to {}", from, to);
            return ResponseEntity.ok(currencyService.exchange(from, to));
        } catch (Exception e) {
            logger.error("Error occurred while performing currency exchange: {}", e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error occurred while performing currency exchange");
        }
    }
}
