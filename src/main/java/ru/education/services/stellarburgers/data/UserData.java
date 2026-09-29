package ru.education.services.stellarburgers.data;

import com.github.javafaker.Faker;

public class UserData {
    static Faker user = new Faker();

    public static final String EMAIL = user.internet().emailAddress();
    public static final String PASSWORD = user.regexify("[0-9]{7}");
    public static final String USER_FIRSTNAME = user.name().firstName();
    public static final String USER_CREATE_PATH = "/api/auth/register";
    public static final String USER_DELETE_PATH = "/api/auth/user";
    public static final String USER_LOGIN_PATH = "/api/auth/login";
}
