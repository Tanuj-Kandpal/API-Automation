package Serialization;

import POJO.Location;
import POJO.PlaceApi;
import io.restassured.RestAssured;
import static io.restassured.RestAssured.given;

public class Serialization {
    public static void main(String[] args) {
        PlaceApi place = new PlaceApi();
        Location location = new Location();
        place.setAccuracy(50);
        place.setName("Test");
        place.setphone_number(919990000);
        place.setAddress("Test Addess");
        place.setTypes(new String[]{"Shoe Park", "shop"});
        place.setWebsite("Getanada.com");
        place.setLanguage("English-IN");
        location.setLat(999.21);
        location.setLng(33.427);
        place.setLocation(location);
        RestAssured.baseURI = "https://rahulshettyacademy.com";
        String response = given().log().all().queryParam("key", "qaclick123").body(place).when().post("/maps/api/place/add/json").then().log().all().assertThat().statusCode(200).extract().response().asPrettyString();

    }
}
