package json.exception;

public class JsonUnknownTokenException extends JsonException {

    public JsonUnknownTokenException(int pos) {
        super("Found unknown token", pos);
    }
}
