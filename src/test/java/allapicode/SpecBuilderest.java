package allapicode;

import com.pojo.AddPlace;
import com.pojo.Location;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.responseSpecification;

public class SpecBuilderest {
    public static void main(String[] args){


        //body creation using POJO class and serialization
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

        //Request Spec builder
        RequestSpecification req = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
                .addQueryParam("key", "qaclick123").setContentType(ContentType.JSON).build();

        RequestSpecification request = given().spec(req).body(addPlace); // given

        ResponseSpecification resspec = new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build(); // then

        Response response = request // when
                            .when().post("/maps/api/place/add/json")
                            .then().spec(resspec).extract().response();

        System.out.println(response.asString());
    }
}
