package tqs.bus.unitTests;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import tqs.bus.services.CurrencyService;

class CacheTests {
    
    private static CurrencyService currencyService = new CurrencyService(2000);

    @Test
    @DisplayName("Test if cache is invalid after ttl")
    void testIfCacheIsInvalidAfterTTL() throws Exception {
        currencyService.exchange("EUR", "USD");
        assertTrue(currencyService.isCacheValid());

        Thread.sleep(2000);
        assertFalse(currencyService.isCacheValid());
    }

    @Test
    @DisplayName("Test if cache is valid during ttl")
    void testIfCacheIsValidDuringTTL() throws Exception {
        currencyService.exchange("EUR", "USD");
        assertTrue(currencyService.isCacheValid());

        Thread.sleep(1000);
        assertTrue(currencyService.isCacheValid());
    }

    @Test
    @DisplayName("Test list currencies")
    void testListCurrencies() throws Exception {
        assertTrue(currencyService.listCurrencies().contains("USD"));
        assertTrue(currencyService.listCurrencies().contains("EUR"));
    }

}
