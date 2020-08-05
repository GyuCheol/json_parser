package json.object;

import json.iterator.JsonIterator;
import json.iterator.JsonStreamIterator;
import json.iterator.JsonStringIterator;
import json.exception.JsonException;
import json.exception.JsonExceptionType;

public abstract class JsonStaticValue extends JsonValue {

    @Override
    public abstract String toString();

    private boolean match(JsonIterator si) {
        int size = toString().length();

        for (int i = 0; i < size; i++) {
            if (!si.hasNext()) {
                return false;
            }

            if (si.current() != toString().charAt(i)) {
                return false;
            }

            si.next();
        }

        return true;
    }

    public static JsonStaticValue parse(JsonIterator si) {
        JsonStaticValue staticValue;
        JsonExceptionType exceptionType;

        switch (si.current()) {
            case 'n':
                staticValue = JsonNull.instance;
                exceptionType = JsonExceptionType.WRONG_TRUE_FORMAT;
                break;
            case 't':
                staticValue = JsonTrue.instance;
                exceptionType = JsonExceptionType.WRONG_TRUE_FORMAT;
                break;
            case 'f':
                staticValue = JsonFalse.instance;
                exceptionType = JsonExceptionType.WRONG_FALSE_FORMAT;
                break;
            default:
                throw new JsonException(JsonExceptionType.UNKNOWN_TOKEN, si.getPos());
        }

        if (!staticValue.match(si)) {
            throw new JsonException(exceptionType, si.getPos());
        }

        return staticValue;
    }

}
