package json.object;

import json.exception.JsonNotFoundSpecificCharException;
import json.iterator.JsonIterator;
import json.exception.JsonException;

public class JsonString extends JsonValue implements Comparable<JsonString> {

    private String string;
    private String toString;

    public JsonString(String string) {
        this.string = string;
        makeToString();
    }

    private void makeToString() {
        StringBuilder sb = new StringBuilder(this.string.length() + 16);

        sb.append('"');

        for (int i = 0; i < this.string.length(); ++i) {
            char tmp = this.string.charAt(i);

            switch (tmp) {
                case '"':
                case '\'':
                    sb.append('\\');
                    break;
            }

            sb.append(tmp);

        }

        sb.append('"');

        this.toString = sb.toString();
    }

    @Override
    public String toString() {
        return this.toString;
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

    public static String parseJsonString(JsonIterator si) {
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

        return sb.toString();
    }

    public static JsonString parse(JsonIterator si) {
        return new JsonString(parseJsonString(si));
    }

    @Override
    public int compareTo(JsonString o) {
        return this.string.compareTo(o.string);
    }
}
