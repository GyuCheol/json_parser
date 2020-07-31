package json.obj;

import json.JsonType;

public abstract class JsonValue {
    private int start;
    private int end;
    private JsonType jsonType;

    public JsonValue(int start, JsonType jsonType) {
        this.start = start;
        this.jsonType = jsonType;
    }

}
