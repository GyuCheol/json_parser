package json.exception;

import java.io.IOException;

public class JsonIOException extends JsonException {
    private IOException exception;

    public JsonIOException(IOException exception, int pos) {
        super(exception.getMessage(), pos);

        this.exception = exception;
    }

    public IOException getIOException() {
        return this.exception;
    }
}
