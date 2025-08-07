package ECommerceApi;

import ECommerceApi.POJO.Email;
import ECommerceApi.POJO.LoginResponse;
import ECommerceApi.POJO.Order;
import ECommerceApi.POJO.OrderDetail;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.specification.RequestSpecification;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import static io.restassured.RestAssured.given;

public class ECommerceApiTest {
    public static void main(String[] args) throws IOException {

        //Fetching data from the external file
        FileInputStream file = new FileInputStream("src/main/resources/secrets.properties");
        Properties properties = new Properties();
        properties.load(file);
        String apiURI = properties.getProperty("practiceApiUri");


        //Login Request
        Email email = new Email();
        email.setUserEmail("simplifymail@fexpost.com");
        email.setUserPassword("Test@123");

        //Authentication
        RequestSpecification req = new RequestSpecBuilder().setBaseUri(apiURI).setContentType(ContentType.JSON).build();
        RequestSpecification login = given().spec(req).log().all().body(email);
        LoginResponse response = login.when().post("api/ecom/auth/login").then().statusCode(200).log().all().extract().response().as(LoginResponse.class);
        String token = response.getToken();
        String userId = response.getUserId();


        //Adding Product to the Cart
        RequestSpecification addToCart = new RequestSpecBuilder().setBaseUri(apiURI).addHeader("Authorization", token).build();

        RequestSpecification reqAddProduct = given().spec(addToCart).log().all().formParam("productName", "Laptop")
                .formParam("productAddedBy", userId)
                .formParam("productCategory", "fashion")
                .formParam("productSubCategory", "shirts")
                .formParam("productPrice", "1500")
                .formParam("productDescription", "Acer XS10")
                .formParam("productFor", "All")
                .multiPart("productImage", new File("C:\\Users\\kandp\\Downloads\\image.jpeg"));
        String productResponse = reqAddProduct.when().post("api/ecom/product/add-product").then().log().all().extract().response().asPrettyString();
        JsonPath js = new JsonPath(productResponse);
        String productId = js.getString("productId");

        //Create Order for the product
        OrderDetail od = new OrderDetail();
        od.setCountry("India");
        od.setProductOrderedId(productId);

        //List of orders
        List<OrderDetail> orderList = new ArrayList<>();
        orderList.add(od);
        Order order  = new Order();
        order.setOrders(orderList);

        RequestSpecification createOrder = new RequestSpecBuilder().setBaseUri(apiURI).addHeader("Authorization", token).setContentType(ContentType.JSON).build();
        RequestSpecification orderBody = given().spec(createOrder).log().all().body(order);
        String orderResponse = orderBody.when().post("api/ecom/order/create-order").then().log().all().extract().response().asPrettyString();

        //Delete the Orders
        RequestSpecification deleteReq = new RequestSpecBuilder().setBaseUri(apiURI).addHeader("Authorization", token).setContentType(ContentType.JSON).build();
        RequestSpecification deleteEndpoint = given().spec(deleteReq).log().all().pathParam("productId", productId);
        deleteEndpoint.log().all().when().delete("api/ecom/product/delete-product/{productId}").then().log().all().extract().response().asPrettyString();

    }
}
