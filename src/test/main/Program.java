package main;

import json.JsonConverter;
import json.element.JsonArray;
import json.element.JsonElement;

import java.io.FileInputStream;
import java.nio.charset.StandardCharsets;

public class Program {

    public static void main(String[] args) throws Exception {
        JsonConverter parser = new JsonConverter(StandardCharsets.UTF_8);

        try (FileInputStream is = new FileInputStream("../sample/generated.json")) {
            JsonArray ary = parser.parse(is);

            for (JsonElement value: ary) {
                System.out.println(value);
            }
        }
    }
}









