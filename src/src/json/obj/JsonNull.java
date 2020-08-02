package json.obj;

import json.exceptions.JsonExceptionType;

public class JsonNull extends JsonStaticValue {
    public static final JsonNull instance = new JsonNull();

    private JsonNull() { }

    @Override
    public String toString() {
        return "null";
    }

}
