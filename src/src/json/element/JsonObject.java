package json.element;

import json.exception.JsonNotFoundSpecificCharException;
import json.exception.JsonUnknownTokenException;
import json.iterator.JsonIterator;

import java.io.IOException;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;

public class JsonObject extends JsonElement {
    private HashMap<String, JsonElement> properties = new HashMap<>();

    public JsonElement put(String key, JsonElement value) {
        if (key == null) {
            return null;
        }

        super.isRecently = false;

        return properties.put(key, value);
    }

    public JsonElement remove(String key) {
        JsonElement tmp = properties.remove(key);

        if (tmp != null) {
            super.isRecently = false;
        }

        return tmp;
    }

    public void clear() {
        if (properties.size() > 0) {
            super.isRecently = false;
            properties.clear();
        }
    }

    public JsonElement get(String key) {
        return properties.get(key);
    }

    public boolean isEmpty() {
        return properties.isEmpty();
    }

    public boolean containsKey(String key) {
        return properties.containsKey(key);
    }

    public Set<String> keySet() {
        return properties.keySet();
    }

    public Collection<JsonElement> values() {
        return properties.values();
    }

    public JsonElement getOrDefault(String key, JsonElement defaultValue) {
        return properties.getOrDefault(key, defaultValue);
    }

    @Override
    protected void appendString(Appendable appendable) throws IOException {
        appendable.append('{');

        Iterator<Map.Entry<String, JsonElement>> it = properties.entrySet().iterator();

        for (int i = 0; i < properties.size() - 1; i++) {
            Map.Entry<String, JsonElement> entry = it.next();

            JsonString.appendString(entry.getKey(), appendable);
            appendable.append(':');
            entry.getValue().appendString(appendable);
            appendable.append(',');
        }

        if (properties.size() > 0) {
            Map.Entry<String, JsonElement> entry = it.next();

            JsonString.appendString(entry.getKey(), appendable);
            appendable.append(':');
            entry.getValue().appendString(appendable);
        }

        appendable.append('}');
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
            JsonElement value = JsonElement.parse(si, true);

            jsonObj.put(key.getString(), value);

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
