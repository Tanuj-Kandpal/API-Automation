package ComplexJsonAPIAutomation;

import Files.Payload;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;

public class ComplexJson {
    public static void main(String[] args) {

        JsonPath json = new JsonPath(Payload.coursePrice());
        //Print Number of courses Size
        int coursesSize = json.getInt("courses.size()");
       //Print Purchase Amount
        int purchaseAmount = json.getInt("dashboard.purchaseAmount");
        //Print Title of First Course
        String firstCourseTitle = json.getString("courses[0].title");
        //Print all course title in respective prices
        for(int i=0;i<coursesSize;i++){
          String courseName  =  json.getString("courses["+i+"].title");
          String coursePrices = json.getString("courses["+i+"].price");
        }

        //Print number of copies sold by RPA
        String rpaCopies = json.getString("courses[2].copies");

        //Verify if sum of courses price is qual to purchaseAmount
        int totalPrice = 0;
        int expectedPrice = json.getInt("dashboard.purchaseAmount");
        for(int i=0;i<coursesSize;i++){
           int coursePrice =  json.getInt("courses["+i+"].price");
           int noOfCopies = json.getInt("courses["+i+"].copies");
           totalPrice  =totalPrice+ noOfCopies * coursePrice;
        }
        System.out.println(totalPrice);
        Assert.assertEquals(totalPrice,expectedPrice);

    }
}
