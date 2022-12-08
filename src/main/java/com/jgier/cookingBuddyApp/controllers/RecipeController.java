package com.jgier.cookingBuddyApp.controllers;

import com.jgier.cookingBuddyApp.components.schemas.Recipe;
import com.jgier.cookingBuddyApp.dao.RecipeRepository;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.NoSuchElementException;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@ResponseStatus(value = INTERNAL_SERVER_ERROR)
@RestController
public class RecipeController {

    private final RecipeRepository recipeRepository;

    @Autowired
    public RecipeController(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    @GetMapping("/recipes")
    public Object showAllRecipes() {
        try {
            List<Recipe> recipes = recipeRepository.findAll();
            return new ResponseEntity<>(recipes, HttpStatus.OK);
        } catch (ResponseStatusException exc) {
            return new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error");
        }
    }

    @PostMapping("/recipes")
    public Object saveRecipe(@RequestBody Recipe recipe) {
        try {
            recipeRepository.save(recipe);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (ResponseStatusException exc) {
            return new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error");
        }
    }

    @GetMapping("/recipes/{recipeName}")
    public Object getRecipe(@PathVariable String recipeName) {
        try {
            Recipe theRecipe = getTheRecipeByName(recipeName);
            if (theRecipe != null) {
                return new ResponseEntity<>(theRecipe, HttpStatus.OK);
            } else
                return new ResponseStatusException(NOT_FOUND, "Not found", new NoSuchElementException());
        } catch (ResponseStatusException exc) {
            return new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error");
        }
    }

    @PutMapping("/recipes/{recipeName}")
    public Object updateRecipe(@RequestBody @NotNull Recipe theRecipeRequest, @PathVariable String recipeName) {
        try {
            Recipe theRecipe = getTheRecipeByName(recipeName);
            if (theRecipe != null) {
                theRecipeRequest.setId(theRecipe.getId());
                recipeRepository.save(theRecipeRequest);
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else
                return new ResponseStatusException(NOT_FOUND, "Not found", new NoSuchElementException());
        } catch (ResponseStatusException exc) {
            return new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error");
        }
    }

    @DeleteMapping("/recipes/{recipeName}")
    public Object deleteRecipe(@PathVariable String recipeName) {
        try {
            Recipe theRecipe = getTheRecipeByName(recipeName);
            if (theRecipe != null) {
                recipeRepository.delete(theRecipe);
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else
                return new ResponseStatusException(NOT_FOUND, "Not found", new NoSuchElementException());
        } catch (ResponseStatusException exc) {
            return new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error");
        }
    }

    private Recipe getTheRecipeByName(String recipeName) {
        return recipeRepository.findByName(recipeName);
    }
}
