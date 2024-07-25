package tech.paramount.cmsdq.crt.worker.util;

public class JsonPathException extends  Exception {

    public JsonPathException() {

    }

    public JsonPathException(String errorMessage) {
        super(errorMessage);
    }

    public JsonPathException(Throwable cause) {
        super(cause);
    }

    public JsonPathException(String errorMessage, Throwable cause) {
        super(errorMessage, cause);
    }
}
