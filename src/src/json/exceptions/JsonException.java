package json.exceptions;

public class JsonException extends RuntimeException {

    public JsonException(JsonExceptionType type, int start) {
        super(String.format("%s (pos : %d)", type.getMsg(), start));
    }
}
