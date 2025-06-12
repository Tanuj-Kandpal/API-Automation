package LibraryAPI;

import Files.Payload;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class DynamicJson {

    @BeforeMethod
    public void baseURI(){
        RestAssured.baseURI = "https://rahulshettyacademy.com";
    }

    @Test(dataProvider = "BookData")
    public String addBook(String bookName, double isbn) {
        String response = given().header("Content-Type", "application/json")
                .body(Payload.addBookPayload(bookName,isbn))
                .when().post("/Library/Addbook.php")
                .then().statusCode(200).extract().response().asString();
        JsonPath js = new JsonPath(response);
        String id = js.getString("ID");
        return id;
    }

    @Test(dataProvider = "BookData")
    public void deleteBook(String bookName,double isbn){
        String deleteResponse = given().header("Content-Type","application/json").body(addBook(bookName,isbn))
                .when().post("/Library/DeleteBook.php")
                .then().statusCode(200).extract().response().asString();
        System.out.println(deleteResponse);
    }

    @DataProvider(name="BookData")
    public Object[][] getData() {
        return new Object[][]{
                {"qwe",Math.random()*10000}, {"rew",Math.random()*10000 }, {"ytr",Math.random()*10000}
        };
    }
}
