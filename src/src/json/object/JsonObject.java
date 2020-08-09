package json.object;

import json.exception.JsonNotFoundSpecificCharException;
import json.exception.JsonUnknownTokenException;
import json.iterator.JsonIterator;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;

public class JsonObject extends JsonValue implements Map<JsonString, JsonValue> {
    private HashMap<JsonString, JsonValue> properties = new HashMap<>();

    @Override
    public int size() {
        return properties.size();
    }

    @Override
    public boolean isEmpty() {
        return properties.isEmpty();
    }

    @Override
    public JsonValue get(Object key) {
        return properties.get(key);
    }

    @Override
    public boolean containsKey(Object key) {
        return properties.containsKey(key);
    }

    @Override
    public JsonValue put(JsonString key, JsonValue value) {
        return properties.put(key, value);
    }

    @Override
    public void putAll(Map<? extends JsonString, ? extends JsonValue> m) {
        properties.putAll(m);
    }

    @Override
    public JsonValue remove(Object key) {
        return properties.remove(key);
    }

    @Override
    public void clear() {
        properties.clear();
    }

    @Override
    public boolean containsValue(Object value) {
        return properties.containsValue(value);
    }

    @Override
    public Set<JsonString> keySet() {
        return properties.keySet();
    }

    @Override
    public Collection<JsonValue> values() {
        return properties.values();
    }

    @Override
    public Set<Entry<JsonString, JsonValue>> entrySet() {
        return properties.entrySet();
    }

    @Override
    public JsonValue getOrDefault(Object key, JsonValue defaultValue) {
        return properties.getOrDefault(key, defaultValue);
    }

    @Override
    public JsonValue putIfAbsent(JsonString key, JsonValue value) {
        return properties.putIfAbsent(key, value);
    }

    @Override
    public boolean remove(Object key, Object value) {
        return properties.remove(key, value);
    }

    @Override
    public boolean replace(JsonString key, JsonValue oldValue, JsonValue newValue) {
        return properties.replace(key, oldValue, newValue);
    }

    @Override
    public JsonValue replace(JsonString key, JsonValue value) {
        return properties.replace(key, value);
    }

    @Override
    public JsonValue computeIfAbsent(JsonString key, Function<? super JsonString, ? extends JsonValue> mappingFunction) {
        return properties.computeIfAbsent(key, mappingFunction);
    }

    @Override
    public JsonValue computeIfPresent(JsonString key, BiFunction<? super JsonString, ? super JsonValue, ? extends JsonValue> remappingFunction) {
        return properties.computeIfPresent(key, remappingFunction);
    }

    @Override
    public JsonValue compute(JsonString key, BiFunction<? super JsonString, ? super JsonValue, ? extends JsonValue> remappingFunction) {
        return properties.compute(key, remappingFunction);
    }

    @Override
    public JsonValue merge(JsonString key, JsonValue value, BiFunction<? super JsonValue, ? super JsonValue, ? extends JsonValue> remappingFunction) {
        return properties.merge(key, value, remappingFunction);
    }

    @Override
    public void forEach(BiConsumer<? super JsonString, ? super JsonValue> action) {
        properties.forEach(action);
    }

    @Override
    public void replaceAll(BiFunction<? super JsonString, ? super JsonValue, ? extends JsonValue> function) {
        properties.replaceAll(function);
    }

    @Override
    public Object clone() {
        return properties.clone();
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

            jsonObj.put(key, value);

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
