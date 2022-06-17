package dev.groenwold.abn_assessment.repository;

import dev.groenwold.abn_assessment.controller.RecipeController;
import dev.groenwold.abn_assessment.model.Recipe;
import dev.groenwold.abn_assessment.model.SearchConditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;


import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Repository
public class RecipeCustomRepositoryImpl implements RecipeCustomRepository{

    private static final Logger LOGGER = LoggerFactory.getLogger(RecipeCustomRepositoryImpl.class);
    @Autowired
    MongoTemplate mongoTemplate;

    public List<Recipe> getRecipesWithConditionTwo(SearchConditions searchConditions, Pageable page){
        LOGGER.info("New condition search.");
        List<String> nonEmptyFields = searchConditions.getNonEmptyFields();

        final Query query = new Query().with(page);
        final List<Criteria> criteria = new ArrayList<>();
        nonEmptyFields.forEach((field) ->{
            switch (field){
                case "id":
                    criteria.add(Criteria.where(field).is(searchConditions.getId()));
                    break;
                case "servings":
                    criteria.add(Criteria.where(field).is(searchConditions.getServings()));
                    break;
                case "diet":
                    criteria.add(Criteria.where(field).is(searchConditions.getDiet()));
                    break;
                case "name":
                    criteria.add(Criteria.where(field).regex(searchConditions.getName(), "i"));
                    break;
                case "ingredients":
                    searchConditions.getIngredientsList().forEach((ingredient) ->{
                        criteria.add(Criteria.where(field).regex(ingredient, "i"));
                    });
                    break;
                case "instructions":
                    searchConditions.getInstructionsList().forEach((instruction) ->{
                        criteria.add(Criteria.where(field).regex(instruction, "i"));
                    });
                    break;
                case "notIngredients":
                    searchConditions.getNotIngredientsList().forEach((notIngredient)->{
                        criteria.add(Criteria.where(field).regex("^(?!.*?"+notIngredient+").*", "i"));
                    });
                    break;
            }
        });
        if (!criteria.isEmpty()) {
            criteria.forEach(query::addCriteria);
        }
        return mongoTemplate.find(query, Recipe.class);
    }

    public List<Recipe> getRecipesWithCondition(Integer id,
                                                String name,
                                                String diet,
                                                Integer servings,
                                                String ingredients,
                                                String notIngredients,
                                                String instructions,
                                                Pageable page){
        LOGGER.info("RecipeCustomRepositoryImpl reached.");
        final Query query = new Query().with(page);
        final List<Criteria> criteria = new ArrayList<>();
        if (id != null){
            criteria.add(Criteria.where("id").is(id));
        }
        if (name != null){
            criteria.add(Criteria.where("name").regex(name, "i"));
        }
        if (diet != null){
            criteria.add(Criteria.where("diet").regex(diet, "i"));
        }
        if (servings != null){
            criteria.add(Criteria.where("servings").is(servings));
        }
        if (ingredients != null){
            List<String> ingredientsSplit = Arrays.asList(ingredients);
            ingredientsSplit.forEach(ingredient -> criteria.add(Criteria.where("ingredients").is(ingredient)));
        }
        if (notIngredients != null){
            List<String> notIngredientSplit = Arrays.asList(notIngredients);
            notIngredientSplit.forEach(notIngredient -> criteria.add(Criteria.where("ingredients").ne(notIngredient)));
        }
        if (instructions != null){
            List<String> instructionSplit = Arrays.asList(instructions);
            instructionSplit.forEach(instruction -> criteria.add(Criteria.where("instructions").is(instruction)));
        }
        if (!criteria.isEmpty()){
            criteria.forEach(query::addCriteria);
//            query.addCriteria(new Criteria().andOperator(criteria.toArray(new Criteria[criteria.size()])));
        }
        return mongoTemplate.find(query, Recipe.class);
    }
}
