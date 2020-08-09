package json.exception;

public class JsonNotMatchedException extends JsonException {

    public JsonNotMatchedException(String value, int pos) {
        super(String.format("Occurred a wrong true format (It should be '%s')", value), pos);
    }
}
