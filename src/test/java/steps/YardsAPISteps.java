package steps;

import io.cucumber.java.en.*;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.junit.Assert;
import utilities.ConfigReader;
import utilities.ElarAPIUtils;
import utilities.JDBCUtils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;


public class YardsAPISteps {
    String yardID;
    Map<String,Object> map;
    Map<String, Object> updatedYard;
    Map<String, Object> queryParams;
    Response response;
    int totalYards;
    int nonExistingYardId;

    @Given("user creates yard with post yard api call with data")
    public void user_creates_yard_with_post_yard_api_call_with_data(io.cucumber.datatable.DataTable dataTable) {
        map = dataTable.asMap(String.class,Object.class);

        /*
        POST
         */

        response = given().baseUri(ConfigReader.getProperty("ElarAppAPIBaseUri"))
                .and().accept(ContentType.JSON)
                .and().contentType(ContentType.JSON)
                .and().header("Authorization","Token 50c79942251edf8c7fd98c8036480b4f80b84c1d")
                .and().body("{\n" +
                        "    \"location\": \""+map.get("name")+"\",\n" +
                        "    \"status\": \"active\",\n" +
                        "    \"address\": \""+map.get("address")+"\",\n" +
                        "    \"city\": \""+map.get("city")+"\",\n" +
                        "    \"state\": \""+map.get("state")+"\",\n" +
                        "    \"zip_code\": \""+map.get("zip_code")+"\",\n" +
                        "    \"spots\": \""+map.get("spots")+"\",\n" +
                        "    \"contacts\": []\n" +
                        "}")
                .and().log().all()
                .when().post("/yards/");
        response.then().log().all();
//        response.then().statusCode(201);
        yardID = response.body().jsonPath().getString("id");

    }

    @When("user gets created yard with get api call")
    public void user_gets_created_yard_with_get_api_call() {
        response = given().baseUri(ConfigReader.getProperty("ElarAppAPIBaseUri"))
                .and().accept(ContentType.JSON)
                .and().header("Authorization","Token 50c79942251edf8c7fd98c8036480b4f80b84c1d")
                .and().log().all()
                .when().get("/yards/"+yardID+"/");

        response.then().log().all();
        response.then().statusCode(200);

    }

    @Then("user validates get call response having right yard details")
    public void user_validates_get_call_response_having_right_yard_details() {
        Assert.assertEquals(map.get("name"), response.body().jsonPath().getString("location"));
        Assert.assertEquals(map.get("address"), response.body().jsonPath().getString("address"));
        Assert.assertEquals(map.get("city"), response.body().jsonPath().getString("city"));
        Assert.assertEquals(map.get("state"), response.body().jsonPath().getString("state"));
        Assert.assertEquals(map.get("zip_code"), response.body().jsonPath().getString("zip_code"));
        Assert.assertEquals(map.get("spots"), response.body().jsonPath().getString("spots"));

    }

    @When("user updates created yatd with patch api call with data")
    public void user_updates_created_yatd_with_patch_api_call_with_data(io.cucumber.datatable.DataTable dataTable) {
        updatedYard = dataTable.asMap(String.class, Object.class);
        /*
        PATCH call
        1. Request
            a. URL
            b. Headers
                1. Accept
                2. Authorization
                3. Content-Type
            c. Body
         2. Response
            a. Status Code
            b. Data
         */
        response = given().baseUri(ConfigReader.getProperty("ElarAppAPIBaseUri"))
                .and().accept(ContentType.JSON)
                .and().contentType(ContentType.JSON)
                .and().header("Authorization","Token 50c79942251edf8c7fd98c8036480b4f80b84c1d")
                .body("{\n" +
                        "    \"location\": \""+updatedYard.get("name")+"\",\n" +
                        "    \"city\": \""+updatedYard.get("city")+"\",\n" +
                        "    \"state\": \""+updatedYard.get("state")+"\",\n" +
                        "    \"zip_code\": \"60173\"\n" +
                        "}")
                .and().log().all()
                .when().patch("/yards/"+yardID+"/");
        response.then().log().all();
        response.then().statusCode(200);

    }

