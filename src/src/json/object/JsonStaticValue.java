package json.object;

import json.JsonStringIterator;
import json.exception.JsonException;
import json.exception.JsonExceptionType;

public abstract class JsonStaticValue extends JsonValue {

    @Override
    public abstract String toString();

    @Override
    public int size() {
        return toString().length();
    }

    public boolean match(JsonStringIterator si) {

        for (int i = 0; i < size(); i++) {
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

    public static JsonStaticValue parse(JsonExceptionType type, JsonStaticValue instance, JsonStringIterator si) {

        if (!instance.match(si)) {
            throw new JsonException(type, si.getPos());
        }

        return instance;
    }

}
