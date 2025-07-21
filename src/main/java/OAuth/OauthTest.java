package OAuth;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLOutput;
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


        String courseDetailsApiResponse = given().
                queryParam("access_token",accessToken)
                .when().log().all().get("https://rahulshettyacademy.com/oauthapi/getCourseDetails").then().extract().response().asPrettyString();
        System.out.println(courseDetailsApiResponse);

    }
}
