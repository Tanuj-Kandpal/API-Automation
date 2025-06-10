package JIRAAPI;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class CreatingJiraIssue {
    @Test
    public void creatingIssue(){
        LogIn login = new LogIn();
        String session = login.createSession();
        JsonPath json = new JsonPath(session);
        String sessionName = json.getString("session.name");
        String sessionValue = json.getString("session.value");
        String sessionId = sessionName+"="+sessionValue;
        RestAssured.baseURI = "http://localhost:8080";
        String response = given().header("Content-Type","application/json").header("Cookie",sessionId).body("{\n" +
                "    \"fields\": {\n" +
                "       \"project\":\n" +
                "       {\n" +
                "          \"key\": \"TEST\"\n" +
                "       },\n" +
                "       \"summary\": \"Bug Summary\",\n" +
                "       \"description\": \"Creating my new bug\",\n" +
                "       \"issuetype\": {\n" +
                "          \"name\": \"Bug\"\n" +
                "       }\n" +
                "    }\n" +
                "}").when().post("/rest/api/2/issue").then().extract().response().asString();

        JsonPath js = new JsonPath(response);
        String issueID =  js.getString("id");
        String key = js.getString("key");
        String issueDetails = given().pathParam(issueID, key).log().all().get("/rest/api/2/issue/{"+issueID+"}").then().log().all().extract().response().asString();
        System.out.println(issueDetails);
       // return response;
    }
}
