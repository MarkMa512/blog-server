# Blog Server with Spring Boot 

## Built with
1. Spring Boot
2. PostgreSQL
3. Docker

## Key Features

### REST API
- This project aims to implement a set of RESTful API for blog post management. 
- Supporting essential CRUD Operations
  - Create: Add new Blog Posts
  - Read: Fetch individual/all blog post
  - Update: Modify existing blog posts
  - Delete: Remove blog posts from the database
- Follows the best practice for REST API design, including 
  - Proper use of HTTP Method
  - Status code 
  - Request and Response Structure

### MVL Models: Model View Controller
- **Packaged by feature**: This project follows the "package by feature" convention for better encapsulation, 
maintainability and clarity. 
  - Each feature (eg. post) is a package that contains its own domain model, controller, repository, and relevant
  exceptions. 
  - Eg: 

```
post/
    - Post.java                     //Record: domain model for a blog post  
    - PostController.java           //Class: handles HTTP requests and responses
    - PostNotFoundException         //Class: custom exception for missing posts
    - PostRepository                //Interface: data access logic using Spring Data JPA
```

### Test Driven Development
- Emphasizes **Test-Driven-Development(TDD)** for robust and reliable code.

#### Unit Test
- Unit Tests for PostController
    - Validates the behavior of the PostController in isolation.
    - Ensures the correctness of the REST endpoints.
#### Integration Test 
- Test the interaction between PostController, PostRepository and the Database. 
- Verifies the complete workflow, from API calls to database operations.

#### Tools Used
- **JUnit**: For writing and running tests.
- **Mockito**: For mocking dependencies in unit tests.
- **Spring Boot Test**: For integration testing with embedded contexts.
