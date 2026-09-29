package ru.education.services.stellarburgers.steps;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import ru.education.services.stellarburgers.data.IngredientType;
import ru.education.services.stellarburgers.model.OrderModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static ru.education.services.stellarburgers.data.OrderData.GET_INGREDIENTS_PATH;
import static ru.education.services.stellarburgers.data.OrderData.ORDER_CREATE_PATH;
import static io.restassured.RestAssured.given;

public class OrderSteps {

    @Step("Create order")
    public static Response orderCreate(OrderModel order, String token) {
        var request = given()
                .log().all()
                .contentType(ContentType.JSON);
        if (token != null) request.auth().oauth2(token);
        return request
                .body(order)
                .when()
                .post(ORDER_CREATE_PATH)
                .then()
                .extract().response();
    }

    @Step("Get random ingredient")
    public static String getRandomIngredient(IngredientType type) {
        Random random = new Random();
        Response getIngredientsResponse = given()
                .contentType(ContentType.JSON)
                .get(GET_INGREDIENTS_PATH)
                .then()
                .extract().response();

        List<String> ingredientsList = new ArrayList<>(getIngredientsResponse.path("data.findAll{it.type == '"+type.toString().toLowerCase()+"'}._id"));
        if (!ingredientsList.isEmpty())
            return ingredientsList.get(random.nextInt(ingredientsList.size()));
        else
            return null;
    }

    @Step("Create list of ingredients")
    public static List<String > createIngredientsList() {
        List<String> ingredients = new ArrayList<>();
        for (IngredientType type: IngredientType.values()) {
            String id = getRandomIngredient(type);
            if (id != null) ingredients.add(id);
        }
        return ingredients;
    }

}
