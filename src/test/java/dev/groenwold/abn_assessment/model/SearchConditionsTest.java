package dev.groenwold.abn_assessment.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SearchConditionsTest {

    private SearchConditions searchConditions;
    private SearchConditions searchConditionsDoubles;

    @BeforeEach
    void setUp() {
        searchConditions = new SearchConditions(
                "1",
                "Tea",
                "vegan",
                1,
                "tea bag",
                "bag",
                "put tea bag inside boiling water and remove after 3 minutes."
        );
        searchConditionsDoubles = new SearchConditions(
                "1",
                "Tea, cup of tea",
                "vegan",
                1,
                "tea, tea bag",
                "bag, coffee",
                "put tea bag inside boiling water, remove after 3 minutes."
        );
    }

    // Setters and getters not tested, see RecipeTest for explanation why.

    @Test
    void getNonEmptyFields() {
        // Test with empty object
        SearchConditions emptySearchConditions = new SearchConditions();
        List<String> allEmptyFields = emptySearchConditions.getNonEmptyFields();
        List<String> emptyList = new ArrayList<>();
        assertEquals(allEmptyFields, emptyList);

        // Test with full object
        List<String> noEmptyFields = searchConditions.getNonEmptyFields();
        List<String> fullList = List.of("id", "name", "diet", "servings", "ingredients", "notIngredients", "instructions");
        assertEquals(noEmptyFields, fullList);

        // Test between full and empty object
        SearchConditions halfFullSearchConditions = new SearchConditions();
        halfFullSearchConditions.setIngredients("test");
        halfFullSearchConditions.setName("anotherTest");
        halfFullSearchConditions.setInstructions("lastTest");
        List<String> betweenAllandNoFields = halfFullSearchConditions.getNonEmptyFields();
        List<String> someFieldsList = List.of("name", "ingredients", "instructions");
        assertEquals(betweenAllandNoFields, someFieldsList);
    }

    @Test
    void getNameList() {
        List<String> singleName = List.of("Tea");
        List<String> doubleName = List.of("Tea, cup of tea");
        assertEquals(searchConditions.getNameList(), singleName);
        assertEquals(searchConditionsDoubles.getNameList(), doubleName);
    }

    @Test
    void getIngredientsList() {
        List<String> singleIngredients = List.of("bag");
        List<String> doubleIngredients = List.of("bag, coffee");
        assertEquals(searchConditions.getIngredientsList(), singleIngredients);
        assertEquals(searchConditionsDoubles.getIngredientsList(), doubleIngredients);
    }

    @Test
    void getNotIngredientsList() {
        List<String> singleNotIngredients = List.of("tea");
        List<String> doubleNotIngredients = List.of("tea, tea bag");
        assertEquals(searchConditions.getNotIngredientsList(), singleNotIngredients);
        assertEquals(searchConditionsDoubles.getNotIngredientsList(), doubleNotIngredients);
    }

    @Test
    void getInstructionsList() {
        List<String> singleInstructions = List.of("put tea bag inside boiling water and remove after 3 minutes.");
        List<String> doubleInstructions = List.of("put tea bag inside boiling water, remove after 3 minutes.");
        assertEquals(searchConditions.getInstructionsList(), singleInstructions);
        assertEquals(searchConditionsDoubles.getInstructionsList(), doubleInstructions);
    }
}