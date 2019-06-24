package utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.IOException;

public class JsonUtils {
    private static ObjectMapper getObjectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

        return objectMapper;
    }

    public static <T> T readFromJson(String json, Class<T> clazz) throws IOException {
        return getObjectMapper().readValue(json, clazz);
    }

    public static <T> T readFromJson(String json, TypeReference typeReference) throws IOException {
        return getObjectMapper().readValue(json, typeReference);
    }

    public static <T> String writeToJson(T obj) {
        try {
            return getObjectMapper().writeValueAsString(obj);
        } catch (IOException e) {
            return "";
        }
    }
}
