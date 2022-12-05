# Spring Boot Cooking Buddy Rest API

This is a sample Java / Maven / Spring Boot application that can be used as a starter for further development.

## Installation

1) Clone the repository
2) Build Maven project
3) Make sure that docker is running
4) Navigate to root directory and use

```bash
docker-compose up
```  

4) Application is running. It's available at http://localhost:8047/recipes

## CRUD coverage

A) GET /recipes - returns all available recipes

B) POST /recipes - recipe creation

C) GET /recipes/{name} - return recipe by name

D) PUT /recipes/{name} - update single recipe

E) DELETE /recipes/{name} -delete single recipe

## Task requirements:

File "cooking_buddy_api.yml" is available in project - /requirements/cooking_buddy_api.yml