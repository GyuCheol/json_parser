package json.object;

import json.exception.JsonIOException;
import json.exception.JsonUnknownTokenException;
import json.iterator.JsonIterator;
import json.exception.JsonException;

import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;

public abstract class JsonElement {
    private String strCache = null;
    private int lastCachingInfo = 0;

    protected abstract void appendStringCache(Appendable appendable) throws IOException;

    public void writeToStream(Appendable ps) throws IOException {
        int hash = hashCode();

        if (lastCachingInfo == hash) {
            ps.append(strCache);
        } else {
            appendStringCache(ps);
        }
    }

    @Override
    public String toString() {
        int hash = hashCode();

        if (strCache == null || lastCachingInfo != hash) {
            StringBuilder sb = new StringBuilder();

            lastCachingInfo = hash;

            try {
                appendStringCache(sb);
                strCache = sb.toString();
            } catch (IOException e) {
                throw new JsonIOException(e, -1);
            }

        }

        return this.strCache;
    }


    public static JsonElement parse(JsonIterator si) {
        return parse(si, false);
    }

    public static JsonElement parse(JsonIterator si, boolean isIncluded) throws JsonException {

        si.skipWhiteSpaces();

        JsonElement jsonElement;

        switch(si.current()) {
            case '[':
                // check number
                jsonElement = JsonArray.parse(si);
                break;
            case '{':
                // check number
                jsonElement = JsonObject.parse(si);
                break;
            case '"':
            case '\'':
                // check number
                jsonElement = JsonString.parse(si);
                break;
            case '0':
            case '1':
            case '2':
            case '3':
            case '4':
            case '5':
            case '6':
            case '7':
            case '8':
            case '9':
            case '+':
            case '-':
            case '.':
                // check number
                jsonElement = JsonNumber.parse(si);
                break;
            case 'n':
            case 't':
            case 'f':
                // check the type of null, true, false (for static instances)
                jsonElement = JsonStaticElement.parse(si);
                break;
            default:
                throw new JsonUnknownTokenException(si.getPos());
        }

        // obj나 ary에 포함된 요소가 아니라면 다른 token 없이 whitespace로 종결 되어야 한다.
        if (!isIncluded) {
            si.skipWhiteSpaces();

            if (!si.hasNext()) {
                return jsonElement;
            } else {
                throw new JsonUnknownTokenException(si.getPos());
            }
        }

        return jsonElement;
    }

}
