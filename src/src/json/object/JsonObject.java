package json.object;

import json.exception.JsonNotFoundSpecificCharException;
import json.exception.JsonUnknownTokenException;
import json.iterator.JsonIterator;

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

    public static JsonObject parse(JsonIterator si) {
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
                throw new JsonNotFoundSpecificCharException(':', si.getPos());
            }

            // JsonValue 추가
            JsonValue value = JsonValue.parse(si, true);

            jsonObj.addJsonProperty(key, value);

            // 추가될 요소가 있는가?
            si.skipWhiteSpaces();

            if (!si.hasNext()) {
                break;
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
                    throw new JsonUnknownTokenException(si.getPos());
            }
        }

        if (!isFinished) {
            throw new JsonNotFoundSpecificCharException('}', si.getPos());
        }

        return jsonObj;

    }
}
