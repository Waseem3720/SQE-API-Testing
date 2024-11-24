package package1;

import io.restassured.RestAssured;

public class APIUtility {
    // Set up the base URI
    public static void setBaseURI() {
        RestAssured.baseURI = "https://reqres.in";
    }
}

