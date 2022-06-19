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

| Action                             | Explanation                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                              |
|------------------------------------|----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| `get` all recipes                  | path: `/recipes`  <br/>parameters: none  </br> returns: List of recipes in JSON format.                                                                                                                                                                                                                                                                                                                                                                                                                                                                  |
| `post` a recipe                    | path: `/recipes/add <body: application/json>`  <br/>parameters:  <br/>* `<body> json`: JSON with contents of the recipe to create.<br/>* [optional] `page`: integer with the page number to return. The API by defaults limits the number of recipes returned to 15 per page. Default is 0, the first page.                                                                                                                                                                                                                                              |
| `put` a recipe:                    | path: `/recipes/put <body: application/json>` </br> Parameters: </br> * `id`: id of the recipe in the database to replace <br/> * `<body> json`: Recipe to replace the entry with. Id cannot be changed, but must be supplied! See template below. <br/> Returns: `List<Recipe>` of the old recipe in database and new entry.                                                                                                                                                                                                                            |
| `delete` a recipe                  | path: `/recipes/delete/<id>`</br>parameters:</br>* `id`: id of the recipe in the database to delete.                                                                                                                                                                                                                                                                                                                                                                                                                                                     |
| `get` a recipe based on conditions | path: `localhost:8081/recipe/find`</br>parameters:</br>* `page` (default 0): Which page of results you wish to return. Each page holds 15 entries.</br>* `<body> json`: A list of criteria to which the recipe should adhere. The possible keys in the JSON are displayed below. The json should contain only the keys which actually have values. The only difference between this JSON and the Recipe JSON, is the addition of "notIngredients", which are the ingredients to exclude from the results. <br/> returns: List of recipes in JSON format. |

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

For the entirety of this code the following applies: it was made with the assessment in mind, with the given timebox of 4-8 hours.
That means that some decisions would not be made in production, or inconsistencies are purposely implemented to display different possibilities and to not overrun the timebox.

**_[Sprint Boot]_** is chosen as the framework, because it is arguably the most popular for REST APIs. 
It is flexible and allows for many actions to be automatically resolved or linked.
Sprint Boot Data also offers a MongoDB module, further simplifying the implementation.
Also, the alternatives are niches and offer things that don't even come close to being useful for this project.

**_[MongoDB]_** was almost an instant decision after reading the assignment scope. 
Recipes lend themselves quite well to the JSON document format, and thus it allows for easy integration of the API.
Using MongoDB also allows for flexible queries: any field can be easily added or excluded in a search and negative searches are natively also supported.
Unfortunately, due to an unresolved bug, the negative lookaround regex query is not working and had to be resolved by writing more application logic.
This could directly be resolved by switching the builtin Spring MongoDB Respository with the MongoDB Java Driver: this was not done in this project, to showcase the benefits of Spring instead.
This section of code also serves as a testament for thinking in quick workaround solutions in where time is of the essence, something I practiced frequently during my standby shifts at ING.

**_[Docker]_** because MongoDB was chosen as a backend, the need arose to facilitate the reviewers. The instructions that explain how MongoDB can be run are therefore added.
But more importantly, it should not be necessary to even run it, as the integration tests are tested against an embedded MongoDB implementation.

**_[Embedded MongoDB]_** The choice for this is described partly above, the other part is that an embedded version of a project' datastore greatly improves the Test Driven Development paradigm.
An embedded datastore for integration testing can be ran at all times and even continuously/automatically, for example after every git commit.
Problems arise earlier and are less likely to end up in production. Before a merge however, the embedded datastore should be swapped out with an actual implementation (easily done in application.properties).

**_[API Documentation]_** is kept concise to avoid confusion. Users only address it in case of issues or to get started, and that latter option is significantly more frequent.
In a more complete project, a choice for a tooling/framework such as Swagger can be considered. For this small scope, the manual approach should be sufficient.