package ru.education.services.stellarburgers;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Test;

import static org.apache.http.HttpStatus.SC_FORBIDDEN;
import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.CoreMatchers.*;
import static ru.education.services.stellarburgers.steps.UserSteps.createUser;

public class CreateUserTests extends BaseApiTest{

    @Test
    @DisplayName("Create unique user with correct ru.education.services.stellarburgers.data")
    @Description("You can create user with correct ru.education.services.stellarburgers.data. Status code 200, 'success'=true, body contains accessToken & refreshToken")
    public void createUserWithCorrectData() {
        createUserResponse
                .then()
                .log().all()
                .statusCode(SC_OK)
                .body("success", equalTo(true))
                .body("user.email", equalTo(user.getEmail()))
                .body("user.name", equalTo(user.getName()))
                .body("accessToken", containsString("Bearer"))
                .body("refreshToken", notNullValue());
    }

    @Test
    @DisplayName("Create user with ununique email")
    @Description("You can't create user with ununique email. Status code 403, 'success' = false")
    public void createUserWithUnuniqueEmail() {
        createUser(user)
                .then()
                .statusCode(SC_FORBIDDEN)
                .body("success", equalTo(false))
                .body("message", equalTo("User already exists"));
    }
}
