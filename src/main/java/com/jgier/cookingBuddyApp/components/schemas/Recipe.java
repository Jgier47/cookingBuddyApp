package com.jgier.cookingBuddyApp.components.schemas;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

@Entity
@Table(name = "recipe")
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "recipe_name", nullable = false, length = 128, unique = true)
    @NotBlank(message = "Recipe name cannot be blank")
    @Length(max = 128, message = "Recipe name must be between 1-128 characters")
    private String name;

    @Column(name = "instructions", nullable = false, length = 1024, unique = true)
    @NotBlank(message = "Instructions field cannot be blank")
    @Length(max = 1024, message = "Instructions field must be between 1-1024 characters")
    private String instructions;

    @Email
    @Column(name = "author", length = 128, unique = true)
    private String author;

    public Recipe() {
    }

    public Recipe(String recipeName, String instructions, String author) {
        this.name = recipeName;
        this.instructions = instructions;
        this.author = author;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    @Override
    public String toString() {
        return "Recipe{" +
                "id=" + id +
                ", recipe name='" + name + '\'' +
                ", instructions='" + instructions + '\'' +
                ", author='" + author + '\'' +
                '}';
    }
}
