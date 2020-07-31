package json.exceptions;

public enum JsonExceptionType {

    BRACKETS("Not matched a pair of brackets"),
    NUMBER_FORMAT("Occurred a wrong number format"),
    UNKNOWN_CHAR("Expected a white space char but not.");

    private final String msg;

    private JsonExceptionType(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }
}
