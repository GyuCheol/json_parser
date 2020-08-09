# Java Json Parser Project

- Purpose : studying to know json architecture and parsing algorithm (without any logical supports but it used standard library like ArrayList)
- Language : Java
- Performance : 

※ reference : https://www.json.org/json-ko.html (just to know the architecture not the logic)

- Example

```Java
JsonConverter parser = new JsonConverter(StandardCharsets.UTF_8);

try (FileInputStream is = new FileInputStream("../sample/generated.json")) {
    JsonArray ary = parser.parse(is);

    for (JsonValue value: ary) {
        System.out.println(value);
    }
}
```

- Class Design
<img src="./img/diagram.JPG"/>
