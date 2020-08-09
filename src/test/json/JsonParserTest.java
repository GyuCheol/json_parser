package json;

import json.exception.JsonException;
import json.object.*;
import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;

class JsonParserTest {
    Charset utf8 = StandardCharsets.UTF_8;
    JsonConverter parser = new JsonConverter(utf8);

    @Test
    void parse_number_array() {
        String json = "\t\t\t    \n\r\r[1, 2, 3, 4]";

        parser.parse(json);
    }

    @Test
    void parse_null() {
        String[] json = { "null", "   null", "      null\t\t\t", "  nu123ll", "nul1", "null123", "  nullapd", "null,", "nu" };
        boolean[] expected = {true, true, true, false, false, false, false, false, false};

        for (int i = 0; i < json.length; ++i) {
            String js = json[i];

            if (expected[i]) {
                assert(parser.parse(js) == JsonStaticValue.nullInstance);
            } else {
                assertThrows(JsonException.class, () -> parser.parse(js));
            }
        }
    }

    @Test
    void parse_true() {
        String[] json = { "   true", "\r\r\r true   ", "  true\t\t\t", " t rue", "truuue", "trueasdasd", " t r u e", "true123,", "tr", "trrr" };
        boolean[] expected = {true, true, true, false, false, false, false, false, false, false};

        for (int i = 0; i < json.length; ++i) {
            String js = json[i];

            if (expected[i]) {
                assert(parser.parse(js) == JsonStaticValue.trueInstance);
            } else {
                assertThrows(JsonException.class, () -> parser.parse(js));
            }
        }
    }

    @Test
    void parse_false() {
        String[] json = { "   false", "\r\r\r false   ", "  false\t\t\t", " f alse", "falle", "falllllllse", "   false,", "falee,", "fa", "faesl" };
        boolean[] expected = {true, true, true, false, false, false, false, false, false, false};

        for (int i = 0; i < json.length; ++i) {
            String js = json[i];

            if (expected[i]) {
                assert(parser.parse(js) == JsonStaticValue.falseInstance);
            } else {
                assertThrows(JsonException.class, () -> parser.parse(js));
            }
        }
    }

    @Test
    void parse_null_array_1() {
        String json = "\t\t\t    \n\r\r[null,     null, \t\t\tnull]";

        parser.parse(json);
    }

    @Test
    void parse_null_array_2() {
        String json = "\t\t\t    \n\r\r[null,     null, \t\t\tnull";

        assertThrows(JsonException.class, () -> parser.parse(json));
    }

    @Test
    void parse_null_array_3() {
        String json = "[null, true, [true, true]]";

        JsonArray result = parser.parse(json);
    }

    @Test
    void parse_string_1() {
        String json = "'str'";

        JsonString result = parser.parse(json);

        assertEquals(result.toString(), "\"str\"");
    }

    @Test
    void parse_string_2_exception_cases() {
        String json1 = "'str'''''";
        String json2 = "'str";
        String json3 = "'str\\'";

        assertThrows(JsonException.class, () -> parser.parse(json1));
        assertThrows(JsonException.class, () -> parser.parse(json2));
        assertThrows(JsonException.class, () -> parser.parse(json3));
    }

    @Test
    void parse_string_array() {
        String json = "['a', 'b', '123123', '\"']";

        JsonArray ary = parser.parse(json);

        JsonArray ary2 = parser.parse(ary.toString());

    }

    @Test
    void parse_string_3() {
        String json = "\"    \n\nasda\naczxczxc\"";

        JsonString js = parser.parse(json);

        assertEquals(json, js.toString());
        assertEquals(parser.parse(js.toString()).toString(), js.toString());
    }

    @Test
    void parse_number_1_without_exceptions() {
        String[] json = {"   1214512", ".123123  ", "1.232E10", "        \t\t\r\r\r\n\n\n123144.51821823198273", "+123", "-123", "1.23E-10"};

        for (String js : json) {
            parser.parse(js);
        }
    }

    @Test
    void parse_number_1_with_exceptions() {
        String[] json = {"123.12 313  ", "123 123", "123.123.123", "1335B10", "1.1.1", "11.23 A", "12.33B", "123,", "123,  456", "+-123"};

        for (String js : json) {
            assertThrows(JsonException.class, () -> parser.parse(js));
        }
    }

    @Test
    void parse_array_with_several_types() {
        String json = "[1, 2, '123', true, false, null]";

        JsonArray ary = parser.parse(json);

        assertEquals(ary.get(3), JsonStaticValue.trueInstance);
        assertEquals(ary.get(4), JsonStaticValue.falseInstance);
        assertEquals(ary.get(5), JsonStaticValue.nullInstance);
    }

    @Test
    void parse_object_1() {
        String json1 = "{ 'A': 1 }";
        String json2 = "{ 'A': 1, 'B': true, 'C': false, 'D': null, 'E': [1, 2, 3, 4] }";
        String json3 = "{ 'AASDAS': 11231, 'B': [1, 2, 3]}";

        JsonObject obj1 = parser.parse(json1);
        JsonObject obj2 = parser.parse(json2);
        JsonObject obj3 = parser.parse(json3);
    }

    @Test
    void parse_object_2_with_exceptions() {
        String json1 = "{ 'A': 1";
        String json2 = "{ 'A': , 'B': true, 'C': false, 'D': null, 'E': [1, 2, 3, 4] }";
        String json3 = "{'A'}";
        String json4 = "{'A' : 29292ZXCZ}";
        String json5 = "{'A   }";

        assertThrows(JsonException.class, () -> parser.parse(json1));
        assertThrows(JsonException.class, () -> parser.parse(json2));
        assertThrows(JsonException.class, () -> parser.parse(json3));
        assertThrows(JsonException.class, () -> parser.parse(json4));
        assertThrows(JsonException.class, () -> parser.parse(json5));
    }

    @Test
    void parse_for_huge_file() throws Exception {
        try (FileInputStream is = new FileInputStream("../sample/generated.json")) {
            assertDoesNotThrow(() -> parser.parse(is));
        }
    }

    @Test
    void parse_for_low_file() throws Exception {
        try (FileInputStream is = new FileInputStream("../sample/low_size.json")) {
            assertDoesNotThrow(() -> parser.parse(is));
        }
    }

    @Test
    void test_compare_two_json_strings() {
        JsonString json1 = new JsonString("asdasd");
        JsonString json2 = new JsonString("asdasd");
        JsonString json3 = new JsonString("asdasd1");

        assertEquals(json1, json2);
        assertNotEquals(json1, json3);
        assertNotEquals(json2, json3);
    }

    @Test
    void test_compare_two_json_numbers() {
        JsonArray ary = parser.parse("[1, 1, 1.1, 1.000000000001]");

        assertEquals(ary.get(0), ary.get(1));
        assertNotEquals(ary.get(2), ary.get(3));
        assertNotEquals(ary.get(0), ary.get(3));
    }

}