package json.obj;

import json.JsonStringIterator;
import json.exceptions.JsonException;
import json.exceptions.JsonExceptionType;

public abstract class JsonValue {

    public abstract String toString();

    public abstract int size();


    public static JsonValue parse(JsonStringIterator si) {
        return parse(si, false, '\0');
    }

    public static JsonValue parse(JsonStringIterator si, boolean comma, char allowed) throws JsonException {

        si.skipWhiteSpaces();

        JsonValue jsonValue;

        switch(si.current()) {
            case '[':
                jsonValue = JsonArray.parse(si);
                break;
            case '{':
                jsonValue = JsonObject.parse(si);
                break;
            case 'n':
                // null
                jsonValue = JsonStaticValue.parse(JsonExceptionType.WRONG_NULL_FORMAT, JsonNull.instance, si);
                break;
            case 't':
                // True
                jsonValue = JsonStaticValue.parse(JsonExceptionType.WRONG_TRUE_FORMAT, JsonTrue.instance, si);
                break;
            case 'f':
                // False
                jsonValue = JsonStaticValue.parse(JsonExceptionType.WRONG_FALSE_FORMAT, JsonFalse.instance, si);
                break;
            case '"':
            case '\'':
                jsonValue = JsonString.parse(si);
                break;
            default:
                // check the number
                jsonValue = JsonNumber.parse(si);
                break;
        }

        si.skipWhiteSpaces();

        if (si.hasNext()) {
            char last = si.current();

            if (comma && (last == ',' || last == allowed)) {
                return jsonValue;
            } else {
                throw new JsonException(JsonExceptionType.UNKNOWN_CHAR, si.getPos());
            }
        }

        return jsonValue;
    }

}
