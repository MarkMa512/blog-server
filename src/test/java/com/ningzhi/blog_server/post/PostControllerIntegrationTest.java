package com.ningzhi.blog_server.post;

import jakarta.validation.constraints.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.client.RestClient;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
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
    void shouldFindPostById() {
    }

    @Test
    void shouldCreatePost() {
    }

    @Test
    void shouldUpdateExistingPost() {
    }

    @Test
    void shouldDeleteExistingPost() {
    }
}