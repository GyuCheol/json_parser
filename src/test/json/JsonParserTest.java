package json;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;

class JsonParserTest {

    Charset utf8 = StandardCharsets.UTF_8;

    @Test
    void parse_number_array() {
        String json = "[1, 2, 3, 4]";
        ByteArrayInputStream is = new ByteArrayInputStream(json.getBytes(utf8));
        JsonParser parser = new JsonParser(utf8);

        try {
            parser.parse(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}