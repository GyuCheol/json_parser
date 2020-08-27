package json.object;

import json.exception.JsonNotMatchedException;
import json.exception.JsonUnknownTokenException;
import json.iterator.JsonIterator;

import java.io.IOException;

public class JsonStaticElement extends JsonElement {
    public static final JsonStaticElement nullInstance = new JsonStaticElement("null", null);
    public static final JsonStaticElement trueInstance = new JsonStaticElement("true", true);
    public static final JsonStaticElement falseInstance = new JsonStaticElement("false", false);

    private String str;
    private Boolean value;

    private JsonStaticElement(String str, Boolean value) {
        this.str = str;
        this.value = value;
    }

    public Boolean getValueOrNull() {
        return this.value;
    }

    private boolean match(JsonIterator si) {
        int size = toString().length();

        for (int i = 0; i < size; i++) {
            if (!si.hasNext()) {
                return false;
            }

            if (si.current() != toString().charAt(i)) {
                return false;
            }

            si.next();
        }

        return true;
    }


    @Override
    public int hashCode() {
        return this.str.hashCode();
    }

    @Override
    protected void appendStringCache(Appendable appendable) throws IOException {
        appendable.append(this.str);
    }

    public static JsonStaticElement parse(JsonIterator si) {
        JsonStaticElement staticValue;

        switch (si.current()) {
            case 'n':
                staticValue = nullInstance;
                break;
            case 't':
                staticValue = trueInstance;
                break;
            case 'f':
                staticValue = falseInstance;
                break;
            default:
                throw new JsonUnknownTokenException(si.getPos());
        }

        if (!staticValue.match(si)) {
            throw new JsonNotMatchedException(staticValue.toString(), si.getPos());
        }

        return staticValue;
    }

}
