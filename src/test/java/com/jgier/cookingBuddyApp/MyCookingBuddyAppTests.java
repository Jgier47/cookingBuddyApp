package com.jgier.cookingBuddyApp;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jgier.cookingBuddyApp.components.schemas.Recipe;
import com.jgier.cookingBuddyApp.controllers.RecipeController;
import com.jgier.cookingBuddyApp.dao.RecipeRepository;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(RecipeController.class)
public class MyCookingBuddyAppTests {

    private final Recipe testRecipe = new Recipe("Recipe_Test_Name", "Recipe Test Instructions", "recipeAuthor@test.pl");

    @MockBean
    private RecipeRepository recipeRepository;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Before
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    /**
     * Method under test: {@link RecipeController#showAllRecipes()}
     * Get complete list of recipes.
     */
    public void getRecipeList_200() throws Exception {
        String uri = "/recipes";
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);

    }

    @Test
    /**
     * Method under test: {@link RecipeController#saveRecipe(Recipe)}
     * Create recipe
     */
    public void createRecipe_201() throws Exception {
        String uri = "/recipes";
        String inputJson = mapToJson(testRecipe);
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(201, status);
    }

    @Test
    /**
     * Method under test: {@link RecipeController#getTheRecipeByName(String)}
     * Get single Recipe with code 404
     */
    public void getSingleRecipe_404() throws Exception {
        String uri = "/recipes/not_existing_recipe_test";
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(404, status);

    }

    @Test
    /**
     * Method under test: {@link RecipeController#getTheRecipeByName(String)}
     *   * Get single Recipe with code 200
     */
    public void getSingleRecipe_200() throws Exception {
        String uri = "/recipes/Recipe_Test_Name";
        when(recipeRepository.findByName("Recipe_Test_Name")).thenReturn(testRecipe);
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
    }

    @Test
    /**
     * Method under test: {@link RecipeController#updateRecipe(Recipe, String)}
     *   * Update single recipe Name field with code 204
     */
    public void updateRecipeName_204() throws Exception {
        Recipe testRecipeUpdated = testRecipe;
        testRecipeUpdated.setName("Recipe_Test_Name_Updated");

        when(recipeRepository.findByName("Recipe_Test_Name")).thenReturn(testRecipe);
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.put("/recipes/Recipe_Test_Name")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(mapToJson(testRecipeUpdated))).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(204, status);
    }

    @Test
    /**
     * Method under test: {@link RecipeController#updateRecipe(Recipe, String)}
     *   * Update single recipe Instruction field with code 204
     */
    public void updateRecipeInstruction_204() throws Exception {
        Recipe testRecipeUpdated = testRecipe;
        testRecipeUpdated.setInstructions("Recipe_Instructions_Updated");

        when(recipeRepository.findByName("Recipe_Test_Name")).thenReturn(testRecipe);
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.put("/recipes/Recipe_Test_Name")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(mapToJson(testRecipeUpdated))).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(204, status);
    }

    @Test
    /**
     * Method under test: {@link RecipeController#updateRecipe(Recipe, String)}
     *   * Update single recipe Author field with code 204
     */
    public void updateRecipeAuthor_204() throws Exception {
        Recipe testRecipeUpdated = testRecipe;
        testRecipeUpdated.setAuthor("newRecipeAuthorUpdated@gmail.com");

        when(recipeRepository.findByName("Recipe_Test_Name")).thenReturn(testRecipe);
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.put("/recipes/Recipe_Test_Name")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(mapToJson(testRecipeUpdated))).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(204, status);
    }

    @Test
    /**
     * Method under test: {@link RecipeController#deleteRecipe(String)}
     *   * Delete single recipe with code 204
     */
    public void deleteRecipe_204() throws Exception {
        when(recipeRepository.findByName("Recipe_Test_Name")).thenReturn(testRecipe);
        String uri = "/recipes/Recipe_Test_Name";
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.delete(uri)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(204, status);
    }


    protected String mapToJson(Object obj) throws JsonProcessingException {
        com.fasterxml.jackson.databind.ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(obj);
    }
}



