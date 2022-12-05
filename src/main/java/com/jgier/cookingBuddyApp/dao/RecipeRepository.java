package com.jgier.cookingBuddyApp.dao;

import com.jgier.cookingBuddyApp.components.schemas.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecipeRepository extends JpaRepository<Recipe, Integer> {
    Recipe findByName(String name);

}
