package dev.groenwold.abn_assessment.repository;

import com.mongodb.client.model.changestream.UpdateDescription;
import dev.groenwold.abn_assessment.model.Recipe;
import dev.groenwold.abn_assessment.model.SearchConditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.ReplaceRootOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.UpdateDefinition;
import org.springframework.stereotype.Repository;


import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

@Repository
public class RecipeCustomRepositoryImpl implements RecipeCustomRepository{

    private static final Logger LOGGER = LoggerFactory.getLogger(RecipeCustomRepositoryImpl.class);

    @Autowired
    // The MongoTemplate allows to easily constructed
    MongoTemplate mongoTemplate;

    // Find and replace a recipe by their ID
    // This should instead use the findAndUpdate() method, which was not implemented due to time constraints.
    public List<Recipe> findAndUpdate(String id, Recipe recipe){
        Recipe result = mongoTemplate.findAndReplace(new Query().addCriteria(Criteria.where("id").is(id)), recipe);
        return List.of(result, recipe);
    }

    // The function that handles turning the given conditions into a MongoDB query.
    public List<Recipe> getRecipesWithConditionTwo(SearchConditions searchConditions, Pageable page) {
        LOGGER.info("New condition search.");
        final Query query = new Query().with(page);
        final Query excludeQuery = new Query();
        final List<Criteria> criteria = new ArrayList<>();
        final List<Criteria> excludeCriteria = new ArrayList<>();

        // Retrieve all supplied keys in the searchCondtions to iterate over them and create the individual criteria.
        List<String> nonEmptyFields = searchConditions.getNonEmptyFields();
        nonEmptyFields.forEach((field) -> {
            StringJoiner combinedRegex;
            // The switch can be shortened to only two options: a .regex() and .is() operator by using .getDeclaredFields().
            // This does require extensive exception handling being defined for the Field reflects, and a CodecRegistry
            // to turn the Binary Json objects to Proper Ordinary Java Objects, for which there was no time.
            switch (field) {
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
                    // While this combined regex approach functions, the better approach of individual criteria  is not
                    // possible to implement through the MongoTemplate, which only supports BasicDocuments with 1 criterium.
                    combinedRegex = new StringJoiner("|");
                    searchConditions.getNameList().forEach(combinedRegex::add);
                    criteria.add(Criteria.where(field).regex(combinedRegex.toString(), "i"));
                    break;
                case "ingredients":
                    combinedRegex = new StringJoiner("|");
                    searchConditions.getIngredientsList().forEach(combinedRegex::add);
                    criteria.add(Criteria.where(field).regex(combinedRegex.toString(), "i"));
                    break;
                case "instructions":
                    combinedRegex = new StringJoiner("|");
                    searchConditions.getInstructionsList().forEach(combinedRegex::add);
                    criteria.add(Criteria.where(field).regex(combinedRegex.toString(), "i"));
                    break;
                case "notIngredients":
                    combinedRegex = new StringJoiner("|");
                    searchConditions.getNotIngredientsList().forEach(combinedRegex::add);
                    excludeCriteria.add(Criteria.where("ingredients").regex(combinedRegex.toString(), "i"));
                    break;
            }
        });

        // Construct the query and execute it
        if (!criteria.isEmpty()) {
            criteria.forEach(query::addCriteria);
        }
        List<Recipe> result = mongoTemplate.find(query, Recipe.class);

        // The following bloc of code is due to a bug in MongoDB (SERVER-18259) regarding negative look-arounds:
        // Just like the regex can find matches based on strings, it can also match all that do NOT contain a string.
        // This is resolved by implementing the Java driver, but I prefer to showcase the Spring Data MongoDB implementation.
        if (!excludeCriteria.isEmpty()) {
            excludeCriteria.forEach(excludeQuery::addCriteria);
            List<Recipe> excludeRecipes = mongoTemplate.find(excludeQuery, Recipe.class);
            List<Recipe> removeResults = new ArrayList<>();
            excludeRecipes.forEach((excludeRecipe -> {
                result.forEach((recipe -> {
                    if (recipe.equals(excludeRecipe)) {
                        removeResults.add(recipe);
                    }
                }));
            }));
            result.removeAll(removeResults);
        }

        // Return the found recipes
        return result;
    }
}
