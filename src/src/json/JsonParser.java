package json;

import json.obj.JsonValue;
import json.task.JsonTask;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Stack;

public class JsonParser {
    private final int BUFFER_SIZE;
    private Charset charset;
    private int pos = 0;

    /***
     * If it doesn't set a charset, it will set on utf-8. (BUFFER_SIZE will also be set on 512)
     */
    public JsonParser() {
        this(StandardCharsets.UTF_8, 512);
    }

    public JsonParser(Charset charset) {
        this(charset, 512);
    }

    public JsonParser(Charset charset, int bufferSize) {
        this.BUFFER_SIZE = bufferSize;
        this.charset = charset;
    }

    private String getString(InputStream in) throws IOException {
        InputStreamReader reader = new InputStreamReader(in, charset);
        StringBuilder sb = new StringBuilder(BUFFER_SIZE * 2);
        char[] buffer = new char[BUFFER_SIZE];
        int offset = 0;
        int read = reader.read(buffer, offset, BUFFER_SIZE);

        while (read > 0) {
            sb.append(buffer, 0, read);
            offset += read;
            read = reader.read(buffer);
        }

        return sb.toString();
    }

    public JsonValue parse(InputStream in) throws IOException {
        Stack<JsonTask> taskStack = new Stack<>();
        Stack<JsonValue> jsonStack = new Stack<>();
        String json = getString(in);
        JsonValue root = null;
        int pos = 0;

        taskStack.add(JsonTask.getFindSymbolTask());

        while (taskStack.isEmpty() == false) {
            JsonTask task = taskStack.pop();

            
            task.doTask(json, pos);

        }

        return root;
    }
}
