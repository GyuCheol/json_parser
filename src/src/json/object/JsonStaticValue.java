package json.object;

import json.exception.JsonNotMatchedException;
import json.exception.JsonUnknownTokenException;
import json.iterator.JsonIterator;
import json.exception.JsonException;

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

        switch (si.current()) {
            case 'n':
                staticValue = JsonNull.instance;
                break;
            case 't':
                staticValue = JsonBoolean.trueInstance;
                break;
            case 'f':
                staticValue = JsonBoolean.falseInstance;
                break;
            default:
                throw new JsonUnknownTokenException(si.getPos());
        }

        if (!staticValue.match(si)) {
            throw new JsonNotMatchedException(staticValue.toString(), si.getPos());
        }

        return staticValue;
    }

}
