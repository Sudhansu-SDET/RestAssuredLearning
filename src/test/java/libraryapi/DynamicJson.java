package libraryapi;

import com.files.Payload;
import com.files.ReusableMethod;
import io.restassured.RestAssured;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class DynamicJson {

    @Test(dataProvider="BooksData")
    public void addBook(String isbn , String aisle) {
        RestAssured.baseURI = "http://216.10.245.166";

        String response = given().header("Content-Type", "application/json")
                .body(Payload.AddBook(isbn, aisle))
                .when().post("/Library/Addbook.php")
                .then().statusCode(200).extract().response().asString();

        System.out.println(ReusableMethod.rawToJson(response).get("ID"));
    }

    @Test(dataProvider="BooksData")
    public void deleteBook(String isbn , String aisle) {
        RestAssured.baseURI = "http://216.10.245.166";

        String response = given().header("Content-Type", "application/json")
                .body(Payload.deleteBook(isbn,aisle)).log().all()
                .when().delete("/Library/DeleteBook.php")
                .then().statusCode(200).extract().response().asString();

        String responsemsg  = ReusableMethod.rawToJson(response).getString("msg");
        System.out.println(responsemsg);
        Assert.assertEquals(responsemsg,"book is successfully deleted");

    }


    @DataProvider(name="BooksData")
    public Object[][] getData(){

        return  new Object[][] {{"sp1","987"}};
    }



}
