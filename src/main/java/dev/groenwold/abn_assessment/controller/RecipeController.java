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

    @GetMapping("/recipe/search")
    public ResponseEntity<?> getRecipeByPropertiesTwo(@RequestBody() SearchConditions searchConditions,
                                                      @RequestParam(required = false) Integer page){
        return ResponseEntity.ok().body(recipeServiceImpl.getRecipesWithConditionTwo(searchConditions, page));
    }

    @GetMapping("/recipe")
    public ResponseEntity<?> getRecipeByProperties(@RequestParam(required = false) Integer id,
                                                   @RequestParam(required = false) String name,
                                                   @RequestParam(required = false) String diet,
                                                   @RequestParam(required = false) Integer servings,
                                                   @RequestParam(required = false) String ingredients,
                                                   @RequestParam(required = false) String notIngredients,
                                                   @RequestParam(required = false) String instructions,
                                                   @RequestParam Integer page){
        LOGGER.info("RecipeController: Get recipes based on conditions.");
        LOGGER.info(id + name + diet + servings + ingredients + instructions);
        return ResponseEntity.ok().body(recipeServiceImpl.getRecipesWithCondition(id,
                name,
                diet,
                servings,
                ingredients,
                notIngredients,
                instructions,
                page));
    }


    @PostMapping(value = "/recipe/add")
    public ResponseEntity<?> addRecipe(@RequestBody Recipe recipe){
        LOGGER.info("RecipeController: Add a recipe");
        return ResponseEntity.ok().body(recipeServiceImpl.saveOrUpdateRecipe(recipe));
    }

    @DeleteMapping("/recipe/delete/{id}")
    public void deleteRecipe(@PathVariable String id){
        LOGGER.info("RecipeController: Delete recipe with ID " + id);
        recipeServiceImpl.deleteRecipeById(id);
    }

}
