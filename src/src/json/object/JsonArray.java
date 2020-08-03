package json.object;

import json.JsonStringIterator;
import json.exception.JsonException;
import json.exception.JsonExceptionType;

import java.util.ArrayList;

public class JsonArray extends JsonValue {
    private ArrayList<JsonValue> jsonValues = new ArrayList<>();

    public void addJsonValue(JsonValue jsonValue) {
        jsonValues.add(jsonValue);
    }

    public void removeJsonValue(JsonValue jsonValue) {
        jsonValues.remove(jsonValue);
    }

    public JsonValue getJsonItem(int index) {
        return jsonValues.get(index);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(size());

        sb.append('[');

        for (JsonValue json: jsonValues) {
            sb.append(json.toString());
            sb.append(", ");
        }
        
        // 마지막 , 제거
        sb.setLength(sb.length() - 2);
        sb.append(']');

        return sb.toString();
    }

    @Override
    public int size() {
        int size = 2; // []

        for (JsonValue json: jsonValues) {
            size += json.size();
        }

        // comma는 json 개수 - 1개 만큼 있다.
        size += jsonValues.size() - 1;

        return size;
    }

    public static JsonArray parse(JsonStringIterator si) {
        JsonArray jsonArray = new JsonArray();
        boolean isFinished = false;

        si.next();

        while (si.hasNext()) {
            si.skipWhiteSpaces();
            char tmp = si.current();

            if (tmp == ',') {
                // 추가 요소 발견
                si.next();
            } else if (tmp == ']') {
                // 배열의 끝
                si.next();
                isFinished = true;
                break;
            } else {
                JsonValue item = JsonValue.parse(si, true, ']');

                jsonArray.addJsonValue(item);
            }
        }

        if (!isFinished) {
            throw new JsonException(JsonExceptionType.NOT_FINISHED_ARRAY, si.getPos());
        }

        return jsonArray;
    }

}
