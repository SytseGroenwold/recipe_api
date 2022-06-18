# Recipe API
Basic Spring Boot API with MongoDB persistent storage to perform CRUD actions on a collection of recipes.
 
## About
This Recipe API is created as part of an assessment for a job application process with ABN AMRO Bank.

Version: 0.1.0  
Author: Sytse Groenwold  
Contact: sytsegroenwold@gmail.com

# Table of contents
- [Requirements](#requirements)
- [How to set up](#how-to-set-up)
- [How to use (API docs)](#how-to-use-api-docs)
- [Design decisions](#design-decisions)

## Requirements
While the versions of these requirements are likely not as strict, these were the versions used during development. 
In case of any issues, try updating to these version first.
* Java 17.0.3
* Maven 3.8.5
* Docker 20.10.10
* MongoDB 5.0.6 (See [How to set up](#how-to-set-up) for more info)

## How to set up
The API requires MongoDB as a backend to start and function.
Steps 1 and 2 are optional, if you choose to use another implementation of MongoDB more convenient.

1. `docker mongo:5.0.6`  
   Downloads the used version of MongoDB docker image.
   Below step should do it automatically, but in case of trouble, see if you can download this image separately. 
2. `docker run -d --name mongo-on-docker -p 27017:27017 mongo:5.0.6`  
    Starts the MongoDB instance inside a docker container. 
    The name can be changed at will. 
3. `mvn clean install`  
    While the below command should do the same, in case you run into issues, ensure the application can be build.
4. `mvn spring-boot:run`  
    After this command the API application is ready to be used. See the API docs on how to use the API.

## How to use (API Docs)
The API should be reachable through your usual preferred method.
Postman was used during the development and is recommended

The API is exposed on port `8081`, thus going to `localhost:8081` in your browser should show you an error page.
This is correct: if the application is not running, you should see a "cannot connect" error instead.

The following paths are available in this API:

| Action                             | Explanation                                                                                                                                                                                                                                                                                                                                                                                                                                                                                         |
|------------------------------------|-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| `get` all recipes                  | path: `/recipes`  <br/>parameters: none                                                                                                                                                                                                                                                                                                                                                                                                                                                             |
| `post` a recipe                    | path: `/recipes/add <body: application/json>`  <br/>parameters:  <br/>* (json body): JSON with contents of the recipe to create.<br/>* [optional] page:                                                                                                                                                                                                                                                                                                                                             |
| `put` a recipe:                    | path: `/recipes/put <body: application/json>` </br> Parameters: </br> *                                                                                                                                                                                                                                                                                                                                                                                                                             |
| `delete` a recipe                  | path: `/recipes/delete/<id>`</br>parameters:</br>* `id`: id of the recipe in the database to delete.                                                                                                                                                                                                                                                                                                                                                                                                |
| `get` a recipe based on conditions | path: `localhost:8081/recipe/find`</br>parameters:</br>* page (default 0): Which page of results you wish to return. Each page holds 15 entries.</br>* json body: A list of criteria to which the recipe should adhere. The possible keys in the JSON are displayed below. The json should contain only the keys which actually have values. The only difference between this JSON and the Recipe JSON, is the addition of "notIngredients", which are the ingredients to exclude from the results. |

The recipes are returned and the json body templates:
> Result and `post`/`put` .json template:
>```
>#recipe.json
>{
>    "id": String,
>    "name": String,
>    "diet": String,
>    "servings": Integer,
>    "ingredients": String,
>    "instructions": String
>}
>```
> `get` method .json template:
> ```
>\#body.json
>\{
>    "id": String,
>    "name": String,
>    "diet": String,
>    "servings": Integer,
>    "ingredients": String,
>    "notIngredients: String,
>    "instructions": String
>\}
>```

## Design decisions
Any code specific decisions are added as comments in the code directly at the relevant location, avoiding lengthily descriptions and troubles finding what is being referred to.

For the entirety of this code the following applies: it was made with the assessment in mind.
That means that some decisions would not be made in production, or inconsistencies are purposely implemented to display different possibilities.


Java a requirement from the assessment.  
Sprint Boot  
MongoDB  
Docker  
Embedded MongoDB  
API Documentation  

### To-do
The scope of this project being an assessment of 4-8 hours, this is a list of actions that were not taking due to time-boxing constraints.
* The `Recipe` model `id` field could be