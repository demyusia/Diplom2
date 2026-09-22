package ru.education.services.stellarburgers.steps;

import com.google.gson.JsonObject;
import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import ru.education.services.stellarburgers.model.UserModel;

import static io.restassured.RestAssured.given;
import static ru.education.services.stellarburgers.data.UserData.*;

public class UserSteps {

    @Step("Create user")
    public static Response createUser(UserModel user) {
        return given()
                .log().all()
                .contentType(ContentType.JSON)
                .body(user)
                .when()
                .post(USER_CREATE_PATH)
                .then()
                .extract().response();
    }

    @Step("Get accessToken")
    public static String getUserAccessToken(Response response) {
        String accessToken;
        try {
            accessToken = response.path("accessToken").toString().replace("Bearer ", "").trim();
        } catch (RuntimeException e) {
            accessToken = null;
        }
        return accessToken;
    }

    @Step("Login user")
    public static Response loginUser(UserModel user) {
        JsonObject json = new JsonObject();
        json.addProperty("email", user.getEmail());
        json.addProperty("password", user.getPassword());
        return given()
                .contentType(ContentType.JSON)
                .body(json)
                .when()
                .post(USER_LOGIN_PATH)
                .then()
                .extract().response();
    }

    @Step("Delete user")
    public static Response deleteUser(String token) {
        return given()
                .log().all()
                .contentType(ContentType.JSON)
                .auth().oauth2(token)
                .when()
                .delete(USER_DELETE_PATH)
                .then()
                .extract().response();
    }
}
