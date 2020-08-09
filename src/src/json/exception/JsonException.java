package json.exception;

public abstract class JsonException extends RuntimeException {

    private int pos;

    public JsonException(String msg, int pos) {
        super(String.format("%s, pos : %d", msg, pos));
        this.pos = pos;
    }

    public int getPos() {
        return this.pos;
    }

}
