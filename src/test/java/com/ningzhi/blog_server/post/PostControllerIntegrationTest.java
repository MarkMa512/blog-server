package com.ningzhi.blog_server.post;

import jakarta.validation.constraints.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.client.RestClient;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = "spring.profiles.active=test")
// must have the properties been set:
/* PostControllerIntegrationTest.shouldCreatePost » IllegalState ApplicationContext failure threshold (1) exceeded:
 * skipping repeated attempt to load context for [WebMergedContextConfiguration@7df5549e testClass =
 * com.ningzhi.blog_server.post.PostControllerIntegrationTest
 */
//@ActiveProfiles("test")
class PostControllerIntegrationTest {

    @LocalServerPort
    private int randomServerPort;
    RestClient restClient;

    @BeforeEach
    void setUp() {
        restClient = RestClient.create("http://localhost:" + randomServerPort);
    }

    @Test
    void shouldFindAllPosts() {
        List<Post> posts = restClient.get()
                .uri("/api/posts")
                .retrieve()
                .body(new ParameterizedTypeReference<List<Post>>() {});

        assertNotNull(posts, "The response should not be null");
        assertFalse(posts.isEmpty(), "The response should not be empty");
    }

    @Test
    void shouldFindOnePostById() {
        Post post = restClient.get()
            .uri("api/posts/1")
            .retrieve()
            .body(Post.class);

        assertNotNull(post, "The response should not be null");
        assertAll(
                ()-> assertEquals(1,post.id()),
                ()-> assertEquals("Introduction to Spring Boot", post.title()),
                ()-> assertEquals("This post explains the basics of Spring Boot and how to get started.", post.content()),
                ()-> assertEquals(1, post.author()),
                ()-> assertEquals(1, post.version())
        );
    }

    @Test
    void shouldCreatePost() {
        Post post = new Post(
                null, "Integration Test", "This is a post created in integration test. ", 1L,
                null, null, null);

        ResponseEntity<Void> response = restClient.post()
                .uri("api/posts")
                .body(post)
                .retrieve()
                .toBodilessEntity();

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    void shouldUpdateExistingPost() {
        Post post = restClient.get().uri("api/posts/2").retrieve().body(Post.class);
        assertNotNull(post, "The response should not be null");

        ResponseEntity<Void> updatedRun = restClient.put()
                .uri("api/posts/2")
                .body(post)
                .retrieve()
                .toBodilessEntity();

        assertEquals(HttpStatus.ACCEPTED, updatedRun.getStatusCode());
    }

    @Test
    void shouldDeleteExistingPost() {
        ResponseEntity<Void> post = restClient.delete()
                .uri("api/posts/5")
                .retrieve()
                .toBodilessEntity();

        assertEquals(HttpStatus.OK, post.getStatusCode());
    }
}