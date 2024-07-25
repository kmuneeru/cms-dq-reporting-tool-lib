package tech.paramount.cmsdq.crt.worker.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.experimental.Delegate;

import java.io.IOException;
import java.io.InputStream;

public class JsonMapper {
    @Delegate
    private final ObjectMapper objectMapperDelegate;

    public JsonMapper() {
        objectMapperDelegate = new ObjectMapper();
        findAndRegisterModules();
        objectMapperDelegate.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true); //TODO - replace deprecated
    }

    public String toJson(Object object) {
        try {
            return writeValueAsString(object);
        } catch (JsonProcessingException e) {
            String message = String.format("Error mapping class %s object to JSON format!", object.getClass().getName());
//            log.error(message, e);
            throw new JsonMapperException(message, e);
        }
    }

    public String toPrettyJson(Object object) {
        try {
            return writerWithDefaultPrettyPrinter().writeValueAsString(object);
        } catch (JsonProcessingException e) {
            String message = String.format("Error mapping class %s object to JSON format!", object.getClass().getName());
//            log.error(message, e);
            throw new JsonMapperException(message, e);
        }
    }

    public <T> T parseJson(String object, Class<T> clazz) {
        try {
            return readValue(object, clazz);
        } catch (JsonProcessingException e) {
            String message = String.format("Error reading class %s object from JSON: %s", clazz.getName(), e.getMessage());
//            log.error(message, e);
            throw new JsonMapperException(message, e);
        }
    }

    public <T> T parseJson(InputStream object, Class<T> clazz) {
        try {
            return readValue(object, clazz);
        } catch (IOException e) {
            String message = String.format("Error reading class %s object from JSON: %s", clazz.getName(), e.getMessage());
//            log.error(message, e);
            throw new JsonMapperException(message, e);
        }
    }
}