    @Then("user validates get call response having updated yard details")
    public void user_validates_get_call_response_having_updated_yard_details() {
        // expected data -> updatedYapd
        // actual data -> response

        Assert.assertEquals(updatedYard.get("name").toString(), response.body().jsonPath().getString("location"));
        Assert.assertEquals(updatedYard.get("city").toString(), response.body().jsonPath().getString("city"));
        Assert.assertEquals(updatedYard.get("state").toString(), response.body().jsonPath().getString("state"));

    }

    @Then("user validates data is updated in DB")
    public void user_validates_data_is_updated_in_db() throws SQLException {
        // expected data -> updatedYard
        // actual data -> dbData
        JDBCUtils.establishDBConnection(
                ConfigReader.getProperty("ElarDBURL"),
                ConfigReader.getProperty("ElarDBUsername"),
                ConfigReader.getProperty("ElarDBPassword")
        );
        List<Map<String, Object>> dbData = JDBCUtils.executeQuery("select location, city, state from core_yard where id="+yardID+";");
        JDBCUtils.closeConnection();

        Assert.assertEquals(updatedYard.get("name"), dbData.get(0).get("location"));
        Assert.assertEquals(updatedYard.get("city"), dbData.get(0).get("city"));
        Assert.assertEquals(updatedYard.get("state"), dbData.get(0).get("state"));
    }

    @Then("user validates status code {int}")
    public void user_validates_status_code(Integer statusCode) {
        response.then().statusCode(statusCode);
    }

    @Then("user validates {string} error message")
    public void user_validates_error_message(String expectedErrorMessage) {
        Assert.assertEquals(expectedErrorMessage, response.body().jsonPath().getString("location[0]"));
    }

    @Then("user validates that yard is not persisted in DB")
    public void user_validates_that_yard_is_not_persisted_in_db() throws SQLException {
//        JDBCUtils.establishDBConnection(
//                ConfigReader.getProperty("ElarDBURL"),
//                ConfigReader.getProperty("ElarDBUsername"),
//                ConfigReader.getProperty("ElarDBPassword")
//        );
//        List<Map<String, Object>> dbData = JDBCUtils.executeQuery("select * from core_yard where id="+yardID+";");
//        JDBCUtils.closeConnection();

    }

    @Given("user creates {int} yards if there is less than {int} yards in database")
    public void user_creates_yards_if_there_is_less_than_yards_in_database(Integer yardsNum, Integer yardsNum2) throws SQLException {
        JDBCUtils.establishDBConnection(
                ConfigReader.getProperty("ElarDBURL"),
                ConfigReader.getProperty("ElarDBUsername"),
                ConfigReader.getProperty("ElarDBPassword")
        );
        List<Map<String, Object>> dbData = JDBCUtils.executeQuery("select * from core_yard");
        JDBCUtils.closeConnection();

        if(dbData.size()<20){
            // here we will create 20 yards with loop
        }

    }

    @When("user gets yards with get api call with query parameters")
    public void user_gets_yards_with_get_api_call_with_query_parameters(io.cucumber.datatable.DataTable dataTable) {
        queryParams = dataTable.asMap(String.class,Object.class);

        // Get Yard call
        response = ElarAPIUtils.getCall("/yards/", queryParams);           // needs ???
        response.then().statusCode(200);

    }

    @Then("user validates api response having right data")
    public void user_validates_api_response_having_right_data() {
        // queryParams -> expected data
        List<String> statuses = response.body().jsonPath().getList("results.status");
        for(String status : statuses){
            Assert.assertEquals(queryParams.get("status").toString(), status);
        }
        List<Integer> ids = response.body().jsonPath().getList("results.id");
        for(int i=1; i<ids.size(); i++){
            int id1 = ids.get(i-1);
            int id2 = ids.get(i);
            if(queryParams.get("ordering").toString().equals("-id")){
                Assert.assertTrue(id2<id1);
            }else {
                Assert.assertTrue(id2>id1);
            }


        }

        Assert.assertTrue(ids.size()==Integer.parseInt(queryParams.get("limit").toString()));


    }

