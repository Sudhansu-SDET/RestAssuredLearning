package com.Files;

import io.restassured.path.json.JsonPath;

public class ReusableMethod {
    public static JsonPath rawToJson(String response){
        JsonPath jsonPath = new JsonPath(response);
        return jsonPath;
    }
}

