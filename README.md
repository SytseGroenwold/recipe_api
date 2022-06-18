# Recipe API
Simple API to perform CRUD actions on recipes.

## About
This Recipe API is created as part of an assessment for a job application process with ABN AMRO Bank.

Author: Sytse Groenwold  
Contact: sytse@groenwold.dev

## Requirements
While the versions of these requirements are likely not as strict, these were the versions used during development. 
In case of any issues, try updating to these version first.
* Java 17.0.3
* Maven 3.8.5
* Docker 20.10.10
* MongoDB 5.0.6 (See [How to setup](#how-to-setup)) for more info)

## How to setup
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

## API Docs
The API should be reachable through your usual preferred method.
Postman was used during the development and is recommended

The API is exposed on port `8081`, thus going to `localhost:8081` in your browser should show you an error page.
This is correct: if the application is not running, you should see a "cannot connect" error instead.

The recipes returned from this API are of the following format, and any `post`ed recipes should follow suit:  
```
{
    "id": String,
    "name": String,
    "diet": String,
    "servings": Integer,
    "ingredients": String,
    "instructions": String
}
```


The following paths are available in this API:
* `get` all recipes  
  path: `localhost:8081/recipes`  
  parameters: none  
  
* `post` a recipe  
  path: `localhost:8081/recipe/add <application/json>`    
  parameters:  
  * (json body): JSON with contents of the recipe to create.
  * [optional] page: 
* `put` a recipe:
  
* `delete` a recipe  
  path: `localhost:8081/recipe/delete/<id>`  
  parameters: 
  * `id`: id of the recipe in the database to delete.

## Design decisions
As this project is part of an assessment, here the decisions on implementations taken or passed are explained.

Java a requirement from the assessment.  
Sprint Boot
MongoDB
Docker
Embedded MongoDB