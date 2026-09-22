package ru.education.services.stellarburgers;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Test;

import static org.apache.http.HttpStatus.SC_OK;
import static org.apache.http.HttpStatus.SC_UNAUTHORIZED;
import static org.hamcrest.CoreMatchers.*;
import static ru.education.services.stellarburgers.steps.UserSteps.*;

public class LoginUserTests extends BaseApiTest{

    @Test
    @DisplayName("Login user with correct ru.education.services.stellarburgers.data")
    @Description("You can login user with correct email & password. Status code 200 Ok")
    public void loginUserWithCorrectData() {
        loginUser(user)
                .then()
                .statusCode(SC_OK)
                .body("success", equalTo(true))
                .body("user.email", equalTo(user.getEmail()))
                .body("user.name", equalTo(user.getName()))
                .body("accessToken", containsString("Bearer"))
                .body("refreshToken", notNullValue());
    }

    @Test
    @DisplayName("Login user with wrong email")
    @Description("You can't login user with wrong email. Status code 401 Unauthorized")
    public void loginUserWithWrongEmail() {
        user.setEmail("some_email@gmail.com");
        loginUser(user)
                .then()
                .statusCode(SC_UNAUTHORIZED)
                .body("success", equalTo(false))
                .body("message", equalTo("email or password are incorrect"));
    }

    @Test
    @DisplayName("Login user with wrong password")
    @Description("You can't login user with wrong password. Status code 401 Unauthorized")
    public void loginUserWithWrongPassword() {
        user.setPassword("654321");
        loginUser(user)
                .then()
                .statusCode(SC_UNAUTHORIZED)
                .body("success", equalTo(false))
                .body("message", equalTo("email or password are incorrect"));
    }
}
