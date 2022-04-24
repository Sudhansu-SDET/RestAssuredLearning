package allapicode;

import com.Files.Payload;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;

public class complexJsonparse {

    public static void main(String args[]) {
        JsonPath js = new JsonPath(Payload.mockCoursePrice());
        // print number of courses returned in json response
        int count = js.getInt("courses.size()");
        System.out.println(count);

        // print purchase amount in json response
        int purchaseAmount = js.getInt("dashboard.purchaseAmount");
        System.out.println(purchaseAmount);

        // print title of first course in json response
        String fistIndexTitle = js.getString("courses[0].title");
        System.out.println(fistIndexTitle);

        //print all course title and respective prices
        for (int i = 0; i < count; i++) {
            String courseTitles = js.getString("courses[" + i + "].title");
            int coursePrices = js.getInt("courses[" + i + "].price");
            System.out.println(courseTitles + " - " + coursePrices);
        }

        //print all course title and respective prices
        int totalValue = 0;
        int numberOfCopiesSold;
        for (int j = 0; j < count; j++) {
            numberOfCopiesSold = js.getInt("courses[" + j + "].copies");
            totalValue = totalValue + numberOfCopiesSold;
        }
        System.out.println(totalValue);

        //verify sum of all cource prices matches with purchase amount
        int eachPrice;
        int eachCourseAmount;
        int numberOfCopiesSold2;
        int totalCalculatedPrice = 0;
        for (int k = 0; k < count; k++) {
            numberOfCopiesSold2 = js.getInt("courses[" + k + "].copies");
            eachPrice = js.getInt("courses[" + k + "].price");
            eachCourseAmount = numberOfCopiesSold2 * eachPrice;
            totalCalculatedPrice = totalCalculatedPrice + eachCourseAmount;
        }
        Assert.assertEquals(totalCalculatedPrice,purchaseAmount);


    }
}


