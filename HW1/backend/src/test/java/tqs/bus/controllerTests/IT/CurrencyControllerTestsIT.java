package tqs.bus.controllerTests.IT;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Set;
import java.util.HashSet;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CurrencyControllerIntegrationTest {

    private int port = 8080;

    @Autowired
    private TestRestTemplate restTemplate;

    @BeforeEach
    void setUp() {
    }

    @Test
    @DisplayName("Test when list currencies then return all currencies")
    void whenListCurrencies_thenReturnAllCurrencies() {
        Set<String> currencies = new HashSet<>();
        currencies.add("USD");
        currencies.add("EUR");

        ResponseEntity<Set> response = restTemplate.getForEntity("http://localhost:" + port + "/currencies/list",
                Set.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody()).contains("USD", "EUR");
    }

    @Test
    @DisplayName("Test when exchange currency then return exchange rate")
    void whenExchangeCurrency_thenReturnExchangeRate() {

        ResponseEntity<Double> response = restTemplate
                .getForEntity("http://localhost:" + port + "/currencies/exchange?from=EUR&to=USD", Double.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
    
    }

    @Test
    @DisplayName("Test when exchange currency twice then return the same exchange rate")
    void whenExchangeCurrencyTwice_thenReturnSameExchangeRate() {

        ResponseEntity<Double> response1 = restTemplate
                .getForEntity("http://localhost:" + port + "/currencies/exchange?from=EUR&to=USD", Double.class);

        assertThat(response1.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response1.getBody()).isNotNull();

        ResponseEntity<Double> response2 = restTemplate
                .getForEntity("http://localhost:" + port + "/currencies/exchange?from=EUR&to=USD", Double.class);

        assertThat(response2.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response2.getBody()).isNotNull();
        
        assertThat(response1.getBody()).isEqualTo(response2.getBody());
    }

}
