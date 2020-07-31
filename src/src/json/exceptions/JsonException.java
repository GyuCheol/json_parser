package json.exceptions;

public class JsonException extends RuntimeException {

    public JsonException(JsonExceptionType type, int start, int end) {
        super(String.format("%s, pos : (%d, %d)", type.getMsg(), start, end));
    }
}
