package dev.groenwold.abn_assessment.repository;

import dev.groenwold.abn_assessment.model.Recipe;
import dev.groenwold.abn_assessment.model.SearchConditions;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface RecipeCustomRepository {
    public List<Recipe> getRecipesWithConditionTwo(SearchConditions searchConditions, Pageable page);
}
