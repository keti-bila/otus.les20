import dto.UserOtus;
import dto.UserOut;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import servicesApi.UserApi;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.lessThan;

public class UserApiTest {
    private UserApi userApi = new UserApi();
    private UserOtus user = UserOtus.builder()
            .userStatus(101)
            .email("email@gmail.com")
            .id(1456)
            .firstName("UserName")
            .lastName("UserSurname")
            .password("pass101")
            .username("user101")
            .phone("1589221")
            .build();

    /**
     * Test case description: Create user and check response status
     * Test step:
     * <ol>
     *     <li>Send post request to the /user endpoint. Content of request should include:
     *     userStatus,email,id,firstName,lastName,password,username,phone</li>
     * </ol>
     * Expected Result: Response status is 200
     */
    @Test
    public void createUserStatusTest(){
        Response response = userApi.createUser(user);

        response
                .then()
                .log().all()
                .statusCode(HttpStatus.SC_OK);
    }

    /**
     * Test case description: Create user and check response data
     * Test step:
     * <ol>
     *     <li>Send post request to the /user endpoint. Content of request should include:
     *     userStatus,email,id,firstName,lastName,password,username,phone</li>
     * </ol>
     * Expected Result: Response should contain body with following data:
     * "code": 200, "type": "unknown", "message": {id}
     */
    @Test
    public void createUserCheckResponseDataTest() {
        Response response = userApi.createUser(user);

        int codeActual = response.jsonPath().getInt("code");
        String typeActual = response.jsonPath().getString("type");
        String messageActual = response.jsonPath().getString("message");

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(codeActual, 200);
        softAssert.assertEquals(typeActual, "unknown");
        softAssert.assertEquals(messageActual, "1456");
        softAssert.assertAll();
    }

    /**
     * Test case description: Get user by valid name and check response status
     * Test step:
     * <ol>
     *     <li>Send get request to the /user/{username} endpoint with existing username</li>
     * </ol>
     * Expected Result: Response status is 200
     */
    @Test
    public void getUserByValidNameTest() {
        Response response = userApi.getUserByName("user101");

        response
                .then()
                .log().all()
                .statusCode(HttpStatus.SC_OK);
    }

    /**
     * Test case description: Get user by invalid name and check response status
     * Test step:
     * <ol>
     *     <li>Send get request to the /user/{username} endpoint with non-existing username</li>
     * </ol>
     * Expected Result: Response status is 404
     */
    @Test
    public void getUserByInvalidNameTest() {
        Response response = userApi.getUserByName("jotakd");

        response
                .then()
                .log().all()
                .statusCode(HttpStatus.SC_NOT_FOUND);
    }

    /**
     * Test case description: Get user by invalid name and check response data
     * Test step:
     * <ol>
     *     <li>Send get request to the /user/{username} endpoint with non-existing username</li>
     * </ol>
     * Expected Result: Response should contain body with following data:
     * "code": 1, "type": "error", "message": "User not found"
     */
    @Test
    public void getUserErrorOutputTest() {
        Response response = userApi.getUserByName("jotakd");

        UserOut userOut = response.as(UserOut.class);
        int codeActual = userOut.getCode();
        String typeActual = userOut.getType();
        String messageActual = userOut.getMessage();

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(codeActual, 1);
        softAssert.assertEquals(typeActual, "error");
        softAssert.assertEquals(messageActual, "User not found");
        softAssert.assertAll();
    }
}
