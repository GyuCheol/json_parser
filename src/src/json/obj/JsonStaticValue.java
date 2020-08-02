package json.obj;

import json.JsonStringIterator;
import json.exceptions.JsonException;
import json.exceptions.JsonExceptionType;

public abstract class JsonStaticValue extends JsonValue {

    @Override
    public abstract String toString();

    @Override
    public int size() {
        return toString().length();
    }

    public boolean match(JsonStringIterator si) {

        for (int i = 0; i < size(); i++) {
            if (si.current() != toString().charAt(i)) {
                return false;
            }

            if (si.hasNext()) {
                si.next();
            } else {
                return false;
            }
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
