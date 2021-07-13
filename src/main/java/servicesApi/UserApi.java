package servicesApi;

import dto.UserOtus;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class UserApi {
    private static final String BASE_URI = "https://petstore.swagger.io/v2/";
    private static final String USER = "/user";
    private RequestSpecification spec;

    public UserApi() {
        spec = given()
                .baseUri(BASE_URI)
                .contentType(ContentType.JSON);
    }

    public Response createUser(UserOtus userOtus) {
        return
                given(spec)
                        .with()
                        .body(userOtus)
                        .log().all()
                        .when()
                        .post(USER);
    }

    public Response getUserByName(String username) {
        return
                given(spec)
                .with()
                .log().all()
                .when()
                .get(USER + "/" + username);
    }
}
