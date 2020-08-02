package json.obj;

public class JsonTrue extends JsonStaticValue {
    public static final JsonTrue instance = new JsonTrue();

    private JsonTrue() {}

    @Override
    public String toString() {
        return "true";
    }

}
