package json.object;

import json.JsonStringIterator;
import json.exception.JsonException;
import json.exception.JsonExceptionType;

import java.math.BigDecimal;

public class JsonNumber extends JsonValue {

    BigDecimal decimal;
    String str;

    @Override
    public String toString() {
        return str;
    }

    public JsonNumber(BigDecimal decimal) {
        this.decimal = decimal;
        this.str = decimal.toString();
    }

    public static JsonNumber parse(JsonStringIterator si) {
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
            throw new JsonException(JsonExceptionType.NUMBER_FORMAT, si.getPos());
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
}
