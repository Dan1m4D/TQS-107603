package tqs.bus.controllerTests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import tqs.bus.controllers.CurrencyController;
import tqs.bus.services.CurrencyService;

import java.util.Set;
import java.util.HashSet;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.hasSize;

@WebMvcTest(CurrencyController.class)
class CurrencyControllerTest {

    @Autowired
    MockMvc mvc;

    @MockBean
    private CurrencyService currencyService;

    @BeforeEach
    public void setUp() throws Exception {
    }

    @Test
    @DisplayName("Test when list currencies then return all currencies")
    void whenListCurrencies_thenReturnAllCurrencies() throws Exception {
        Set<String> currencies = new HashSet<>();
        currencies.add("USD");
        currencies.add("EUR");

        when(currencyService.listCurrencies()).thenReturn(currencies);

        mvc.perform(get("/currencies/list").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0]", is("EUR")))
                .andExpect(jsonPath("$[1]", is("USD")));

        verify(currencyService, times(1)).listCurrencies();
    }

    @Test
    @DisplayName("Test when exchange currency then return exchange rate")
    void whenExchangeCurrency_thenReturnExchangeRate() throws Exception {
        double exchangeRate = 0.85;

        when(currencyService.exchange("EUR", "USD")).thenReturn(exchangeRate);

        mvc.perform(get("/currencies/exchange?from=EUR&to=USD").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(String.valueOf(exchangeRate)));

        verify(currencyService, times(1)).exchange("EUR", "USD");
    }
}
