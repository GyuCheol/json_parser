package json.exception;

public class JsonException extends RuntimeException {

    private JsonExceptionType jsonExceptionType;

    public JsonException(JsonExceptionType type, int start) {
        super(String.format("%s (pos : %d)", type.getMsg(), start));

        this.jsonExceptionType = type;
    }

    public JsonExceptionType getJsonExceptionType() {
        return this.jsonExceptionType;
    }

}
