package com.jgier.cookingBuddyApp;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jgier.cookingBuddyApp.components.schemas.Recipe;
import com.jgier.cookingBuddyApp.components.schemas.RecipeDTO;
import com.jgier.cookingBuddyApp.controllers.RecipeController;
import com.jgier.cookingBuddyApp.service.RecipeService;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(RecipeController.class)
public class IntegrationTestOfMyCookingBuddyApp {

    private final Recipe testRecipe = new Recipe(0, "Recipe_Test_Name", "Recipe Test Instructions", "recipeAuthor@test.pl");

    @MockBean
    private RecipeService recipeService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ModelMapper modelMapper;

    @Before
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void getRecipeList_200() throws Exception {
        String uri = "/recipes";
        List<RecipeDTO> emptyList = Collections.emptyList();
        mockMvc.perform(MockMvcRequestBuilders.get(uri)
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(result -> assertEquals(mapToJson(emptyList),
                        result.getResponse().getContentAsString()));
    }

    @Test
    public void createRecipe_201() throws Exception {
        String uri = "/recipes";
        RecipeDTO recipeRequestDto = modelMapper.map(testRecipe, RecipeDTO.class);
        mockMvc.perform(MockMvcRequestBuilders.post(uri)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(mapToJson(recipeRequestDto)))
                .andExpect(status().isCreated());
    }

    @Test
    public void getSingleRecipe_404() throws Exception {
        String uri = "/recipes/not_existing_recipe_test";
        mockMvc.perform(MockMvcRequestBuilders.get(uri)
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertEquals("Resource not found",
                        result.getResponse().getContentAsString()));
    }

    @Test
    public void getSingleRecipe_200() throws Exception {
        String uri = "/recipes/Recipe_Test_Name";
        RecipeDTO recipeResponse = modelMapper.map(testRecipe, RecipeDTO.class);

        when(recipeService.findByName("Recipe_Test_Name")).thenReturn(testRecipe);
        mockMvc.perform(MockMvcRequestBuilders.get(uri)
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(result -> assertEquals(mapToJson(recipeResponse),
                        result.getResponse().getContentAsString()));
    }

    @ParameterizedTest
    @ValueSource(strings = {"Recipe_Test_Name_Updated", "", "Recipe_Test_Name_UpdatedRecipe_Test_Name_UpdatedRecipe_Test_Name_UpdatedRecipe_Test_Name_UpdatedRecipe_Test_Name_UpdatedRecipe"})
    public void updateRecipeName_204(String newRecipeName) throws Exception {
        Recipe testRecipeUpdated = testRecipe;
        testRecipeUpdated.setName(newRecipeName);

        when(recipeService.findByName("Recipe_Test_Name")).thenReturn(testRecipe);
        mockMvc.perform(MockMvcRequestBuilders.put("/recipes/Recipe_Test_Name")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(mapToJson(testRecipeUpdated)))
                .andExpect(status().isNoContent());
    }

    @ParameterizedTest
    @ValueSource(strings = {"Recipe_Instructions_Updated", "", "Recipe_Instructions_UpdatedRecipe_Instructions_UpdatedRecipe_Instructions_UpdatedRecipe_Instructions_UpdatedRecipe_Instructions_UpdatedRecipe_Instructions_UpdatedRecipe_Instructions_UpdatedRecipe_Instructions_UpdatedRecipe_Instructions_UpdatedRecipe_Instructions_UpdatedRecipe_Instructions_UpdatedRecipe_Instructions_UpdatedRecipe_Instructions_UpdatedRecipe_Instructions_UpdatedRecipe_Instructions_UpdatedRecipe_Instructions_UpdatedRecipe_InstruRecipe_Instructions_UpdatedRecipe_Instructions_UpdatedRecipe_Instructions_UpdatedRecipe_Instructions_UpdatedRecipe_Instructions_UpdatedRecipe_Instructions_UpdatedRecipe_Instructions_UpdatedRecipe_Instructions_UpdatedRecipe_Instructions_UpdatedRecipe_Instructions_UpdatedRecipe_Instructions_UpdatedRecipe_Instructions_UpdatedRecipe_Instructions_UpdatedRecipe_Instructions_UpdatedRecipe_Instructions_UpdatedRecipe_Instructions_UpdatedRecipe_InstruRecipe_Instructions_UpdatedRecipe_Instructions_UpdatedRecipe_Instructions_UpdatedRecipe_Instructions_UpdatedRecipe_Instructions_Upda"})
    public void updateRecipeInstruction_204(String newInstruction) throws Exception {
        Recipe testRecipeUpdated = testRecipe;
        testRecipeUpdated.setInstructions(newInstruction);

        when(recipeService.findByName("Recipe_Test_Name")).thenReturn(testRecipe);
        mockMvc.perform(MockMvcRequestBuilders.put("/recipes/Recipe_Test_Name")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(mapToJson(testRecipeUpdated)))
                .andExpect(status().isNoContent());
    }

    @ParameterizedTest
    @ValueSource(strings = {"newRecipeAuthorUpdated@gmail.com", "@gmail.com", "@"})
    public void updateRecipeAuthor_204(String authors) throws Exception {
        Recipe testRecipeUpdated = testRecipe;
        testRecipeUpdated.setAuthor(authors);

        when(recipeService.findByName("Recipe_Test_Name")).thenReturn(testRecipe);
        mockMvc.perform(MockMvcRequestBuilders.put("/recipes/Recipe_Test_Name")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(mapToJson(testRecipeUpdated)))
                .andExpect(status().isNoContent());
    }

    @Test
    public void deleteRecipe_204() throws Exception {
        when(recipeService.findByName("Recipe_Test_Name")).thenReturn(testRecipe);
        String uri = "/recipes/Recipe_Test_Name";
        mockMvc.perform(MockMvcRequestBuilders.delete(uri))
                .andExpect(status().isNoContent());
    }

    protected String mapToJson(Object obj) throws JsonProcessingException {
        return objectMapper.writeValueAsString(obj);
    }
}




