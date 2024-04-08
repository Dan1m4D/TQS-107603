package tqs.bus.controllers;

import org.springframework.web.bind.annotation.RestController;

import tqs.bus.services.CurrencyService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.http.ResponseEntity;
import java.util.Set;


@RestController
@RequestMapping("/currencies")
@CrossOrigin("*")
public class CurrencyController {


    @Autowired
    private CurrencyService currencyService;


    @GetMapping("/list")
    public ResponseEntity<Set<String>> listCurrencies() throws Exception {
        return ResponseEntity.ok(currencyService.listCurrencies());
    }

    @GetMapping("/exchange")
    public ResponseEntity<Double> exchange(String from, String to) throws Exception {
        return ResponseEntity.ok(currencyService.exchange(from, to));
    }

}
