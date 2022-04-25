package allapicode;

import com.files.Payload;
import com.files.ReusableMethod;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;


public class GoogleAPI {
    public static void main(String args[]){
        RestAssured.baseURI = "https://rahulshettyacademy.com";

        ///post request
        String response  = given().queryParam("key","qaclick123").header("Content-Type","application/json")
                .body(Payload.AddPlace()).when().post("maps/api/place/add/json").then().assertThat().statusCode(200)
                .body("scope",equalTo("APP")).header("server","Apache/2.4.41 (Ubuntu)").extract().response().asString();
        //System.out.println(response);

        JsonPath jsonPath = new JsonPath(response); // for parsing json , takes string as input
        String placeID = jsonPath.getString("place_id");// place id  created in response for above post reeuest
         System.out.println(placeID);

        // put/update request
        String newAddress ="Odisha-ricky home";
        given().queryParam("key","qaclick123").header("Content-Type","application/json")
                .body("{\n" +
                        "\"place_id\":\""+placeID+"\",\n" +
                        "\"address\":\""+newAddress+"\",\n" +
                        "\"key\":\"qaclick123\"\n" +
                        "}").when().put("maps/api/place/update/json").then().assertThat().statusCode(200)
                .body("msg",equalTo("Address successfully updated"));

        //Get request
        String getPlaceResponse = given().queryParam("key","qaclick123").queryParam("place_id",placeID).header("Content-Type","application/json")
                .when().get("maps/api/place/get/json")
                .then().assertThat().statusCode(200).extract().response().asString();

        JsonPath js = ReusableMethod.rawToJson(getPlaceResponse); // for parsing json , takes string as input
        String newAddressresponse = js.getString("address");// place id  created in response for above post reeuest
        System.out.println(newAddressresponse);

        Assert.assertEquals(newAddress,newAddressresponse, "Both data is matching");

    }
}