    @Then("user validates data with DB")
    public void user_validates_data_with_db() throws SQLException {
        String order ="";
        order = queryParams.get("ordering").toString().equals("-id") ? "desc" : "asc";


        JDBCUtils.establishDBConnection(
                ConfigReader.getProperty("ElarDBURL"),
                ConfigReader.getProperty("ElarDBUsername"),
                ConfigReader.getProperty("ElarDBPassword")
        );
//        List<Map<String, Object>> dbData = JDBCUtils.executeQuery("select * from core_yard where status='"+queryParams.get("status")+"' order by id desc limit "+queryParams.get("limit")+" offset "+queryParams.get("offset")+";");
        List<Map<String,Object>> dbData=JDBCUtils.executeQuery("select *\n" +
                "from core_yard\n" +
                "where status='"+queryParams.get("status")+"'\n" +
                "order by id "+order+"\n" +
                "limit "+queryParams.get("limit")+" offset "+queryParams.get("offset")+";");
        JDBCUtils.closeConnection();

        // Actual -> response
        // Expected -> dbData

        List<Integer> ids = response.body().jsonPath().getList("results.id");

        for(int i=0; i<dbData.size(); i++){
            Assert.assertEquals(dbData.get(i).get("id"), ids.get(i).toString());
        }
    }

    @Given("user gets total number of yards")
    public void user_gets_total_number_of_yards() throws SQLException {
        JDBCUtils.establishDBConnection(
                ConfigReader.getProperty("ElarDBURL"),
                ConfigReader.getProperty("ElarDBUsername"),
                ConfigReader.getProperty("ElarDBPassword")
        );
        List<Map<String, Object>> dbData = JDBCUtils.executeQuery("select * from core_yard");
        JDBCUtils.closeConnection();
        totalYards=dbData.size();
    }

    @When("user gets yards with query parameter offset more than total yards")
    public void user_gets_yards_with_query_parameter_offset_more_than_total_yards() {
        Map<String,Object> queryParams = new HashMap<>();
        queryParams.put("offset",totalYards+1);
        response = ElarAPIUtils.getCall("/yards/", queryParams);

    }

    @Then("user validates {int} number of yards in response")
    public void user_validates_number_of_yards_in_response(Integer numberOfYards) {
        Assert.assertTrue(response.body().jsonPath().getList("results").size()==numberOfYards);
    }

    @When("user sends get yards api call with negative number {int} for query param offset")
    public void user_sends_get_yards_api_call_with_negative_number_for_query_param_offset(Integer negativeQueryParam) {
        Map<String, Object> queryParams = new HashMap<>();
        queryParams.put("offset", negativeQueryParam);
        response = ElarAPIUtils.getCall("/yards/", queryParams);
    }

    @Then("user validates {string} query parameter error message")
    public void user_validates_query_parameter_error_message(String errorMessage) {
        Assert.assertEquals(errorMessage, response.body().jsonPath().getString("errorMessage[0]"));
    }

    @Given("user checks what yard id doesn't exist in DB")
    public void user_checks_what_yard_id_doesn_t_exist_in_db() throws SQLException {
        JDBCUtils.establishDBConnection(
                ConfigReader.getProperty("ElarDBURL"),
                ConfigReader.getProperty("ElarDBUsername"),
                ConfigReader.getProperty("ElarDBPassword")
        );
        List<Map<String,Object>> map = JDBCUtils.executeQuery("select * from core_yard order by id desc");
        nonExistingYardId = Integer.parseInt(map.get(0).get("id").toString()) + 1;


    }

    @When("user sends get call for non existing yard")
    public void user_sends_get_call_for_non_existing_yard() {
        response = ElarAPIUtils.getCall("/yards/" + nonExistingYardId +"/");
    }
}
