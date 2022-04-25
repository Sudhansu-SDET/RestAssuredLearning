package allapicode;

import io.restassured.path.json.JsonPath;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import static io.restassured.RestAssured.given;

public class OauthTest {

    public static void main(String args[]) throws Throwable{

        //below is to get the code by launching chrome browser in selenium , entering credentials
// Google restricted the below approach for login . Hence below set of code will not work . Manula intervention rewuired at this step

//        System.setProperty("webdriver.chrome.driver","C://Users//sudha//IdeaProjects//RestAssuredFrameworkE2E//src//main//resources//driver");
//        WebDriver driver = new ChromeDriver();
//        driver.get("https://accounts.google.com/o/oauth2/v2/auth?scope=https://www.googleapis.com/auth/userinfo.email&auth_url=https://accounts.google.com/o/oauth2/v2/auth&client_id=692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com&response_type=code&redirect_uri=https://rahulshettyacademy.com/getCourse.php");
//        driver.findElement(By.cssSelector("input[type='email']")).sendKeys("ricky.patro");
//        driver.findElement(By.cssSelector("input[type='email']")).sendKeys(Keys.ENTER);
//        Thread.sleep(3000);
//        driver.findElement(By.cssSelector("input[type='password']")).sendKeys("Gmail@2016");
//        driver.findElement(By.cssSelector("input[type='email']")).sendKeys(Keys.ENTER);
//        Thread.sleep(3000);
//        String url = driver.getCurrentUrl();
        String urltolaunchinchrome = "https://accounts.google.com/o/oauth2/v2/auth?scope=https://www.googleapis.com/auth/userinfo.email&auth_url=https://accounts.google.com/o/oauth2/v2/auth&client_id=692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com&response_type=code&redirect_uri=https://rahulshettyacademy.com/getCourse.php";
        String urlWithCode = "https://rahulshettyacademy.com/getCourse.php?code=4%2F0AX4XfWgrwaArcHhwGgGGpgXuaLnys9wS5NbEAjfWaxutNtmbtG2snm4K7S_wLXic0wqFAA&scope=email+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.email+openid&authuser=0&prompt=consent";
        String code = urlWithCode.split("code=")[1].split("&scope")[0];
        System.out.println(code);



        //request to get access token
        String accessTokenResponse = given().urlEncodingEnabled(false).queryParam("code",code)
                .queryParam("client_id","692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
                .queryParam("client_secret","erZOWM9g3UtwNRj340YYaK_W")
                .queryParam("redirect_uri","https://rahulshettyacademy.com/getCourse.php")
                .queryParam("grant_type","authorization_code")
                .when().log().all().post("https://www.googleapis.com/oauth2/v4/token").asString() ;

        JsonPath js = new JsonPath(accessTokenResponse);
        String accessToken  = js.getString("access_token");





         // final request with aouth2.0 to access the required server
        String response  = given().queryParam("access_token",accessToken)
                .when().get("https://rahulshettyacademy.com/getCourse.php").asString();

        System.out.println(response);





    }
}
