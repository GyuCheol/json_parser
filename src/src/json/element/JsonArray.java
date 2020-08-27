package json.element;

import json.exception.JsonNotFoundSpecificCharException;
import json.exception.JsonUnknownTokenException;
import json.iterator.JsonIterator;

import java.io.IOException;
import java.util.*;
import java.util.function.UnaryOperator;

public class JsonArray extends JsonElement implements List<JsonElement> {
    private ArrayList<JsonElement> jsonElements = new ArrayList<>();

    @Override
    public List<JsonElement> subList(int fromIndex, int toIndex) {
        return jsonElements.subList(fromIndex, toIndex);
    }

    @Override
    public Spliterator<JsonElement> spliterator() {
        return jsonElements.spliterator();
    }

    @Override
    public void replaceAll(UnaryOperator<JsonElement> operator) {
        jsonElements.replaceAll(operator);
    }

    public int size() {
        return this.jsonElements.size();
    }

    @Override
    public ListIterator<JsonElement> listIterator(int index) {
        return jsonElements.listIterator(index);
    }

    @Override
    public ListIterator<JsonElement> listIterator() {
        return jsonElements.listIterator();
    }

    @Override
    public boolean isEmpty() {
        return jsonElements.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return false;
    }

    @Override
    public int indexOf(Object o) {
        return jsonElements.indexOf(o);
    }

    @Override
    public int lastIndexOf(Object o) {
        return jsonElements.lastIndexOf(o);
    }

    @Override
    public Iterator<JsonElement> iterator() {
        return this.jsonElements.iterator();
    }

    @Override
    public void sort(Comparator<? super JsonElement> c) {
        jsonElements.sort(c);
    }

    @Override
    public Object[] toArray() {
        return this.jsonElements.toArray();
    }

    @Override
    public <T> T[] toArray(T[] a) {

        if (a instanceof JsonElement[]) {
            return this.jsonElements.toArray(a);
        }

        return a;
    }

    @Override
    public JsonElement get(int index) {
        return jsonElements.get(index);
    }

    @Override
    public JsonElement set(int index, JsonElement element) {
        return jsonElements.set(index, element);
    }

    @Override
    public boolean add(JsonElement jsonElement) {
        return this.jsonElements.add(jsonElement);
    }

    @Override
    public void add(int index, JsonElement element) {
        jsonElements.add(index, element);
    }

    @Override
    public JsonElement remove(int index) {
        return jsonElements.remove(index);
    }

    @Override
    public boolean equals(Object o) {

        if (o instanceof JsonArray) {
            return jsonElements.equals(((JsonArray) o).jsonElements);
        }

        return false;
    }

    @Override
    public boolean remove(Object o) {
        return this.jsonElements.remove(o);
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return this.jsonElements.containsAll(c);
    }


    @Override
    public boolean removeAll(Collection<?> c) {
        return this.jsonElements.removeAll(c);
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return this.jsonElements.retainAll(c);
    }

    @Override
    public void clear() {
        this.jsonElements.clear();
    }

    @Override
    public boolean addAll(Collection<? extends JsonElement> c) {
        return jsonElements.addAll(c);
    }

    @Override
    public boolean addAll(int index, Collection<? extends JsonElement> c) {
        return jsonElements.addAll(index, c);
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

    @Override
    public int hashCode() {
        int hash = 0;

        for (JsonElement json: jsonElements) {
            hash += json.hashCode();
            hash <<= 1;
        }

        return hash;
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

}
