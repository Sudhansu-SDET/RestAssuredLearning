package libraryapi;

import com.Files.Payload;
import com.Files.ReusableMethod;
import io.restassured.RestAssured;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class DynamicJson {

    @Test
    public void addBook(){
        RestAssured.baseURI = "http://216.10.245.166";

        String response  = given().header("Content-Type","application/json")
                .body(Payload.AddBook("",""))
                .when().post("/Library/Addbook.php")
                .then().statusCode(200).extract().response().asString();

        System.out.println(ReusableMethod.rawToJson(response).get("ID"));




    }
}
