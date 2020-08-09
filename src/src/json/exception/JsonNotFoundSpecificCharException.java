package json.exception;

public class JsonNotFoundSpecificCharException extends JsonException {

    public JsonNotFoundSpecificCharException(char delimiter, int pos) {
        super(String.format("Could not found the delimiter or splitter '%c'", delimiter), pos);
    }
}
