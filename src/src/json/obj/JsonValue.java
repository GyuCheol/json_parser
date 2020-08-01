package json.obj;

public abstract class JsonValue {

    public JsonValue() {
    }

    public abstract String toString();

    public static JsonArray parseJsonArray(String json, int start) {
        JsonArray jsonArray = new JsonArray();

        return jsonArray;
    }

    public static JsonObject parseJsonObject(String json, int start) {
        return null;
    }

    public static JsonValue parseJsonValue(String json, int start) {

        for (int i = start; i < json.length(); i++) {
            char tmp = json.charAt(i);

            // 화이트 스페이스 무시하고, symbol 찾기
            switch(tmp) {
                case ' ':
                case '\r':
                case '\t':
                case '\n':
                    continue;
            }

            switch(tmp) {
                case '[':
                    return parseJsonArray(json, start);
                case '{':
                    return parseJsonObject(json, start);
                case 'n':
                    // null
                    break;
                case 't':
                    // True
                    break;
                case 'f':
                    // False
                    break;
                case '"':
                case '\'':
                    // String
                    break;
                default:
                    // check the number
            }

        }

        return null;
    }

}
