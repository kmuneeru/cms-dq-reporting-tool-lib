package tech.paramount.cmsdq.crt.worker.util;

public class JsonMapperException extends  RuntimeException {
    public JsonMapperException(String message) {
        super(message);
    }

    public JsonMapperException(String message, Throwable e) {
        super(message, e);
    }
}
