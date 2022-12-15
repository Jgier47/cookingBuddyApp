package com.jgier.cookingBuddyApp.service;

import com.jgier.cookingBuddyApp.components.schemas.Recipe;
import com.jgier.cookingBuddyApp.dao.RecipeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class RecipeServiceImpl implements RecipeService {

    private final RecipeRepository recipeRepository;

    public RecipeServiceImpl(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    @Override
    public List<Recipe> findAll() {
        return recipeRepository.findAll();
    }

    @Override
    public Recipe createRecipe(Recipe recipe) {
        if (recipeRepository.findByName(recipe.getName()) != null)
            throw new IllegalArgumentException();
        return recipeRepository.save(recipe);
    }

    @Override
    public Recipe findByName(String recipeName) {
        Recipe currentRecipe = recipeRepository.findByName(recipeName);
        if (currentRecipe == null)
            throw new NoSuchElementException();
        return currentRecipe;
    }

    @Override
    public Recipe updateRecipe(String recipeNameToUpdate, Recipe updatedRecipe) {
        Recipe currentRecipe = recipeRepository.findByName(recipeNameToUpdate);
        if (currentRecipe == null)
            throw new NoSuchElementException();

        updatedRecipe.setId(currentRecipe.getId());
        return recipeRepository.save(updatedRecipe);
    }

    @Override
    public void deleteRecipe(String recipeName) {
        Recipe currentRecipe = recipeRepository.findByName(recipeName);
        if (currentRecipe == null)
            throw new NoSuchElementException();
        recipeRepository.delete(currentRecipe);
    }
}
