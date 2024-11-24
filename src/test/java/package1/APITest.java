package package1;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class APITest {

    @BeforeClass
    public void setup() {
        APIUtility.setBaseURI();
    }

    @Test
    public void testGetUser() {
        Response response = 
            given()
                .when()
                .get("/api/users/2");
        
        System.out.println("GET Response Code: " + response.statusCode());
        System.out.println("GET Response Body: " + response.prettyPrint());
        System.out.println("GET Response Headers: " + response.headers());
        System.out.println("GET Response Time: " + response.time() + "ms");

        response.then()
                .statusCode(200)
                .body("data.id", equalTo(2))
                .body("data.first_name", equalTo("Janet"));
    }

    @Test
    public void testCreateUser() {
        String requestBody = "{ \"name\": \"John\", \"job\": \"Engineer\" }";

        Response response = 
            given()
                .contentType("application/json")
                .body(requestBody)
                .when()
                .post("/api/users");
        
        System.out.println("POST Response Code: " + response.statusCode());
        System.out.println("POST Response Body: " + response.prettyPrint());
        System.out.println("POST Response Headers: " + response.headers());
        System.out.println("POST Response Time: " + response.time() + "ms");

        response.then()
                .statusCode(201)
                .body("name", equalTo("John"))
                .body("job", equalTo("Engineer"));
    }

    @Test
    public void testUpdateUser() {
        String requestBody = "{ \"name\": \"John\", \"job\": \"Manager\" }";

        Response response = 
            given()
                .contentType("application/json")
                .body(requestBody)
                .when()
                .put("/api/users/2");
        
        System.out.println("PUT Response Code: " + response.statusCode());
        System.out.println("PUT Response Body: " + response.prettyPrint());
        System.out.println("PUT Response Headers: " + response.headers());
        System.out.println("PUT Response Time: " + response.time() + "ms");

        response.then()
                .statusCode(200)
                .body("name", equalTo("John"))
                .body("job", equalTo("Manager"));
    }

    @Test
    public void testDeleteUser() {
        Response response = 
            given()
                .when()
                .delete("/api/users/2");
        
        System.out.println("DELETE Response Code: " + response.statusCode());
        System.out.println("DELETE Response Body: " + response.prettyPrint());
        System.out.println("DELETE Response Headers: " + response.headers());
        System.out.println("DELETE Response Time: " + response.time() + "ms");

        response.then()
                .statusCode(204);
    }
}
