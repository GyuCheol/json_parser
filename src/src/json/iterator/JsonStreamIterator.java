package json.iterator;

import json.exception.JsonException;
import json.exception.JsonIOException;

import java.io.IOException;
import java.io.InputStreamReader;

public class JsonStreamIterator extends JsonIterator {
    private InputStreamReader reader;
    private char[] buffer;
    private int curLimit;
    private int offset;

    public JsonStreamIterator(InputStreamReader reader, int bufferSize) {
        this.reader = reader;
        this.buffer = new char[bufferSize];
        this.curLimit = 0;
    }

    @Override
    public char current() {
        return this.buffer[pos];
    }


    @Override
    public boolean hasNext() {

        // -1이면 스트림의 모든 버퍼를 다 읽은 상태
        if (this.curLimit == -1) {
            return false;
        }

        if (super.pos == this.curLimit) {
            try {
                this.offset += this.curLimit;
                this.curLimit = reader.read(buffer, 0, buffer.length);

                // -1이면 모든 버퍼를 다 읽음.
                if (curLimit == -1) {
                    return false;
                } else {
                    super.pos = 0;
                }
            } catch (IOException exception) {
                throw new JsonIOException(exception, super.pos + this.offset);
            }
        }

        return true;
    }
}
