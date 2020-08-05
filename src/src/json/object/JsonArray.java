package json.object;

import json.iterator.JsonIterator;
import json.iterator.JsonStringIterator;
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
        StringBuilder sb = new StringBuilder();

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

    public static JsonArray parse(JsonIterator si) {
        JsonArray jsonArray = new JsonArray();
        boolean isFinished = false;

        si.next();
        si.skipWhiteSpaces();

        // 아무 요소 없다면 바로 리턴
        if (si.current() == ']') {
            return jsonArray;
        }

        si_loop:
        while (si.hasNext()) {

            JsonValue item = JsonValue.parse(si, true);

            jsonArray.addJsonValue(item);

            si.skipWhiteSpaces();

            if (!si.hasNext()) {
                break;
            }

            switch (si.current()) {
                case ',':
                    si.next();
                    si.skipWhiteSpaces();
                    break;
                case ']':
                    si.next();
                    isFinished = true;
                    break si_loop;
                default:
                    throw new JsonException(JsonExceptionType.UNKNOWN_TOKEN, si.getPos());
            }
        }

        if (!isFinished) {
            throw new JsonException(JsonExceptionType.NOT_FINISHED_ARRAY, si.getPos());
        }

        return jsonArray;
    }

}
