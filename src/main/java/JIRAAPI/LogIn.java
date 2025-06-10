package JIRAAPI;

import io.restassured.RestAssured;
import io.restassured.filter.session.SessionFilter;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class LogIn {

    public String createSession() {
        SessionFilter session = new SessionFilter();

        RestAssured.baseURI = "http://localhost:8080";
        String response = given().header("content-type", "application/json").body("{\"username\": \"khiladitanuj\", \"password\": \"kandpaltanuj25@@\"}").log().all().filter(session).when().post("/rest/auth/1/session").then().extract().response().asString();
        return response;
    }
}
