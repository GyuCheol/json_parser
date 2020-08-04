package json.object;

import json.JsonStringIterator;
import json.exception.JsonException;
import json.exception.JsonExceptionType;

import java.util.HashMap;

public class JsonObject extends JsonValue {
    private HashMap<JsonString, JsonValue> properties = new HashMap<>();


    public void addJsonProperty(JsonString key, JsonValue value) {
        properties.put(key, value);
    }

    public void removeJsonProperty(JsonString key) {
        properties.remove(key);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append('{');

        properties.forEach((k, v) -> {
            sb.append(k.toString());
            sb.append(": ");
            sb.append(v.toString());
        });

        sb.append('}');

        return sb.toString();
    }

    public static JsonObject parse(JsonStringIterator si) {
        JsonObject jsonObj = new JsonObject();
        boolean isFinished = false;

        si.next();
        si.skipWhiteSpaces();

        if (si.current() == '}') {
            // key, value 없이 끝난 경우
            return jsonObj;
        }

        si_loop:
        while (si.hasNext()) {

            // key를 발견해야 함!
            JsonString key = JsonString.parse(si);

            // :을 발견해야 함!!
            si.skipWhiteSpaces();

            if (si.current() == ':') {
                si.next();
            } else {
                throw new JsonException(JsonExceptionType.NOT_FOUND_COLON, si.getPos());
            }

            // JsonValue 추가
            JsonValue value = JsonValue.parse(si, true);

            jsonObj.addJsonProperty(key, value);

            // 추가될 요소가 있는가?
            si.skipWhiteSpaces();

            if (!si.hasNext()) {
                throw new JsonException(JsonExceptionType.NOT_FINISHED_OBJECT, si.getPos());
            }

            switch (si.current()) {
                case '}':
                    // obj의 끝
                    isFinished = true;
                    si.next();
                    break si_loop;
                case ',':
                    // 그 다음 key, value 쌍 존재
                    si.next();
                    si.skipWhiteSpaces();
                    break;
                default:
                    throw new JsonException(JsonExceptionType.UNKNOWN_TOKEN, si.getPos());
            }
        }

        if (!isFinished) {
            throw new JsonException(JsonExceptionType.NOT_FINISHED_OBJECT, si.getPos());
        }

        return jsonObj;

    }
}
