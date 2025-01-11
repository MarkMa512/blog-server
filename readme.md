# Blog Server with Spring Boot 

![Java CI with Maven](https://github.com/MarkMa512/blog-server/actions/actions/workflows/maven.yml/badge.svg)

## Built with
1. Spring Boot
2. PostgreSQL
3. Docker

## Key Features

### REST API
- This project aims to implement a set of **RESTful API** for blog post management. 
- Supporting essential **CRUD Operations**
  - Create: Add new blog posts
  ```
  POST http://localhost:8080/api/posts
  ```
  - Read: Fetch individual/all blog post
  ```
  GET http://localhost:8080/api/posts
  ```
  - Update: Modify existing blog posts
  ```
  PUT http://localhost:8080/api/posts/{id}
  ```
  - Delete: Remove blog posts from the database
  ```
  DELETE http://localhost:8080/api/posts/{id}
  ```
  
- Follows the best practice for REST API design, including proper use of 
  - HTTP Method
  - HTTP Status code
  - Request and Response Structure

For more details, please refer to [api/post.http](api/post.http)

### MVC Models: Model View Controller
- **Packaged by feature**: This project follows this convention for better encapsulation, 
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
- Unit Tests for `PostController`: [`PostControllerTest.java`](src/test/java/com/ningzhi/blog_server/post/PostControllerTest.java)
    - Validates the behavior of the PostController in isolation.
    - Ensures the correctness of the REST endpoints.
#### Integration Test
- Integration Test for `PostController`: [`PostControllerIntegrationTest.java`](src/test/java/com/ningzhi/blog_server/post/PostControllerIntegrationTest.java)
  - Test the interaction between PostController, PostRepository and the Database. 
  - Utilizing In-memory H2 database for realistic testing. 
  - Verifies the complete workflow, from API calls to database operations.

#### Tools Used
- **JUnit**: For writing and running tests.
- **Mockito**: For mocking dependencies in unit tests.
- **Spring Boot Test**: For integration testing with embedded contexts.
