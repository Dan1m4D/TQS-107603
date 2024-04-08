package tqs.bus.services;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Set;
import java.util.Map;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CurrencyService {

    private Set<String> currencies;
    private Map<String, Object> cachedRates = new HashMap<>();
    private int cacheTTL = 3600 * 1000; // 1 hour
    private long lastCaching = 0;
    private String apiKey = "3c3da1cd491ed199ff510778";

    @Autowired
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

    public Set<String> listCurrencies() throws Exception {
        if (currencies == null)
            exchange("EUR", "USD");
        return currencies;
    }

    public double exchange(String from, String to) throws Exception {
        if (isCacheValid()) {
            return Double.parseDouble(cachedRates.get(to).toString());
        } else {
            String api_link = "https://v6.exchangerate-api.com/v6/" + apiKey + "/latest/" + from;
            String content = doRequest(api_link);
            JSONObject obj = new JSONObject(content.toString());
            cacheExchangeRates(obj.getJSONObject("conversion_rates").toMap());
            currencies = obj.getJSONObject("conversion_rates").keySet();
            double rate;
            try {
                rate = obj.getJSONObject("conversion_rates").getDouble(to);
            } catch (Exception e) {
                throw new Exception("Currency not found");
            }
            return rate;
        }
    }

    public String doRequest(String link) throws Exception {
        URL url = new URL(link);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        StringBuilder content = new StringBuilder();
        String inputLine;
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        in.close();
        con.disconnect();
        return content.toString();
    }
}
