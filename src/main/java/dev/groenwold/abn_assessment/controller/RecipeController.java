package dev.groenwold.abn_assessment.controller;

import dev.groenwold.abn_assessment.model.Recipe;
import dev.groenwold.abn_assessment.model.SearchConditions;
import dev.groenwold.abn_assessment.service.RecipeServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class RecipeController {
    private static final Logger LOGGER = LoggerFactory.getLogger(RecipeController.class);

    @Autowired
    RecipeServiceImpl recipeServiceImpl;

    @GetMapping("/recipes")
    public List<Recipe> getRecipes(){
        LOGGER.info("RecipeController: Get all recipes.");
        return recipeServiceImpl.findAll();
    }

    @GetMapping("/recipes/search")
    public ResponseEntity<?> getRecipeByProperties(@RequestBody() SearchConditions searchConditions,
                                                   @RequestParam(required = false) Integer page){
        return ResponseEntity.ok().body(recipeServiceImpl.getRecipesWithCondition(searchConditions, page));
    }


    @PostMapping(value = "/recipes/add")
    public ResponseEntity<?> addRecipe(@RequestBody Recipe recipe){
        LOGGER.info("RecipeController: Add a recipe");
        return ResponseEntity.ok().body(recipeServiceImpl.saveOrUpdateRecipe(recipe));
    }

    @PutMapping(value = "recipes/edit")
    public ResponseEntity<?> editRecipe(@RequestParam String id,
                                        @RequestBody Recipe recipe){
        return ResponseEntity.ok().body(recipeServiceImpl.updateRecipe(id, recipe));
    }

    @DeleteMapping("/recipes/delete")
    public void deleteRecipe(@RequestParam String id){
        LOGGER.info("RecipeController: Delete recipe with ID " + id);
        recipeServiceImpl.deleteRecipeById(id);
    }

}
