package dev.groenwold.abn_assessment.repository;

import dev.groenwold.abn_assessment.model.Recipe;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface RecipeCustomRepository {
    public List<Recipe> getRecipesWithCondition(Integer id, String title, String diet, Integer servings, List<String> ingredients, List<String> instructions, Pageable page);
}
