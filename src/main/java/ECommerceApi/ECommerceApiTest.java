package ECommerceApi;

import ECommerceApi.POJO.Email;
import ECommerceApi.POJO.LoginResponse;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class ECommerceApiTest {
    public static void main(String[] args) {

        Email email = new Email();
        email.setUserEmail("simplifymail@fexpost.com");
        email.setUserPassword("Test@123");

        RequestSpecification req = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com/").setContentType(ContentType.JSON).build();
        RequestSpecification login = given().spec(req).log().all().body(email);
        LoginResponse response = login.when().post("api/ecom/auth/login").then().statusCode(200).log().all().extract().response().as(LoginResponse.class);
        System.out.println(response.getToken());
        System.out.println(response.getUserId());


    }
}
