//package JIRAAPI;
//
//import io.restassured.RestAssured;
//import io.restassured.path.json.JsonPath;
//import org.testng.annotations.Test;
//
//import java.io.File;
//
//import static io.restassured.RestAssured.given;
//
//public class AddAttachment {
//
//    @Test
//    public void addingAttachment(){
//        RestAssured.baseURI= "http://localhost:8080";
//        CreatingJiraIssue creatingJiraIssue = new CreatingJiraIssue();
//        //String creatingIssue  = creatingJiraIssue.creatingIssue();
//        JsonPath json = new JsonPath(creatingIssue);
//        System.out.println(creatingIssue);
//        String key = json.getString("key");
//        System.out.println(key);
//        given().header("X-Atlassian-Token", "no-check").header("Content-Type","multipart/form-data").multiPart("File",new File("src/main/resources/jira.txt"))
//                .when().post("/rest/api/2/issue/"+key+"/attachments").then().log().all();
//
//    }
//}
