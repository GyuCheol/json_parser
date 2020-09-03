package json.element;

import json.exception.JsonNotFoundSpecificCharException;
import json.exception.JsonUnknownTokenException;
import json.iterator.JsonIterator;

import java.io.IOException;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.UnaryOperator;

public class JsonArray extends JsonElement implements Iterable<JsonElement> {
    private ArrayList<JsonElement> jsonElements = new ArrayList<>();


    public void clear() {
        if (size() > 0) {
            super.isRecently = false;
            this.jsonElements.clear();
        }
    }

    public JsonElement get(int index) {
        return this.jsonElements.get(index);
    }

    public int size() {
        return this.jsonElements.size();
    }

    public void add(JsonElement element) {
        super.isRecently = false;
        this.jsonElements.add(element);
    }

    public boolean remove(JsonElement element) {
        boolean result = this.jsonElements.remove(element);

        if (result) {
            super.isRecently = false;
        }

        return result;
    }

    public JsonElement remove(int index) {
        JsonElement tmp = this.jsonElements.remove(index);

        super.isRecently = false;

        return tmp;
    }

    public boolean contain(JsonElement element) {
        return this.jsonElements.contains(element);
    }

    @Override
    protected void appendString(Appendable appendable) throws IOException {
        appendable.append('[');

        for (int i = 0; i < jsonElements.size() - 1; i++) {
            jsonElements.get(i).appendString(appendable);
            appendable.append(",");
        }

        // 요소가 있다면 마지막 제거
        if (this.size() > 0) {
            appendable.append(jsonElements.get(jsonElements.size() - 1).toString());
        }

        appendable.append(']');
    }


    public static JsonArray parse(JsonIterator si) {
        JsonArray jsonArray = new JsonArray();
        boolean isFinished = false;

        si.next();
        si.skipWhiteSpaces();

        // 아무 요소 없다면 바로 리턴
        if (si.current() == ']') {
            return jsonArray;
        }

        si_loop:
        while (si.hasNext()) {

            JsonElement item = JsonElement.parse(si, true);

            jsonArray.add(item);

            si.skipWhiteSpaces();

            if (!si.hasNext()) {
                break;
            }

            switch (si.current()) {
                case ',':
                    si.next();
                    si.skipWhiteSpaces();
                    break;
                case ']':
                    si.next();
                    isFinished = true;
                    break si_loop;
                default:
                    throw new JsonUnknownTokenException(si.getPos());
            }
        }

        if (!isFinished) {
            throw new JsonNotFoundSpecificCharException(']', si.getPos());
        }

        return jsonArray;
    }

    @Override
    public Iterator<JsonElement> iterator() {
        return this.jsonElements.iterator();
    }

    @Override
    public void forEach(Consumer<? super JsonElement> action) {
        this.jsonElements.forEach(action);
    }

    @Override
    public Spliterator<JsonElement> spliterator() {
        return this.jsonElements.spliterator();
    }
}
