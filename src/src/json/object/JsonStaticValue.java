package json.object;

import json.exception.JsonNotMatchedException;
import json.exception.JsonUnknownTokenException;
import json.iterator.JsonIterator;

public class JsonStaticValue extends JsonValue {
    public static final JsonStaticValue nullInstance = new JsonStaticValue("null", null);
    public static final JsonStaticValue trueInstance = new JsonStaticValue("true", true);
    public static final JsonStaticValue falseInstance = new JsonStaticValue("false", false);

    private String str;
    private Boolean value;

    private JsonStaticValue(String str, Boolean value) {
        this.str = str;
        this.value = value;
    }

    public Boolean getValueOrNull() {
        return this.value;
    }

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

    @Override
    public String toString() {
        return this.str;
    }

    public static JsonStaticValue parse(JsonIterator si) {
        JsonStaticValue staticValue;

        switch (si.current()) {
            case 'n':
                staticValue = nullInstance;
                break;
            case 't':
                staticValue = trueInstance;
                break;
            case 'f':
                staticValue = falseInstance;
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
