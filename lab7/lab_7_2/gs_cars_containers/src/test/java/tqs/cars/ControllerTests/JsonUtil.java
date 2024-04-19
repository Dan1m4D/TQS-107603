package tqs.cars.ControllerTests;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtil {

    public static String toJson(Object object) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(object);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
