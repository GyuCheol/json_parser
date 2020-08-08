package json.object;

public class JsonBoolean extends JsonStaticValue {
    public static final JsonBoolean trueInstance = new JsonBoolean(true);
    public static final JsonBoolean falseInstance = new JsonBoolean(false);

    private boolean bool;

    private JsonBoolean(boolean bool) {
        this.bool = bool;
    }

    @Override
    public String toString() {
        return bool ? "true" : "false";
    }

}
