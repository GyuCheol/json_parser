package json.iterator;

public abstract class JsonIterator {
    protected int pos = 0;

    public void next() {
        ++pos;
    }

    public abstract char current();

    public abstract boolean hasNext();

    public int getPos() {
        return this.pos;
    }

    public boolean isWhiteSpace() {
        // 화이트 스페이스 무시하고, symbol 찾기
        switch(current()) {
            case ' ':
            case '\r':
            case '\t':
            case '\n':
                return true;
            default:
                return false;
        }
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
