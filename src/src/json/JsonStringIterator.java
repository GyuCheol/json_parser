package json;

public class JsonStringIterator {
    private String string;
    private int pos = 0;

    public JsonStringIterator(String string) {
        this.string = string;
    }

    public int length() {
        return string.length();
    }

    public boolean hasNext() {
        return pos < this.string.length();
    }

    public int getPos() {
        return this.pos;
    }

    public char current() {
        return this.string.charAt(pos);
    }

    public void next() {
        ++pos;
    }

    public void skipWhiteSpaces() {
        while (hasNext()) {
            // 화이트 스페이스 무시하고, symbol 찾기
            switch(current()) {
                case ' ':
                case '\r':
                case '\t':
                case '\n':
                    next();
                    continue;
            }

            return;
        }
    }
}
