package json.element;

import json.exception.JsonNumberFormatException;
import json.iterator.JsonIterator;

import java.io.IOException;
import java.math.BigDecimal;

public class JsonNumber extends JsonElement implements Comparable<JsonNumber> {
    private BigDecimal decimal;

    public JsonNumber(BigDecimal decimal) {
        this.decimal = decimal;
    }

    public JsonNumber(String decimal) {
        this(new BigDecimal(decimal));
    }

    public JsonNumber(int value) {
        this(new BigDecimal(value));
    }

    public JsonNumber(double value) {
        this(new BigDecimal(value));
    }

    public JsonNumber(float value) {
        this(new BigDecimal(value));
    }

    @Override
    protected void appendString(Appendable appendable) throws IOException {
        appendable.append(this.decimal.toString());
    }

    public static JsonNumber parse(JsonIterator si) {
        // Number 가능한 포맷
        // 12389123 정수
        // 1231.1232 실수
        // 1.23E10 지수 표기법
        // 숫자, ., E만 가능함!
        // BigDecimal 이용해서 처리
        StringBuilder sb = new StringBuilder();

        find_number:
        while (si.hasNext() && !si.isWhiteSpace()) {
            char tmp = si.current();

            switch (si.current()) {
                case ',':
                case ']':
                case '}':
                    break find_number;
            }

            sb.append(tmp);
            si.next();
        }

        try {
            return new JsonNumber(new BigDecimal(sb.toString()));
        } catch (NumberFormatException ex) {
            throw new JsonNumberFormatException(ex, si.getPos());
        }
    }

    @Override
    public int hashCode() {
        return this.decimal.hashCode();
    }

    @Override
    public boolean equals(Object obj) {

        if (obj instanceof JsonNumber) {
            return this.decimal.equals(((JsonNumber)obj).decimal);
        }

        return false;
    }

    @Override
    public int compareTo(JsonNumber o) {
        return this.decimal.compareTo(o.decimal);
    }
}
