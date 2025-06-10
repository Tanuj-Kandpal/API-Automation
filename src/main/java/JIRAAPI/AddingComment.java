package JIRAAPI;

import io.restassured.RestAssured;

import static io.restassured.RestAssured.given;

public class AddingComment {

    public void addingAComment(){
        RestAssured.baseURI= "http://localhost:8080/rest/api/2/issue/TEST-3/comment";
        //given()
    }
}
