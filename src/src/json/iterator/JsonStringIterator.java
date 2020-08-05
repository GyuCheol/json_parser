package json.iterator;

public class JsonStringIterator extends JsonIterator {
    private String string;

    public JsonStringIterator(String string) {
        this.string = string;
    }

    @Override
    public char current() {
        return this.string.charAt(pos);
    }

    @Override
    public boolean hasNext() {
        return super.pos < this.string.length();
    }


}
