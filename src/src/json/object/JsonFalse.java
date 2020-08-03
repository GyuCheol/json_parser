package json.object;

public class JsonFalse extends JsonStaticValue {
    public static final JsonFalse instance = new JsonFalse();

    private JsonFalse() {}

    @Override
    public String toString() {
        return "false";
    }

}
