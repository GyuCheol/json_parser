package json.element;

import json.exception.JsonNotFoundSpecificCharException;
import json.exception.JsonUnknownTokenException;
import json.iterator.JsonIterator;

import java.io.IOException;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;

public class JsonObject extends JsonElement implements Map<JsonString, JsonElement> {
    private HashMap<JsonString, JsonElement> properties = new HashMap<>();

    @Override
    public int size() {
        return properties.size();
    }

    @Override
    public boolean isEmpty() {
        return properties.isEmpty();
    }

    @Override
    public JsonElement get(Object key) {
        return properties.get(key);
    }

    @Override
    public boolean containsKey(Object key) {
        return properties.containsKey(key);
    }

    @Override
    public JsonElement put(JsonString key, JsonElement value) {
        return properties.put(key, value);
    }

    public JsonElement put(String key, JsonElement value) {
        return properties.put(new JsonString(key), value);
    }

    @Override
    public void putAll(Map<? extends JsonString, ? extends JsonElement> m) {
        properties.putAll(m);
    }

    @Override
    public JsonElement remove(Object key) {
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
    public Collection<JsonElement> values() {
        return properties.values();
    }

    @Override
    public Set<Entry<JsonString, JsonElement>> entrySet() {
        return properties.entrySet();
    }

    @Override
    public JsonElement getOrDefault(Object key, JsonElement defaultValue) {
        return properties.getOrDefault(key, defaultValue);
    }

    @Override
    public JsonElement putIfAbsent(JsonString key, JsonElement value) {
        return properties.putIfAbsent(key, value);
    }

    @Override
    public boolean remove(Object key, Object value) {
        return properties.remove(key, value);
    }

    @Override
    public boolean replace(JsonString key, JsonElement oldValue, JsonElement newValue) {
        return properties.replace(key, oldValue, newValue);
    }

    @Override
    public JsonElement replace(JsonString key, JsonElement value) {
        return properties.replace(key, value);
    }

    @Override
    public JsonElement computeIfAbsent(JsonString key, Function<? super JsonString, ? extends JsonElement> mappingFunction) {
        return properties.computeIfAbsent(key, mappingFunction);
    }

    @Override
    public JsonElement computeIfPresent(JsonString key, BiFunction<? super JsonString, ? super JsonElement, ? extends JsonElement> remappingFunction) {
        return properties.computeIfPresent(key, remappingFunction);
    }

    @Override
    public JsonElement compute(JsonString key, BiFunction<? super JsonString, ? super JsonElement, ? extends JsonElement> remappingFunction) {
        return properties.compute(key, remappingFunction);
    }

    @Override
    public JsonElement merge(JsonString key, JsonElement value, BiFunction<? super JsonElement, ? super JsonElement, ? extends JsonElement> remappingFunction) {
        return properties.merge(key, value, remappingFunction);
    }

    @Override
    public void forEach(BiConsumer<? super JsonString, ? super JsonElement> action) {
        properties.forEach(action);
    }

    @Override
    public void replaceAll(BiFunction<? super JsonString, ? super JsonElement, ? extends JsonElement> function) {
        properties.replaceAll(function);
    }

    @Override
    public Object clone() {
        return properties.clone();
    }

    @Override
    public int hashCode() {
        int hash = 0;

        for (JsonString json: properties.keySet()) {
            hash += json.hashCode();
            hash += properties.get(json).hashCode();
            hash <<= 1;
        }

        return hash;
    }

    @Override
    protected void appendString(Appendable appendable) throws IOException {
        appendable.append('{');

        Iterator<Entry<JsonString, JsonElement>> it = properties.entrySet().iterator();

        for (int i = 0; i < properties.size() - 1; i++) {
            Entry<JsonString, JsonElement> entry = it.next();

            entry.getKey().appendString(appendable);
            appendable.append(':');
            entry.getValue().appendString(appendable);
            appendable.append(',');
        }

        if (properties.size() > 0) {
            Entry<JsonString, JsonElement> entry = it.next();

            entry.getKey().appendString(appendable);
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
