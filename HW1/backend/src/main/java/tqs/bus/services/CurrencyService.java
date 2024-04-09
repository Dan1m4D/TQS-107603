package tqs.bus.services;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Service
public class CurrencyService {

    private static final Logger logger = LoggerFactory.getLogger(CurrencyService.class);
    private final HttpClient httpClient = HttpClient.newBuilder().build();

    private Set<String> currencies;
    private Map<String, Object> cachedRates = new HashMap<>();
    private int cacheTTL = 3600 * 1000; // 1 hour
    private long lastCaching = 0;
    private String apiKey = "3c3da1cd491ed199ff510778";

    public CurrencyService() {
    }

    public CurrencyService(int ttl) {
        cacheTTL = ttl;
        cachedRates = new HashMap<>();
    }

    public boolean cacheExchangeRates(Map<String, Object> rates) {
        cachedRates = rates;
        lastCaching = System.currentTimeMillis();
        return true;
    }

    public boolean isCacheValid() {
        return !cachedRates.isEmpty() && lastCaching != 0 && System.currentTimeMillis() < lastCaching + cacheTTL;
    }

    public Set<String> listCurrencies() {
        try {
            if (currencies == null)
                exchange("EUR", "USD");
        } catch (Exception e) {
            logger.error("Error occurred while listing currencies: {}", e.getMessage());
        }
        return currencies;
    }

    public double exchange(String from, String to) throws Exception {
        if (isCacheValid()) {
            return Double.parseDouble(cachedRates.get(to).toString());
        } else {
            try {
                String apiLink = "https://v6.exchangerate-api.com/v6/" + apiKey + "/latest/" + from;
                String content = doRequest(apiLink);
                JSONObject obj = new JSONObject(content);
                cacheExchangeRates(obj.getJSONObject("conversion_rates").toMap());
                currencies = obj.getJSONObject("conversion_rates").keySet();
                return obj.getJSONObject("conversion_rates").getDouble(to);
            } catch (Exception e) {
                logger.error("Error occurred while exchanging currency from {} to {}: {}", from, to, e.getMessage());
                throw new Exception("Currency not found");
            }
        }
    }

    public String doRequest(String link) {
        try {
            logger.info("Making HTTP request to: {}", link);
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(link))
                    .GET()
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() != HttpURLConnection.HTTP_OK) {
                logger.error("HTTP request failed with status code: {}", response.statusCode());
                throw new IOException("HTTP request failed with status code: " + response.statusCode());
            }
            logger.info("HTTP request succeeded with status code: {}", response.statusCode());
            return response.body();
        } catch (IOException | InterruptedException e) {
            logger.error("Error occurred while making HTTP request: {}", e.getMessage());
            return null;
        }
    }
}
