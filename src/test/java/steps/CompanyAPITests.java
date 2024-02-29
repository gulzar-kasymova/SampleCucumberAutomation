package steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.Assert;
import utilities.ConfigReader;
import utilities.ElarAPIUtils;

import java.util.Map;
import java.util.Random;

import static io.restassured.RestAssured.given;

public class CompanyAPITests {

    Response response;
    Map<String,Object> dataMap;
    Map<String,Object> updatedDataMap;
    String yardId;
    Integer randomMC = new Random().nextInt(666666);
    Integer randomDOT = new Random().nextInt(999999999);

    @Given("user creates company with POST api call with data")
    public void user_creates_company_with_post_api_call_with_data(io.cucumber.datatable.DataTable dataTable) {
        dataMap = dataTable.asMap(String.class, Object.class);
        response = given().baseUri(ConfigReader.getProperty("ElarAppAPIBaseUri"))
                .and().accept(ContentType.JSON)
                .and().contentType(ContentType.JSON)
                .and().header("Authorization","Token 50c79942251edf8c7fd98c8036480b4f80b84c1d")
                .and().body("{\n" +
                        "    \"company_name\": \""+dataMap.get("Name")+"\",\n" +
                        "    \"company_type\": \"broker company\",\n" +
                        "    \"status\": \"active\",\n" +
                        "    \"mc_number\": \""+randomMC.toString()+"\",\n" +
                        "    \"dot_number\": \""+randomDOT.toString()+"\",\n" +
                        "    \n" +
                        "    \"address\": \""+dataMap.get("Street")+"\",\n" +
                        "    \"city\": \""+dataMap.get("City")+"\",\n" +
                        "    \"state\": \""+dataMap.get("State")+"\",\n" +
                        "    \"zip_code\": \""+dataMap.get("Zip code")+"\",\n" +
                        "    \"insurance\": \"1234567890\",\n" +
                        "    \"policy_expiration\": \"02/22/2024\",\n" +
                        "  \"policy_number\": \"12345678901\",\n" +
                        " \"contacts\": [\n" +
                        " {\n" +
                        "\"phone\": \"123-456-7890\",\n" +
                        "        \"email\": \"qwerty@qwerty.com\"\n" +
                        "             } ],\n" +
                        "    \"phone_number\": [\n" +
                        "        {\n" +
                        "            \"phone\": \"123-456-7890\",\n" +
                        "            \"email\": \"qwerty@qwerty.com\"\n" +
                        "      \n" +
                        "        }\n" +
                        "    ],\n" +
                        " \n" +
                        "    \"email_number\": [\n" +
                        "        {\n" +
                        "            \"email\": \"qwerty@qwerty.com\"\n" +
                        "        }\n" +
                        "    ]\n" +
                        "}")
                .when().post("/companies/");

        response.then().statusCode(201);
        yardId = response.body().jsonPath().getString("id");
        System.out.println(yardId);
    }

    @When("user validates company is created with GET API call")
    public void user_validates_company_is_created_with_get_api_call() {

        response = given().baseUri(ConfigReader.getProperty("ElarAppAPIBaseUri"))
                .and().accept(ContentType.JSON)
                .and().header("Authorization","Token 50c79942251edf8c7fd98c8036480b4f80b84c1d")
                .when().get("/companies/"+yardId+"/");

        response.then().statusCode(200);

    }

    @When("user validates get call response having company details")
    public void user_validates_get_call_response_having_company_details() {
        Assert.assertEquals(dataMap.get("Name"), response.body().jsonPath().getString("company_name"));

    }

    @Then("updates created company with PATCH api call with data")
    public void updates_created_company_with_patch_api_call_with_data(io.cucumber.datatable.DataTable dataTable) {
        dataMap = dataTable.asMap(String.class, Object.class);
        System.out.println(yardId);
        response = given().baseUri(ConfigReader.getProperty("ElarAppAPIBaseUri"))
                        .and().contentType(ContentType.JSON)
                        .and().accept(ContentType.JSON)
                        .and().header("Authorization","Token 50c79942251edf8c7fd98c8036480b4f80b84c1d")
                        .and().body("{\n" +
                        "  \"company_name\": \""+dataMap.get("Name")+"\",\n" +
                        "  \"address\": \"123 Washington ave.\"\n" +
                        "}")
                        .and().log().all()
                        .when().patch("/companies/"+yardId+"/");

        response.then().log().all();
        response.then().statusCode(200);
    }

}
