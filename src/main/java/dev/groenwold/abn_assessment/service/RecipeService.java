package dev.groenwold.abn_assessment.service;

import dev.groenwold.abn_assessment.model.Recipe;

import java.util.List;

public interface RecipeService {

    List<Recipe> findAll();

    Recipe saveOrUpdateRecipe(Recipe recipe);

    List<Recipe> getRecipeByProperties(Integer id, String title, String diet, Integer servings, List<String> ingredients, List<String> instructions, Integer page);

    void deleteRecipeById(String id);
}
