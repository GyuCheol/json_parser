package json.element;

import json.exception.JsonNotFoundSpecificCharException;
import json.iterator.JsonIterator;

import java.io.IOException;

public class JsonString extends JsonElement implements Comparable<JsonString> {
    private String string;

    public JsonString(String string) {
        this.string = string;
    }

    public String getString() {
        return this.string;
    }

    @Override
    public int hashCode() {
        return string.hashCode();
    }

    @Override
    public boolean equals(Object obj) {

        if (obj instanceof JsonString) {
            return this.string.equals(((JsonString)obj).string);
        }

        return false;
    }

    @Override
    protected void appendString(Appendable appendable) throws IOException {
        appendString(this.string, appendable);
    }

    public static void appendString(String str, Appendable appendable) throws IOException {
        appendable.append('"');

        for (int i = 0; i < str.length(); ++i) {
            char tmp = str.charAt(i);

            switch (tmp) {
                case '"':
                case '\'':
                    appendable.append('\\');
                    break;
            }

            appendable.append(tmp);

        }

        appendable.append('"');
    }

    public static JsonString parse(JsonIterator si) {
        StringBuilder sb = new StringBuilder();

        boolean isFinished = false;

        // 맨 앞 ', " 건너 뛰기
        char start = si.current();

        si.next();

        for (;si.hasNext(); si.next()) {

            if (si.current() == '\\') {
                if (si.hasNext()) {
                    si.next();
                    sb.append(si.current());
                    continue;
                } else {
                    break;
                }
            }

            if (si.current() == start) {
                // String 종결자. escape 없이, start와 같은 문자를 만남.
                isFinished = true;
                break;
            }

            sb.append(si.current());
        }

        if (!isFinished) {
            throw new JsonNotFoundSpecificCharException(start, si.getPos());
        }

        si.next();

        return new JsonString(sb.toString());
    }

    @Override
    public int compareTo(JsonString o) {
        return this.string.compareTo(o.string);
    }
}
