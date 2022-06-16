# Recipe API

## About

## How to run
1. docker mongo:5.0.7
2. docker run -d --name mongo-on-docker -p 27017:27017 mongo:5.0.7 
3. mvn clean install
4. mvn spring-boot:run

## API Docs
* get all: localhost:8081/recipes
* post: localhost:8081/recipe/add (json body)
* delete: localhost:8081/recipe/delete/<id>
## Design decisions