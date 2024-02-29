package utilities;

import io.restassured.response.Response;
import org.junit.Assert;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class RestAPITest {
    public static void main(String args[]) throws SQLException {
        /*
        POST Yard
        Request:
        1. URL
        2. Headers
            a. Accept:application/json
            b. Authorization: Token
            c. Content-type: application/json
        3. Payload

        Response:
        1.Data
        2.Status code

         */

        Response response = given().baseUri("https://elarbridgelogisticsmindtek.space/en-us/api/v2")
                .and().header("Accept","application/json")
                .and().header("Authorization", "Token 50c79942251edf8c7fd98c8036480b4f80b84c1d")
                .and().header("Content-Type", "application/json")
                .and().body("{\n" +
                        "    \"location\": \"Arslan Yard 3\",\n" +
                        "    \"status\": \"active\",\n" +
                        "    \"address\": \"123 abc st\",\n" +
                        "    \"city\": \"New York\",\n" +
                        "    \"state\": \"NY\",\n" +
                        "    \"zip_code\": \"60173\",\n" +
                        "    \"spots\": \"1123\",\n" +
                        "    \"contacts\": []\n" +
                        "}")
                .and().log().all() // logs request's details
                .when().post("/yards/");

        // Log -> print all details of response
        response.then().log().all();

        response.then().statusCode(201);
        String yardID = response.body().jsonPath().getString("id");

        /*
        GET Call
        Request:
        1. URL
        2. Headers:
            a. Accept:application/json
            b. Authorization: Token

        Response:
        1.Status
        2.Data
         */

        Response getResponse = given().baseUri("https://elarbridgelogisticsmindtek.space/en-us/api/v2")
                .and().header("Accept", "application/json")
                .and().header("Authorization", "Token 50c79942251edf8c7fd98c8036480b4f80b84c1d")
                .and().log().all()
                .when().get("/yards/"+yardID+"/");

        getResponse.then().log().all();
        getResponse.then().statusCode(200);

        JDBCUtils.establishDBConnection(ConfigReader.getProperty("ElarDBURL"),
                ConfigReader.getProperty("ElarDBUsername"),
                ConfigReader.getProperty("ElarDBPassword"));
        List<Map<String, Object>> data = JDBCUtils.executeQuery("select * from core_yard where id="+yardID);

        Assert.assertTrue(data.size()==1);




    }

}
