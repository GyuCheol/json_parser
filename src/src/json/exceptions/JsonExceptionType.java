package json.exceptions;

public enum JsonExceptionType {

    BRACKETS("Not matched a pair of brackets"),
    NUMBER_FORMAT("Occurred a wrong number format"),
    UNKNOWN_CHAR("Expected a white space char but not"),
    WRONG_NULL_FORMAT("Occurred a wrong null format (It should be 'null')"),
    IO_EXCEPTION("Occurred an IO exception"),
    WRONG_TRUE_FORMAT("Occurred a wrong true format (It should be 'true')"),
    WRONG_FALSE_FORMAT("Occurred a wrong false format (It should be 'false')"),
    NOT_FINISHED_ARRAY_FORMAT("Could not found the end of array"),
    NONE_STRING("Unexpected none json item");

    private final String msg;

    JsonExceptionType(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }
}
