package dev.groenwold.abn_assessment.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
public class RecipeDTO {

        private String id;
        private String name;
        private String diet;
        private Integer servings;
        private Map<String, String> ingredients;
        private String instructions;

        public RecipeDTO(){}
}
