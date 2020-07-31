package json;

public enum JsonType {
    NONE,
    NUMBER,
    OBJECT('{', '}'),
    ARRAY('[', ']'),
    STRING('"', '"'),
    TRUE('t', 'e'),
    FALSE('f', 'e'),
    NULL('n', 'l');

    private final char startChar;
    private final char endChar;

    private JsonType() {
        this('\0', '\0');
    }

    private JsonType(char start, char end) {
        this.startChar = start;
        this.endChar = end;
    }

}
