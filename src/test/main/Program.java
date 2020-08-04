package main;

import json.JsonParser;
import json.object.JsonArray;
import json.object.JsonValue;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.charset.StandardCharsets;

public class Program {



    public static void main(String[] args) throws Exception {
        JsonParser parser = new JsonParser(StandardCharsets.UTF_8);



        try (FileInputStream is = new FileInputStream("../sample/generated.json")) {
            JsonArray ary = parser.parse(is);
        }
    }
}
