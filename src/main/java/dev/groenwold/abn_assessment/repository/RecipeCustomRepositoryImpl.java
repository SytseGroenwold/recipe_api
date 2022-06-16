package dev.groenwold.abn_assessment.repository;

import dev.groenwold.abn_assessment.controller.RecipeController;
import dev.groenwold.abn_assessment.model.Recipe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;


import java.util.ArrayList;
import java.util.List;

@Repository
public class RecipeCustomRepositoryImpl implements RecipeCustomRepository{

    private static final Logger LOGGER = LoggerFactory.getLogger(RecipeCustomRepositoryImpl.class);
    @Autowired
    MongoTemplate mongoTemplate;

    public List<Recipe> getRecipesWithCondition(Integer id, String name, String diet, Integer servings, List<String> ingredients, List<String> instructions, Pageable page){
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
            ingredients.forEach(ingredient -> criteria.add(Criteria.where("ingredients").is(ingredient)));
        }
        if (instructions != null){
            instructions.forEach(instruction -> criteria.add(Criteria.where("instructions").is(instruction)));
        }
        if (!criteria.isEmpty()){
            criteria.forEach(query::addCriteria);
//            query.addCriteria(new Criteria().andOperator(criteria.toArray(new Criteria[criteria.size()])));
        }
        return mongoTemplate.find(query, Recipe.class);
    }
}
