package utilities;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.util.Map;

import static io.restassured.RestAssured.given;

public class ElarAPIUtils {

    /*
    .getCall(String endpoint); -> returns response
    .postCall(String endpoint, Object body) -> returns response;
    .patchCall(String endpoint, Object body) -> returns response;
     */

    public static Response getCall(String endPoint){
        Response response = given().baseUri(ConfigReader.getProperty("ElarAppAPIBaseUri"))
                .and().accept(ContentType.JSON)
                .and().header("Authorization", "Token 50c79942251edf8c7fd98c8036480b4f80b84c1d")
                .and().log().all()
                .when().get(endPoint);
        response.then().log().all();
        return response;
    }

    public static Response getCall(String endPoint, Map<String,Object> queryParams){
        Response response = given().baseUri(ConfigReader.getProperty("ElarAppAPIBaseUri"))
                .and().accept(ContentType.JSON)
                .and().queryParams(queryParams)
                .and().header("Authorization", "Token 50c79942251edf8c7fd98c8036480b4f80b84c1d")
                .and().log().all()
                .when().get(endPoint);
        response.then().log().all();
        return response;
    }

    public static Response postCall(String endPoint, Object body){
        Response response = given().baseUri(ConfigReader.getProperty("ElarAppAPIBaseUri"))
                .and().accept(ContentType.JSON)
                .and().contentType(ContentType.JSON)
                .and().header("Authorization", "Token 50c79942251edf8c7fd98c8036480b4f80b84c1d")
                .and().body(body)
                .and().log().all()
                .when().post(endPoint);
        response.then().log().all();
        return response;
    }

    public static Response patchCall(String endPoint, Object body){
        Response response = given().baseUri(ConfigReader.getProperty("ElarAppAPIBaseUri"))
                .and().accept(ContentType.JSON)
                .and().header("Authorization", "Token 50c79942251edf8c7fd98c8036480b4f80b84c1d")
                .and().body(body)
                .and().log().all()
                .when().patch(endPoint);
        response.then().log().all();
        return response;
    }


}
