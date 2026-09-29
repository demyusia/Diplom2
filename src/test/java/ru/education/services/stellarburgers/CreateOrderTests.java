package ru.education.services.stellarburgers;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static ru.education.services.stellarburgers.data.OrderData.WRONG_INGREDIENT;
import static org.apache.http.HttpStatus.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.hasSize;
import static ru.education.services.stellarburgers.steps.OrderSteps.orderCreate;

public class CreateOrderTests extends BaseApiTest{

    @Test
    @DisplayName("Check create order with correct ingredients and authorization")
    @Description("You can create order with correct ingredients and authorization. Status code 200 Ok")
    public void checkCreateOrderWithCorrectIngredientsAndAccessToken() {
        orderCreate(order, accessToken)
                .then()
                .log().all()
                .statusCode(SC_OK)
                .body("success", equalTo(true))
                .body("name", notNullValue())
                .body("order.ingredients", hasSize(order.getIngredients().size()))
                .body("order.owner.name", equalTo(user.getName()))
                .body("order.owner.email", equalTo(user.getEmail()))
                .body("order.number", notNullValue())
                .body("order.price", notNullValue());
    }

    @Test
    @DisplayName("Check create order without authorization")
    @Description("You can't create order without authorization. Status code 403 forbidden")
    public void checkCreateOrderWithoutAuthorization() {
        orderCreate(order, null)
                .then()
                .log().all()
                .statusCode(SC_FORBIDDEN)
                .body("success", equalTo(false));
    }

    @Test
    @DisplayName("Check create order without ingredients but with authorization")
    @Description("You can't create order without ingredients. Status code 400 Bad request")
    public void checkCreateOrderWithoutIngredients() {
        order.setIngredients(new ArrayList<>());
        orderCreate(order, accessToken)
                .then()
                .log().all()
                .statusCode(SC_BAD_REQUEST)
                .body("success", equalTo(false))
                .body("message", equalTo("Ingredient ids must be provided"));

    }

    @Test
    @DisplayName("Check create order with wrong ingredient hash and with authorization")
    @Description("You can't create order wrong ingredient hash. Status code 400 Bad request")
    public void checkCreateOrderWithWrongIngredientHash() {
        order.setIngredients(Arrays.asList(WRONG_INGREDIENT));
        orderCreate(order, accessToken)
                .then()
                .log().all()
                .statusCode(SC_BAD_REQUEST)
                .body("success", equalTo(false))
                .body("message", equalTo("Ingredient ids must be provided"));

    }
}
