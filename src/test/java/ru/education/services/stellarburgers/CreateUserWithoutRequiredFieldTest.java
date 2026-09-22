package ru.education.services.stellarburgers;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static ru.education.services.stellarburgers.data.UserData.*;
import static org.apache.http.HttpStatus.SC_FORBIDDEN;
import static org.hamcrest.CoreMatchers.equalTo;
import static ru.education.services.stellarburgers.steps.UserSteps.createUser;

@RunWith(Parameterized.class)
public class CreateUserWithoutRequiredFieldTest extends BaseApiTest{

    @Parameterized.Parameter(0)
    public String email;
    @Parameterized.Parameter(1)
    public String password;
    @Parameterized.Parameter(2)
    public String name;

    @Parameterized.Parameters(name = "Тестовые данные: email={0}, password={1}, name={2}")
    public static Object[][] getData() {
        return new Object[][]{
                {null, PASSWORD, USER_FIRSTNAME},
                {EMAIL, null, USER_FIRSTNAME},
                {EMAIL, PASSWORD, null},
        };
    }

    @Override
    protected void updateUserData() {
        user.setName(name);
        user.setPassword(password);
        user.setEmail(email);
    }

    @Test
    @DisplayName("Create user without required field")
    @Description("You can't create user without required field. Status code 403")
    public void createUserWithoutRequiredField() {
        createUser(user)
                .then()
                .statusCode(SC_FORBIDDEN)
                .body("message", equalTo("Email, password and name are required fields"));
    }
}
