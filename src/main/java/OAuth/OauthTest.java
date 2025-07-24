package OAuth;

import POJO.Api;
import POJO.GetCourse;
import POJO.WebAutomation;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import static io.restassured.RestAssured.given;

public class OauthTest {

    static Properties properties =  new Properties();
    static FileInputStream file = null;
    public static void main(String[] args) throws IOException {

        file = new FileInputStream("src/main/resources/secrets.properties");
        properties.load(file);

        String clientID = properties.getProperty("client_id");
        String clientSecret = properties.getProperty("client_secret");

        String[] webAutomationCoursesArray = {"Selenium Webdriver Java","Cypress","Protractor"};

        RestAssured.baseURI="https://rahulshettyacademy.com/oauthapi/getCourseDetails";
        String tokenResponse = given()
                .formParam("client_id",clientID)
                .formParam("client_secret",clientSecret)
                .formParam("grant_type","client_credentials")
                .formParam("scope","trust")
                .when().log().all()
                .post("https://rahulshettyacademy.com/oauthapi/oauth2/resourceOwner/token").then().log().all().extract().response().asPrettyString();

        JsonPath js = new JsonPath(tokenResponse);
        String accessToken = js.getString("access_token");


        GetCourse courseDetailsApiResponse = given().
                queryParam("access_token",accessToken)
                .when().log().all().get("https://rahulshettyacademy.com/oauthapi/getCourseDetails").as(GetCourse.class);

        List<Api> apiCourses = courseDetailsApiResponse.getCourses().getApi();
        for(int i=0;i<apiCourses.size();i++){
        if(apiCourses.get(i).getCourseTitle().equals("Rest Assured Automation using Java")){
            System.out.println(apiCourses.get(i).getPrice());
        }
        }

        //Get the courseTitle for webAutomation
        List<WebAutomation> webAutomation = courseDetailsApiResponse.getCourses().getWebAutomation();
        ArrayList<String> webAutomationCoursesList = new ArrayList<>();
        for(int i=0;i<webAutomation.size();i++){
            webAutomationCoursesList.add(webAutomation.get(i).getCourseTitle());
        }

        //Asserting the courseTitle
        List<String> actualWebAutomationCourses = Arrays.asList(webAutomationCoursesArray);
        Assert.assertEquals(actualWebAutomationCourses, webAutomationCoursesList);
    }
}
