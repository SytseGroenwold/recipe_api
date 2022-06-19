package dev.groenwold.abn_assessment.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
// @JsonIgnoreProperties allows the user not need to supply keys with empty values for fields they are not interested in.
@JsonIgnoreProperties(ignoreUnknown = true)
public class SearchConditions {
    private String id;
    private String name;
    private String diet;
    private Integer servings;
    private String ingredients;
    private String notIngredients;
    private String instructions;

    public List<String> getNonEmptyFields() {
        List<String> nonEmptyFields = new ArrayList<>();
        for (Field f : getClass().getDeclaredFields()){
            try {
                if (f.get(this) != null){
                    nonEmptyFields.add(f.getName());
                }
            } catch (IllegalAccessException e) {
                continue;
            }
        }
        return nonEmptyFields;
    }

    public List<String> getNameList() {
        return List.of(name.split(","));
    }

    public List<String> getIngredientsList(){
        return List.of(ingredients.split(","));
    }

    public List<String> getNotIngredientsList(){
        return List.of(notIngredients.split(","));
    }

    public List<String> getInstructionsList(){
        return List.of(instructions.split(","));
    }
}
