package ECommerceApi;

import ECommerceApi.POJO.Email;
import ECommerceApi.POJO.LoginResponse;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import java.io.File;

import static io.restassured.RestAssured.given;

public class ECommerceApiTest {
    public static void main(String[] args) {

        //Login Request
        Email email = new Email();
        email.setUserEmail("simplifymail@fexpost.com");
        email.setUserPassword("Test@123");

        RequestSpecification req = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com/").setContentType(ContentType.JSON).build();
        RequestSpecification login = given().spec(req).log().all().body(email);
        LoginResponse response = login.when().post("api/ecom/auth/login").then().statusCode(200).log().all().extract().response().as(LoginResponse.class);
        String token = response.getToken();
        String userId = response.getUserId();


        //Adding Product to the Cart
        RequestSpecification addToCart = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com/").addHeader("Authorization", token).build();

        RequestSpecification reqAddProduct = given().spec(addToCart).log().all().formParam("productName", "Laptop")
                .formParam("productAddedBy", userId)
                .formParam("productCategory", "fashion")
                .formParam("productSubCategory", "shirts")
                .formParam("productPrice", "1500")
                .formParam("productDescription", "Acer XS10")
                .formParam("productFor", "All")
                .multiPart("productImage", new File("C:\\Users\\kandp\\Downloads\\image.jpeg"));
        String productResponse = reqAddProduct.when().post("api/ecom/product/add-product").then().extract().response().asPrettyString();
        System.out.println(productResponse);

    }
}
