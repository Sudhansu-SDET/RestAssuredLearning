package allapicode;

import com.pojo.Api;
import com.pojo.GetCourse;
import io.restassured.parsing.Parser;
import io.restassured.path.json.JsonPath;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.List;

import static io.restassured.RestAssured.given;

public class OauthTest {

    public static void main(String[] args) throws Throwable{

        //STEP 1
        //below is to get the code by launching chrome browser in selenium , entering credentials
// Google restricted the below approach for login . Hence below set of code will not work . Manula intervention rewuired at this step

//        System.setProperty("webdriver.chrome.driver","C://Users//sudha//IdeaProjects//RestAssuredFrameworkE2E//src//main//resources//driver");
//        WebDriver driver = new ChromeDriver();
//        driver.get("https://accounts.google.com/o/oauth2/v2/auth?scope=https://www.googleapis.com/auth/userinfo.email&auth_url=https://accounts.google.com/o/oauth2/v2/auth&client_id=692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com&response_type=code&redirect_uri=https://rahulshettyacademy.com/getCourse.php");
//        driver.findElement(By.cssSelector("input[type='email']")).sendKeys("ricky.patro");
//        driver.findElement(By.cssSelector("input[type='email']")).sendKeys(Keys.ENTER);
//        Thread.sleep(3000);
//        driver.findElement(By.cssSelector("input[type='password']")).sendKeys("*******");
//        driver.findElement(By.cssSelector("input[type='email']")).sendKeys(Keys.ENTER);
//        Thread.sleep(3000);
//        String url = driver.getCurrentUrl();

        String urltolaunchinchrome = "https://accounts.google.com/o/oauth2/v2/auth?scope=https://www.googleapis.com/auth/userinfo.email&auth_url=https://accounts.google.com/o/oauth2/v2/auth&client_id=692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com&response_type=code&redirect_uri=https://rahulshettyacademy.com/getCourse.php";
        String urlWithCode = "https://rahulshettyacademy.com/getCourse.php?code=4%2F0AX4XfWh0lVnbxfwDGeOGWeBLppIAFHahqzvIkATJEOG-H5qgmw4BPtYJZPaSxdt63m4Faw&scope=email+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.email+openid&authuser=0&prompt=none" ;
        String code = urlWithCode.split("code=")[1].split("&scope")[0];
        System.out.println(code);


        //STEP 2
        //request to get access token
        String accessTokenResponse = given().urlEncodingEnabled(false).queryParam("code",code)
                .queryParam("client_id","692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
                .queryParam("client_secret","erZOWM9g3UtwNRj340YYaK_W")
                .queryParam("redirect_uri","https://rahulshettyacademy.com/getCourse.php")
                .queryParam("grant_type","authorization_code")
                .when().post("https://www.googleapis.com/oauth2/v4/token").asString() ;

        JsonPath js = new JsonPath(accessTokenResponse);
        String accessToken  = js.getString("access_token");




        //STEP 3
         // final request with aouth2.0 to access the required server
//        String response  = given().queryParam("access_token",accessToken)
//                .when().get("https://rahulshettyacademy.com/getCourse.php").asString();
//
//        System.out.println(response);


        // using deserialization in pojo classes to get values from response in step 3
        GetCourse response  = given().queryParam("access_token",accessToken).expect().defaultParser(Parser.JSON)
                .when().get("https://rahulshettyacademy.com/getCourse.php").as(GetCourse.class);



        System.out.println(response.getCourses().getWebAutomation().get(0).getCourseTitle());
        System.out.println(response.getLinkedIn());
        System.out.println(response.getCourses().getApi().get(1).getCourseTitle());

        List<Api> allAPICourses = response.getCourses().getApi();
        for (int i=0;i<allAPICourses.size();i++){

            if(allAPICourses.get(i).getCourseTitle().equalsIgnoreCase("SoapUI Webservices testing")){
                System.out.println(allAPICourses.get(i).getPrice());
            }
        }




















    }
}
