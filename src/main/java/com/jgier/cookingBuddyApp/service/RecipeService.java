package com.jgier.cookingBuddyApp.service;

import com.jgier.cookingBuddyApp.components.schemas.Recipe;

import java.util.List;

public interface RecipeService {

    List<Recipe> findAll();

    Recipe createRecipe(Recipe recipe);

    Recipe findByName(String name);

    Recipe updateRecipe(String recipeName, Recipe recipe);

    void deleteRecipe(String recipeName);
}