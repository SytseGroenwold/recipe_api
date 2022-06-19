package dev.groenwold.abn_assessment.repository;

import dev.groenwold.abn_assessment.model.Recipe;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RecipeRepository extends MongoRepository<Recipe, String>, RecipeCustomRepository {
    Optional<Recipe> findById(String id);

    List<Recipe> findAll();
}
