package json;

import json.exception.JsonException;
import json.element.*;
import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.IOException;
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
                assert(parser.parse(js) == JsonStaticElement.nullInstance);
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
                assert(parser.parse(js) == JsonStaticElement.trueInstance);
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
                assert(parser.parse(js) == JsonStaticElement.falseInstance);
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

        assertEquals(ary.get(3), JsonStaticElement.trueInstance);
        assertEquals(ary.get(4), JsonStaticElement.falseInstance);
        assertEquals(ary.get(5), JsonStaticElement.nullInstance);
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
        try (FileInputStream is = new FileInputStream("./sample/generated.json")) {
            assertDoesNotThrow(() -> parser.parse(is));
        }
    }

    @Test
    void parse_for_low_file() throws Exception {
        try (FileInputStream is = new FileInputStream("./sample/low_size.json")) {
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

    @Test
    void test_to_string_in_array() {
        JsonArray ary = parser.parse("[1, 2, 1]");
        String s = ary.toString();
        String s2 = ary.toString();

        // cache 되므로 레퍼런스가 똑같아야함.
        assert s == s2;

        ary.clear();
        ary.add(new JsonNumber(2));
        ary.add(new JsonNumber(1));
        ary.add(new JsonNumber(1));

        assertNotEquals(ary.toString(), s);
    }

    @Test
    void test_to_string_in_object() {
        JsonObject obj = parser.parse("{'a': 1, 'b': 2}");
        String s = obj.toString();
        String s2 = obj.toString();

        // cache 되므로 레퍼런스가 똑같아야함.
        assert s == s2;

        obj.clear();
        obj.put("b", new JsonNumber(2));
        obj.put("a", new JsonNumber(1));

        // 새로운 put, remove가 나오면 새로운 레퍼런스
        String s3 = obj.toString();
        assert s3 != s2;

        obj.clear();
        obj.put("b", new JsonNumber(2));
        obj.put("a", new JsonNumber(2));

        // 값이 바뀌었으므로 새 string을 만들어야 한다.
        String s4 = obj.toString();
        assert s4 != s3;
    }

    @Test
    void test_to_string_in_object2() {
        JsonObject obj = new JsonObject();

        obj.put("A", parser.parse("true"));
        String s = obj.toString();

        // key가 null이라면, 추가되어선 안됨, cache가 업데이트 되어서도 안됨.
        obj.put(null, parser.parse("false"));
        String s2 = obj.toString();

        assert s == s2;

        // 등록안된 key 제거 cache 유지
        obj.remove("C");
        String s3 = obj.toString();
        assert s == s3;

        // 등록된 key 제거 cache 업뎃
        obj.remove("A");
        String s4 = obj.toString();
        assert s != s4;
    }

    @Test
    void test_to_write() {
        JsonObject obj = parser.parse("{'a': 1, 'b': 2}");
        JsonObject obj2 = new JsonObject();
        StringBuilder sb = new StringBuilder();

        try {
            obj.writeToStream(sb);

            assertEquals(obj.toString(), sb.toString());

            sb.setLength(0);

            obj2.writeToStream(sb);
            assertEquals(sb.toString(), "{}");
            assertEquals(obj2.toString(), "{}");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}