package obj;

import json.annotation.JsonProperty;
import json.annotation.Unserialization;

public class User {

    @JsonProperty(key="number")
    private int id;
    @JsonProperty()
    private String lastName;
    @Unserialization
    private String firstName;

    private int age;


    public User(int id, String lastName, String firstName, int age) {
        this.id = id;
        this.lastName = lastName;
        this.firstName = firstName;
        this.age = age;
    }

}
