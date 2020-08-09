package json.exception;

public class JsonNumberFormatException extends JsonException {
    private NumberFormatException exception;

    public JsonNumberFormatException(NumberFormatException exception, int pos) {
        super(String.format("Occurred a wrong number format (%s)", exception.getMessage()), pos);

        this.exception = exception;
    }

    public NumberFormatException getException() {
        return this.exception;
    }


}
