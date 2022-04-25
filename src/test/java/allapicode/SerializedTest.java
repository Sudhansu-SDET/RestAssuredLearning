package allapicode;

import com.pojo.AddPlace;
import com.pojo.Location;
import io.restassured.RestAssured;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;

public class SerializedTest {
    public static void main(String[] args){

        RestAssured.baseURI="https://rahulshettyacademy.com/";

        AddPlace addPlace = new AddPlace();
        addPlace.setAccuracy(50);
        addPlace.setAddress("29, side layout, cohen 09");
        addPlace.setLanguage("French-IN");
        addPlace.setName("ricky new home");
        addPlace.setPhone_number("123456789");

        List<String> myListTypes = new ArrayList<String>();
        myListTypes.add("shoe park");
        myListTypes.add("shop");
        addPlace.setTypes(myListTypes);

        Location loc = new Location();
        loc.setLat(-38.383494);
        loc.setLng(33.427362);
        addPlace.setLocation(loc);


        String response = given().queryParam("key","qaclick123")
                            .body(addPlace)
                            .when().post("maps/api/place/add/json")
                            .then().statusCode(200).extract().response().asString();

        System.out.println(response);
    }
}
