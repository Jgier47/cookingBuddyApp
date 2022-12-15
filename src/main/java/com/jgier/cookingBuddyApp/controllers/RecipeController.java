package com.jgier.cookingBuddyApp.controllers;

import com.jgier.cookingBuddyApp.components.schemas.Recipe;
import com.jgier.cookingBuddyApp.components.schemas.RecipeDTO;
import com.jgier.cookingBuddyApp.service.RecipeService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@RestController
public class RecipeController {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    RecipeService recipeService;

    @Autowired
    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping("/recipes")
    public List<RecipeDTO> showAllRecipes() {
        return recipeService.findAll().stream().map(post -> modelMapper.map(post, RecipeDTO.class))
                .collect(Collectors.toList());
    }

    @PostMapping("/recipes")
    public ResponseEntity<RecipeDTO> createRecipe(@Valid @RequestBody RecipeDTO recipeDTO) {

        Recipe recipeRequest = modelMapper.map(recipeDTO, Recipe.class);
        recipeService.createRecipe(recipeRequest);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/recipes/{recipeName}")
    public ResponseEntity<RecipeDTO> getRecipe(@PathVariable String recipeName) {
        Recipe recipeFromDb = recipeService.findByName(recipeName);
        if (recipeFromDb == null)
            throw new NoSuchElementException();
        RecipeDTO recipeResponse = modelMapper.map(recipeFromDb, RecipeDTO.class);

        return new ResponseEntity<>(recipeResponse, HttpStatus.OK);
    }

    @PutMapping("/recipes/{recipeName}")
    public ResponseEntity<RecipeDTO> updateRecipe(@Valid @RequestBody @NotNull RecipeDTO theRecipeDto, @PathVariable String recipeName) {

        Recipe recipeRequest = modelMapper.map(theRecipeDto, Recipe.class);
        recipeService.updateRecipe(recipeName, recipeRequest);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/recipes/{recipeName}")
    public ResponseEntity<RecipeDTO> deleteRecipe(@PathVariable String recipeName) {
        recipeService.deleteRecipe(recipeName);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
