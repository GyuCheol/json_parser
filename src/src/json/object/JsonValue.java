package json.object;

import json.exception.JsonUnknownTokenException;
import json.iterator.JsonIterator;
import json.exception.JsonException;

public abstract class JsonValue {

    public abstract String toString();

    public static JsonValue parse(JsonIterator si) {
        return parse(si, false);
    }

    public static JsonValue parse(JsonIterator si, boolean isIncluded) throws JsonException {

        si.skipWhiteSpaces();

        JsonValue jsonValue;

        switch(si.current()) {
            case '[':
                // check number
                jsonValue = JsonArray.parse(si);
                break;
            case '{':
                // check number
                jsonValue = JsonObject.parse(si);
                break;
            case '"':
            case '\'':
                // check number
                jsonValue = JsonString.parse(si);
                break;
            case '0':
            case '1':
            case '2':
            case '3':
            case '4':
            case '5':
            case '6':
            case '7':
            case '8':
            case '9':
            case '+':
            case '-':
            case '.':
                // check number
                jsonValue = JsonNumber.parse(si);
                break;
            case 'n':
            case 't':
            case 'f':
                // check the type of null, true, false (for static instances)
                jsonValue = JsonStaticValue.parse(si);
                break;
            default:
                throw new JsonUnknownTokenException(si.getPos());
        }

        // obj나 ary에 포함된 요소가 아니라면 다른 token 없이 whitespace로 종결 되어야 한다.
        if (!isIncluded) {
            si.skipWhiteSpaces();

            if (!si.hasNext()) {
                return jsonValue;
            } else {
                throw new JsonUnknownTokenException(si.getPos());
            }
        }

        return jsonValue;
    }

}
