package ru.education.services.stellarburgers.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class OrderModel {

    private List<String> ingredients;


}
