package json.exception;

public class JsonNumberFormatException extends JsonException {
    private NumberFormatException ex;

    public JsonNumberFormatException(NumberFormatException ex, int pos) {
        super(String.format("Occurred a wrong number format (%s)", ex.getMessage()), pos);

        this.ex = ex;
    }

    public NumberFormatException getNumberFormatException() {
        return this.ex;
    }


}
