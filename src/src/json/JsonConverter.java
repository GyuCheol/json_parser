package json;

import json.exception.JsonException;
import json.exception.JsonIOException;
import json.iterator.JsonStreamIterator;
import json.iterator.JsonStringIterator;
import json.object.JsonValue;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class JsonConverter {
    private final int bufferSize;
    private Charset charset;
    private int pos = 0;

    /***
     * If it doesn't set a charset, it will set on utf-8. (BUFFER_SIZE will also be set on 512)
     */
    public JsonConverter() {
        this(StandardCharsets.UTF_8, 512);
    }

    public JsonConverter(Charset charset) {
        this(charset, 512);
    }

    public JsonConverter(Charset charset, int bufferSize) {
        this.bufferSize = bufferSize;
        this.charset = charset;
    }

    @SuppressWarnings("unchecked")
    public <T extends JsonValue> T parse(InputStream in) throws JsonException {
        return (T) JsonValue.parse(new JsonStreamIterator(new InputStreamReader(in, charset), bufferSize));
    }

    @SuppressWarnings("unchecked")
    public <T extends JsonValue> T parse(String str) throws JsonException {
        return (T) JsonValue.parse(new JsonStringIterator(str));
    }

    public <T> T toObject(JsonValue jsonValue) {
        // JsonNumber > Integer, Double, BigDecimal, Float, Long
        // JsonString > String
        // JsonArray > ArrayList<T> or T[]
        // JsonObject > T
        // JsonNull > null
        // JsonBoolean > Boolean (true or false)

        

        return null;
    }

}
