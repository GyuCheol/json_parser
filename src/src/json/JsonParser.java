package json;

import json.exception.JsonException;
import json.exception.JsonExceptionType;
import json.iterator.JsonStreamIterator;
import json.iterator.JsonStringIterator;
import json.object.JsonValue;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class JsonParser {
    private final int bufferSize;
    private Charset charset;
    private int pos = 0;

    /***
     * If it doesn't set a charset, it will set on utf-8. (BUFFER_SIZE will also be set on 512)
     */
    public JsonParser() {
        this(StandardCharsets.UTF_8, 512);
    }

    public JsonParser(Charset charset) {
        this(charset, 512);
    }

    public JsonParser(Charset charset, int bufferSize) {
        this.bufferSize = bufferSize;
        this.charset = charset;
    }

    @SuppressWarnings("unchecked")
    public <T extends JsonValue> T parse(InputStream in) throws JsonException {
        try {
            return (T) JsonValue.parse(new JsonStreamIterator(new InputStreamReader(in, charset), bufferSize));
        } catch (IOException exception) {
            throw new JsonException(JsonExceptionType.PARSING_IO_EXCEPTION, 0);
        }
    }

    @SuppressWarnings("unchecked")
    public <T extends JsonValue> T parse(String str) throws JsonException {
        return (T) JsonValue.parse(new JsonStringIterator(str));
    }
}
