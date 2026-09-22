package ru.education.services.stellarburgers;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import ru.education.services.stellarburgers.model.OrderModel;
import ru.education.services.stellarburgers.model.UserModel;
import org.junit.After;
import org.junit.Before;

import java.util.List;

import static ru.education.services.stellarburgers.data.UserData.*;
import static ru.education.services.stellarburgers.steps.OrderSteps.createIngredientsList;
import static ru.education.services.stellarburgers.steps.UserSteps.*;

public class BaseApiTest {

    protected UserModel user;
    protected OrderModel order;
    protected String accessToken;
    protected Response createUserResponse;

    @Before
    public void setUp() {
        RestAssured.baseURI = "https://stellarburgers.education-services.ru";
        user = new UserModel(EMAIL, PASSWORD, USER_FIRSTNAME);

        updateUserData();
        createUserResponse = createUser(user);
        accessToken = getUserAccessToken(createUserResponse);

        List<String> ingredients = createIngredientsList();
        order = new OrderModel(ingredients);
    }


    protected void updateUserData() {

    }

    @After
    public void cleanUp() {
        if (accessToken != null) deleteUser(accessToken);
    }
}
