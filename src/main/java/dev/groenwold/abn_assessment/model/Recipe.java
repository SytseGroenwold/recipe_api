package dev.groenwold.abn_assessment.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Getter
@Setter
@AllArgsConstructor
public class Recipe {
    @Id
    private String id;
    private String name;
    private String diet;
    private Integer servings;
    private String ingredients;
    private String instructions;
}
