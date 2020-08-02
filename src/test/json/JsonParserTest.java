package json;

import json.exceptions.JsonException;
import json.obj.JsonArray;
import json.obj.JsonFalse;
import json.obj.JsonNull;
import json.obj.JsonTrue;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;

class JsonParserTest {

    Charset utf8 = StandardCharsets.UTF_8;

    @Test
    void parse_number_array() {
        String json = "\t\t\t    \n\r\r[1, 2, 3, 4]";
        ByteArrayInputStream is = new ByteArrayInputStream(json.getBytes(utf8));
        JsonParser parser = new JsonParser(utf8);

        parser.parse(is);
    }

    @Test
    void parse_null() {
        String[] json = { "null", "   null", "      null\t\t\t", "  nu123ll", "nul1", "null123", "  nullapd", "null,", "nu" };
        boolean[] expected = {true, true, true, false, false, false, false, false, false};
        JsonParser parser = new JsonParser(utf8);

        for (int i = 0; i < json.length; ++i) {
            ByteArrayInputStream is = new ByteArrayInputStream(json[i].getBytes(utf8));

            if (expected[i]) {
                assert(parser.parse(is) == JsonNull.instance);
            } else {
                assertThrows(JsonException.class, () -> parser.parse(is));
            }
        }
    }

    @Test
    void parse_true() {
        String[] json = { "   true", "\r\r\r true   ", "  true\t\t\t", " t rue", "truuue", "trueasdasd", " t r u e", "true123,", "tr", "trrr" };
        boolean[] expected = {true, true, true, false, false, false, false, false, false, false};
        JsonParser parser = new JsonParser(utf8);

        for (int i = 0; i < json.length; ++i) {
            ByteArrayInputStream is = new ByteArrayInputStream(json[i].getBytes(utf8));

            if (expected[i]) {
                assert(parser.parse(is) == JsonTrue.instance);
            } else {
                assertThrows(JsonException.class, () -> parser.parse(is));
            }
        }
    }

    @Test
    void parse_false() {
        String[] json = { "   false", "\r\r\r false   ", "  false\t\t\t", " f alse", "falle", "falllllllse", "   false,", "falee,", "fa", "faesl" };
        boolean[] expected = {true, true, true, false, false, false, false, false, false, false};
        JsonParser parser = new JsonParser(utf8);

        for (int i = 0; i < json.length; ++i) {
            ByteArrayInputStream is = new ByteArrayInputStream(json[i].getBytes(utf8));

            if (expected[i]) {
                assert(parser.parse(is) == JsonFalse.instance);
            } else {
                assertThrows(JsonException.class, () -> parser.parse(is));
            }
        }
    }

    @Test
    void parse_null_array_1() {
        String json = "\t\t\t    \n\r\r[null,     null, \t\t\tnull]";
        ByteArrayInputStream is = new ByteArrayInputStream(json.getBytes(utf8));
        JsonParser parser = new JsonParser(utf8);

        parser.parse(is);
    }

    @Test
    void parse_null_array_2() {
        String json = "\t\t\t    \n\r\r[null,     null, \t\t\tnull";
        ByteArrayInputStream is = new ByteArrayInputStream(json.getBytes(utf8));
        JsonParser parser = new JsonParser(utf8);

        assertThrows(JsonException.class, () -> parser.parse(is));
    }
}