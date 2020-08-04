package json.exception;

public enum JsonExceptionType {

    UNKNOWN_TOKEN("Found unknown token"),
    NUMBER_FORMAT("Occurred a wrong number format"),
    UNKNOWN_CHAR("Expected a white space char but not"),
    WRONG_NULL_FORMAT("Occurred a wrong null format (It should be 'null')"),
    IO_EXCEPTION("Occurred an IO exception"),
    WRONG_TRUE_FORMAT("Occurred a wrong true format (It should be 'true')"),
    WRONG_FALSE_FORMAT("Occurred a wrong false format (It should be 'false')"),
    NOT_FINISHED_ARRAY("Could not found the end of array ']'"),
    NOT_FINISHED_OBJECT("Could not found the end of object '}'"),
    NOT_FOUND_COLON("Expected ':' but another char was found"),
    NOT_FINISHED_STRING("Could not found the pair of delimiters \" or '");

    private final String msg;

    JsonExceptionType(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }
}
