package dev.groenwold.abn_assessment.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RecipeTest {

    private Recipe recipe;

    @BeforeEach
    void setUp() {
        recipe = new Recipe(
                "1",
                "Tea",
                "vegan",
                1,
                "tea bag",
                "put tea bag inside boiling water, remove after 3 minutes.");
    }

    @Test
    void testEquals() {
        Recipe recipeDuplicate = new Recipe(
                "1",
                "Tea",
                "vegan",
                1,
                "tea bag",
                "put tea bag inside boiling water, remove after 3 minutes.");
        // Ensuring objects are not equal
        assertNotEquals(recipe, recipeDuplicate);
        assertTrue(recipe.equals(recipeDuplicate) );
    }

    // To Create unit tests for getters/setters is a contentious topic with no resounding majority agreeing on yes/no.
    // In the design principles of Test Driven Development, however, it should be done: design first, implement second.
    // In your design you do not yet know whether your implementation getters/setters hold any logic, therefore they must be created.
    // In this project, due to the scope and constraints, only two fields are getting their getters and setters tested.
    // In production code, this will always be implemented, because logical change to them will cause the test to fail,
    // pointing out potential issues down the road.
    @Test
    void getId() {
        assertEquals(this.recipe.getId(), "1");
    }

    @Test
    void getName() {
        assertEquals(this.recipe.getName(), "Tea");
    }

    @Test
    void setId() {
        this.recipe.setId("2");
        assertEquals(this.recipe.getId(), "2");
    }

    @Test
    void setName() {
        this.recipe.setName("Cup of Tea");
        assertEquals(this.recipe.getName(), "Cup of Tea");
    }
}