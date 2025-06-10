package Files;

import io.restassured.path.json.JsonPath;

public class ReusableMethods {

    public static JsonPath rawJsonStringToJson(String responseJsonAsAString){

        return new JsonPath(responseJsonAsAString);

    }
}
