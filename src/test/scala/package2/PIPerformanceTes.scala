package package2

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.concurrent.duration._

class APIPerformanceTest extends Simulation {

  // Base URL for the application
  val baseUrl = "https://reqres.in"

  // HTTP Protocol configuration
  val httpProtocol = http
    .baseUrl(baseUrl) // Base URL for requests
    .acceptHeader("application/json") // Accept JSON responses

  // Scenario for GET Request
  val scnGet = scenario("GET User")
    .exec(
      http("Get User")
        .get("/api/users/2")
        .check(status.is(200))
    )

  // Scenario for POST Request
  val scnPost = scenario("POST Create User")
    .exec(
      http("Create User")
        .post("/api/users")
        .body(StringBody("""{ "name": "John", "job": "Engineer" }""")).asJson
        .check(status.is(201))
    )

  // Scenario for PUT Request
  val scnPut = scenario("PUT Update User")
    .exec(
      http("Update User")
        .put("/api/users/2")
        .body(StringBody("""{ "name": "John", "job": "Manager" }""")).asJson
        .check(status.is(200))
    )

  // Scenario for DELETE Request
  val scnDelete = scenario("DELETE User")
    .exec(
      http("Delete User")
        .delete("/api/users/2")
        .check(status.is(204))
    )

  // Set up the simulation
  setUp(
    scnGet.inject(atOnceUsers(10)), // 10 users for GET
    scnPost.inject(rampUsers(10).during(10.seconds)), // 10 users ramping over 10 seconds for POST
    scnPut.inject(atOnceUsers(5)), // 5 users for PUT
    scnDelete.inject(constantUsersPerSec(2).during(15.seconds)) // 2 users per second for DELETE
  ).protocols(httpProtocol)
}
