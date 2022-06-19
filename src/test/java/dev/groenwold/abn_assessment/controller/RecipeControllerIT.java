package dev.groenwold.abn_assessment.controller;

import dev.groenwold.abn_assessment.model.Recipe;
import dev.groenwold.abn_assessment.repository.RecipeRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureDataMongo
@AutoConfigureMockMvc
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class RecipeControllerIT {

    @Autowired
    private MockMvc mvc;
    @Autowired
    private RecipeRepository recipeRepository;

    @BeforeEach
    void setUp() {
        recipeRepository.deleteAll();
        recipeRepository.save(new Recipe(
                "0",
                "Peanut butter fingers",
                "vegan",
                1,
                "jar of peanut butter, fingers",
                "Insert finger in jar and mouth sequentially. Optionally watch your favorite Friends episode."));
        recipeRepository.save(new Recipe(
                "1",
                "Popcorn",
                "Vegetarian",
                2,
                "100g corn, 2tbsp butter",
                "Heat a cast iron pan and put the butter and a few corn kernels in. When those pop, take out and put the rest of the corn in and cover. Take off heat when popping stops."
        ));
        recipeRepository.save(new Recipe(
                "2",
                "PB&J sandwich",
                "VEGAN",
                1,
                "Peanut butter, jelly, 2 slices of bread",
                "Spread each slice with one of the spreads and assemble."
        ));
    }

    @Test
    void addRecipe() throws Exception {
        ResultActions resultAdd = mvc.perform(post("/recipes/add").contentType("application/json").content("{\"id\":\"999\",\"name\":\"test\",\"diet\":\"test\",\"servings\":1,\"ingredients\":\"test\",\"instructions\":\"test\"}"));
        resultAdd.andExpect(status().isOk());
        // Check if it appears in results
        ResultActions resultGet = mvc.perform(get("/recipes"));
        resultGet.andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(content().string("[{\"id\":\"0\",\"name\":\"Peanut butter fingers\",\"diet\":\"vegan\",\"servings\":1,\"ingredients\":\"jar of peanut butter, fingers\",\"instructions\":\"Insert finger in jar and mouth sequentially. Optionally watch your favorite Friends episode.\"},{\"id\":\"1\",\"name\":\"Popcorn\",\"diet\":\"Vegetarian\",\"servings\":2,\"ingredients\":\"100g corn, 2tbsp butter\",\"instructions\":\"Heat a cast iron pan and put the butter and a few corn kernels in. When those pop, take out and put the rest of the corn in and cover. Take off heat when popping stops.\"},{\"id\":\"2\",\"name\":\"PB&J sandwich\",\"diet\":\"VEGAN\",\"servings\":1,\"ingredients\":\"Peanut butter, jelly, 2 slices of bread\",\"instructions\":\"Spread each slice with one of the spreads and assemble.\"},{\"id\":\"999\",\"name\":\"test\",\"diet\":\"test\",\"servings\":1,\"ingredients\":\"test\",\"instructions\":\"test\"}]"));
    }

    @Test
    void getAllRecipes() throws Exception {
        ResultActions resultGet = mvc.perform(get("/recipes"));
        resultGet.andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(content().string("[{\"id\":\"0\",\"name\":\"Peanut butter fingers\",\"diet\":\"vegan\",\"servings\":1,\"ingredients\":\"jar of peanut butter, fingers\",\"instructions\":\"Insert finger in jar and mouth sequentially. Optionally watch your favorite Friends episode.\"},{\"id\":\"1\",\"name\":\"Popcorn\",\"diet\":\"Vegetarian\",\"servings\":2,\"ingredients\":\"100g corn, 2tbsp butter\",\"instructions\":\"Heat a cast iron pan and put the butter and a few corn kernels in. When those pop, take out and put the rest of the corn in and cover. Take off heat when popping stops.\"},{\"id\":\"2\",\"name\":\"PB&J sandwich\",\"diet\":\"VEGAN\",\"servings\":1,\"ingredients\":\"Peanut butter, jelly, 2 slices of bread\",\"instructions\":\"Spread each slice with one of the spreads and assemble.\"}]"));
    }

    @Test
    void getRecipeByPropertiesId() throws Exception {
        ResultActions resultGet = mvc.perform(get("/recipes/search")
                .param("page","0")
                .contentType("application/json")
                .content("{\"id\": \"0\"}"));
        resultGet.andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(content().string("[{\"id\":\"0\",\"name\":\"Peanut butter fingers\",\"diet\":\"vegan\",\"servings\":1,\"ingredients\":\"jar of peanut butter, fingers\",\"instructions\":\"Insert finger in jar and mouth sequentially. Optionally watch your favorite Friends episode.\"}]"));
    }

    @Test
    void getRecipeByPropertiesName() throws Exception {
        ResultActions resultGet = mvc.perform(get("/recipes/search")
                .param("page","0")
                .contentType("application/json")
                .content("{\"name\": \"peanut butter fingers\"}"));
        resultGet.andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(content().string("[{\"id\":\"0\",\"name\":\"Peanut butter fingers\",\"diet\":\"vegan\",\"servings\":1,\"ingredients\":\"jar of peanut butter, fingers\",\"instructions\":\"Insert finger in jar and mouth sequentially. Optionally watch your favorite Friends episode.\"}]"));
    }

    @Test
    void getRecipeByPropertiesDiet() throws Exception {
        ResultActions resultGet = mvc.perform(get("/recipes/search")
                .param("page","0")
                .contentType("application/json")
                .content("{\"diet\": \"Vegetarian\"}"));
        resultGet.andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(content().string("[{\"id\":\"1\",\"name\":\"Popcorn\",\"diet\":\"Vegetarian\",\"servings\":2,\"ingredients\":\"100g corn, 2tbsp butter\",\"instructions\":\"Heat a cast iron pan and put the butter and a few corn kernels in. When those pop, take out and put the rest of the corn in and cover. Take off heat when popping stops.\"}]"));
    }

    @Test
    void getRecipeByPropertiesServings() throws Exception {
        ResultActions resultGet = mvc.perform(get("/recipes/search")
                .param("page","0")
                .contentType("application/json")
                .content("{\"servings\": \"2\"}"));
        resultGet.andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(content().string("[{\"id\":\"1\",\"name\":\"Popcorn\",\"diet\":\"Vegetarian\",\"servings\":2,\"ingredients\":\"100g corn, 2tbsp butter\",\"instructions\":\"Heat a cast iron pan and put the butter and a few corn kernels in. When those pop, take out and put the rest of the corn in and cover. Take off heat when popping stops.\"}]"));
    }

    @Test
    void getRecipeByPropertiesIngredient() throws Exception {
        ResultActions resultGet = mvc.perform(get("/recipes/search")
                .param("page","0")
                .contentType("application/json")
                .content("{\"ingredients\": \"peanut\"}"));
        resultGet.andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(content().string("[{\"id\":\"0\",\"name\":\"Peanut butter fingers\",\"diet\":\"vegan\",\"servings\":1,\"ingredients\":\"jar of peanut butter, fingers\",\"instructions\":\"Insert finger in jar and mouth sequentially. Optionally watch your favorite Friends episode.\"},{\"id\":\"2\",\"name\":\"PB&J sandwich\",\"diet\":\"VEGAN\",\"servings\":1,\"ingredients\":\"Peanut butter, jelly, 2 slices of bread\",\"instructions\":\"Spread each slice with one of the spreads and assemble.\"}]"));
    }

    @Test
    void getRecipeByPropertiesIngredients() throws Exception {
        ResultActions resultGet = mvc.perform(get("/recipes/search")
                .param("page","0")
                .contentType("application/json")
                .content("{\"ingredients\": \"peanut, corn\"}"));
        resultGet.andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(content().string("[{\"id\":\"0\",\"name\":\"Peanut butter fingers\",\"diet\":\"vegan\",\"servings\":1,\"ingredients\":\"jar of peanut butter, fingers\",\"instructions\":\"Insert finger in jar and mouth sequentially. Optionally watch your favorite Friends episode.\"},{\"id\":\"1\",\"name\":\"Popcorn\",\"diet\":\"Vegetarian\",\"servings\":2,\"ingredients\":\"100g corn, 2tbsp butter\",\"instructions\":\"Heat a cast iron pan and put the butter and a few corn kernels in. When those pop, take out and put the rest of the corn in and cover. Take off heat when popping stops.\"},{\"id\":\"2\",\"name\":\"PB&J sandwich\",\"diet\":\"VEGAN\",\"servings\":1,\"ingredients\":\"Peanut butter, jelly, 2 slices of bread\",\"instructions\":\"Spread each slice with one of the spreads and assemble.\"}]"));
    }

    @Test
    void getRecipeByPropertiesExcludeIngredient() throws Exception {
        ResultActions resultGet = mvc.perform(get("/recipes/search")
                .param("page","0")
                .contentType("application/json")
                .content("{\"ingredients\": \"peanut\",\"notIngredients\": \"bread\"}"));
        resultGet.andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(content().string("[{\"id\":\"0\",\"name\":\"Peanut butter fingers\",\"diet\":\"vegan\",\"servings\":1,\"ingredients\":\"jar of peanut butter, fingers\",\"instructions\":\"Insert finger in jar and mouth sequentially. Optionally watch your favorite Friends episode.\"}]"));
    }

    @Test
    void getRecipeByPropertiesInstructions() throws Exception {
        ResultActions resultGet = mvc.perform(get("/recipes/search")
                .param("page","0")
                .contentType("application/json")
                .content("{\"instructions\": \"pan\"}"));
        resultGet.andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(content().string("[{\"id\":\"1\",\"name\":\"Popcorn\",\"diet\":\"Vegetarian\",\"servings\":2,\"ingredients\":\"100g corn, 2tbsp butter\",\"instructions\":\"Heat a cast iron pan and put the butter and a few corn kernels in. When those pop, take out and put the rest of the corn in and cover. Take off heat when popping stops.\"}]"));
    }

    @Test
    void editRecipe() throws Exception {
        ResultActions resultPut = mvc.perform(put("/recipes/edit")
                .param("id","0")
                .contentType("application/json")
                .content("{\"id\":\"0\",\"name\":\"Peanut butter fingers\",\"diet\":\"vegan\",\"servings\":420,\"ingredients\":\"jar of peanut butter, fingers\",\"instructions\":\"Insert finger in jar and mouth sequentially. Optionally watch your favorite Friends episode.\"}"));
        resultPut.andExpect(status().isOk());

        ResultActions resultGet = mvc.perform(get("/recipes"));
        resultGet.andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(content().string("[{\"id\":\"0\",\"name\":\"Peanut butter fingers\",\"diet\":\"vegan\",\"servings\":420,\"ingredients\":\"jar of peanut butter, fingers\",\"instructions\":\"Insert finger in jar and mouth sequentially. Optionally watch your favorite Friends episode.\"},{\"id\":\"1\",\"name\":\"Popcorn\",\"diet\":\"Vegetarian\",\"servings\":2,\"ingredients\":\"100g corn, 2tbsp butter\",\"instructions\":\"Heat a cast iron pan and put the butter and a few corn kernels in. When those pop, take out and put the rest of the corn in and cover. Take off heat when popping stops.\"},{\"id\":\"2\",\"name\":\"PB&J sandwich\",\"diet\":\"VEGAN\",\"servings\":1,\"ingredients\":\"Peanut butter, jelly, 2 slices of bread\",\"instructions\":\"Spread each slice with one of the spreads and assemble.\"}]"));

    }

    @Test
    void deleteRecipe() throws Exception {
        ResultActions resultDelete = mvc.perform(delete("/recipes/delete").param("id","2"));
        resultDelete.andExpect(status().isOk());

        ResultActions resultGet = mvc.perform(get("/recipes"));
        resultGet.andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(content().string("[{\"id\":\"0\",\"name\":\"Peanut butter fingers\",\"diet\":\"vegan\",\"servings\":1,\"ingredients\":\"jar of peanut butter, fingers\",\"instructions\":\"Insert finger in jar and mouth sequentially. Optionally watch your favorite Friends episode.\"},{\"id\":\"1\",\"name\":\"Popcorn\",\"diet\":\"Vegetarian\",\"servings\":2,\"ingredients\":\"100g corn, 2tbsp butter\",\"instructions\":\"Heat a cast iron pan and put the butter and a few corn kernels in. When those pop, take out and put the rest of the corn in and cover. Take off heat when popping stops.\"}]"));
    }
}