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
        // Add an entry at startup for troubleshooting purposes
        return strings -> {
            recipeRepository.save(new Recipe(
                    "0",
                    "Peanut butter fingers",
                    "vegan",
                    1,
                    "jar of peanut butter, fingers",
                    "Insert finger in jar and mouth sequentially. Optionally watch your favorite Friends episode."));
        };
    }
}
