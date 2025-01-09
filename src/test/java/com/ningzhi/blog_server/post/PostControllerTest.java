package com.ningzhi.blog_server.post;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@WebMvcTest(PostController.class)
class PostControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @TestConfiguration
    static class PostControllerTestContextConfiguration {
        @Bean
        public PostRepository postRepository() {
            return Mockito.mock(PostRepository.class);
        }
    }

    @Autowired
    PostRepository postRepository;

    private final List<Post> posts = new ArrayList<>();


    @BeforeEach
    void setUp() {
        posts.add(new Post(
                99L, "Test Title", "Test Content", 99L,
                LocalDateTime.now(), LocalDateTime.now(), 0
        ));
    }

    @Test
    void shouldFindAll() throws Exception {
        when(postRepository.findAll()).thenReturn(posts);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/posts"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()",is(posts.size())));
    }

    @Test
    void shouldFindOnePostById() throws Exception {
        Post post = posts.get(0);
        when(postRepository.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.of(post));
        mockMvc.perform(MockMvcRequestBuilders.get("/api/posts/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(post.id().intValue()))) // Explicit Type Conversion needed here
                .andExpect(jsonPath("$.title", is(post.title())))
                .andExpect(jsonPath("$.content", is(post.content())))
                .andExpect(jsonPath("$.author", is(post.author().intValue())))
                .andExpect(jsonPath("$.createdAt", is(post.createdAt().withNano(post.createdAt().getNano() / 1000 * 1000).toString())))
                .andExpect(jsonPath("$.updatedAt", is(post.updatedAt().withNano(post.updatedAt().getNano() / 1000 * 1000).toString())))
                .andExpect(jsonPath("$.version", is(post.version())));
    }
    @Test
    void shouldNotFindOnePostById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/posts/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void create() {
    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }
}