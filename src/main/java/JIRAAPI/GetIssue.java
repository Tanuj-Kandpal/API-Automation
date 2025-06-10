//package JIRAAPI;
//
//import io.restassured.RestAssured;
//import io.restassured.path.json.JsonPath;
//import org.testng.annotations.Test;
//
//import static io.restassured.RestAssured.given;
//
//public class GetIssue {
//
//    @Test
//    public void getIssue() {
//        RestAssured.baseURI = "http://localhost:8080";
//        CreatingJiraIssue creatingIssue = new CreatingJiraIssue();
//        ///rest/api/2/issue/{issueIdOrKey}
//        String issue = creatingIssue.creatingIssue();
//        JsonPath js = new JsonPath(issue);
//        String issueID =  js.getString("id");
//        String key = js.getString("key");
//        String issueDetails = given().pathParam(issueID, key).log().all().get("/rest/api/2/issue/{"+issueID+"}").then().log().all().extract().response().asString();
//        System.out.println(issueDetails);
//    }
//}
