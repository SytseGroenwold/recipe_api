package dev.groenwold.abn_assessment.service;

import dev.groenwold.abn_assessment.model.Recipe;
import dev.groenwold.abn_assessment.model.SearchConditions;
import dev.groenwold.abn_assessment.repository.RecipeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class RecipeServiceImpl {
    @Autowired
    private RecipeRepository recipeRepository;

    public List<Recipe> findAll(){
        return recipeRepository.findAll();
    }

    public Recipe saveOrUpdateRecipe(Recipe recipe){
        return recipeRepository.save(recipe);
    }

    public void deleteRecipeById(String id) {
        recipeRepository.deleteById(id);
    }

    public Object getRecipesWithConditionTwo(SearchConditions searchConditions, Integer page) {
        return recipeRepository.getRecipesWithConditionTwo(searchConditions, PageRequest.of(page, 15));
    }
}
