package dev.groenwold.abn_assessment.config;

import dev.groenwold.abn_assessment.model.Recipe;
import dev.groenwold.abn_assessment.repository.RecipeRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.util.HashMap;
import java.util.Map;

@EnableMongoRepositories(basePackageClasses = RecipeRepository.class)
@Configuration
public class MongoConfig {
    @Bean
    CommandLineRunner commandLineRunner(RecipeRepository recipeRepository){
        //TODO Remove this deleteAll(), here only for development purposes
        recipeRepository.deleteAll();
        return strings -> {
            recipeRepository.save(new Recipe(
                    "1",
                    "Popcorn",
                    "Vegetarian",
                    2,
                    "100g corn, 2tbsp oil",
                    "Heat a cast iron pan and put the oil and a few corn kernels in. When those pop, take out and put the rest of the corn in and cover. Take off heat when popping stops."));
        };
    }
}
