package PlaceAPIAutomation;

import Files.Payload;
import Files.ReusableMethods;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;

import java.io.IOException;
import java.nio.file.*;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class Basics {

    public static void main(String[] args) throws IOException {

        //given - all input field
        //when - Submit the API - resource, http method
        //then - Validate the response.
        Path path = Path.of("C:\\Users\\kandp\\IdeaProjects\\APIAutomation\\src\\main\\resources\\addPlace.json");
        String data = new String(Files.readAllBytes(path));

        RestAssured.baseURI = "https://rahulshettyacademy.com";
        String response = given().queryParam("key", "qaclick123")
                .header("Content-Type", "application/json")
                .body(data).log().all()
                .when().post("maps/api/place/add/json")
                //ALL the ASSERTIONS IN THEN
                .then().assertThat().statusCode(200)
                .body("scope", equalTo("APP"))
                .header("Server", equalTo("Apache/2.4.52 (Ubuntu)"))
                .extract().response().asString();

        System.out.println("==================RESPONSE==================================");
        System.out.println(response);
        //JSON PATH is the class which will take string as an input and convert it to the json and helps us to retrieve specific value from the json.
        JsonPath json = ReusableMethods.rawJsonStringToJson(response);
        String placeIdValue = json.getString("place_id");
        System.out.println(placeIdValue);

        //UPDATE PLACE
        String newAddress = "UK03 100";
        given().log().all()
                .queryParam("key", "qaclick123")
                .header("Content-Type", "application/json")
                .body(Payload.updatePlace(placeIdValue, newAddress))
                .when().put("maps/api/place/update/json").then().assertThat().statusCode(200)
                .body("msg", equalTo("Address successfully updated"));

        //GET PLACE
        String getPlaceResponse =  given().log().all()
                .queryParam("key", "qaclick123")
                .queryParam("place_id", placeIdValue)
                .when().get("maps/api/place/get/json")
                .then().statusCode(200).log().all().extract().response().asString();
        JsonPath getPlaceJson = new JsonPath(getPlaceResponse);
        //Extracting value of address from the json with the help of jsonpath
        String expectedAddressValue = getPlaceJson.getString("address");
        Assert.assertEquals(newAddress,expectedAddressValue);
    }

}
