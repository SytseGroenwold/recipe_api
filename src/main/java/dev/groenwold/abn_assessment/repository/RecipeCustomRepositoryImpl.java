package dev.groenwold.abn_assessment.repository;

import dev.groenwold.abn_assessment.model.Recipe;
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
    @Autowired
    MongoTemplate mongoTemplate;

    public List<Recipe> getRecipesWithCondition(Integer id, String title, String diet, Integer servings, List<String> ingredients, List<String> instructions, Pageable page){
        final Query query = new Query().with(page);
        final List<Criteria> criteria = new ArrayList<>();
        if (id != null){
            criteria.add(Criteria.where("id").is(id));
        }
        if (title != null && !title.isEmpty()){
            criteria.add(Criteria.where("title").in(id));
        }
        if (diet != null && !diet.isEmpty()){
            criteria.add(Criteria.where("diet").is(diet));
        }
        if (servings != null){
            criteria.add(Criteria.where("servings").in(servings));
        }
        if (ingredients != null && !ingredients.isEmpty()){
            ingredients.forEach(ingredient -> criteria.add(Criteria.where("ingredients").in(ingredient)));
        }
        if (instructions != null && !instructions.isEmpty()){
            instructions.forEach(instruction -> criteria.add(Criteria.where("instructions").in(instruction)));
        }
        if (!criteria.isEmpty()){
            query.addCriteria(new Criteria().andOperator(criteria.toArray(new Criteria[criteria.size()])));
        }
        return mongoTemplate.find(query, Recipe.class);
    }
}
