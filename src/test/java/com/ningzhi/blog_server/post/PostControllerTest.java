package com.ningzhi.blog_server.post;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@WebMvcTest(PostController.class)
//@SpringBootTest(properties = "spring.profiles.active=test"):
// Not to be enabled: found multiple declarations of @BootstrapWith for test class
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
//                .andExpect(jsonPath("$.createdAt", is(post.createdAt().withNano(post.createdAt()
//                        .getNano() / 1000 * 1000).toString())))
//                .andExpect(jsonPath("$.updatedAt", is(post.updatedAt().withNano(post.updatedAt()
//                        .getNano() / 1000 * 1000).toString())))
                .andExpect(jsonPath("$.version", is(post.version())));
    }
    @Test
    void shouldNotFindOnePostById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/posts/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldCreateOnePost() throws Exception {
        Post post = new Post(
                null, "Test Title", "Test Content", 99L,
                LocalDateTime.now(), LocalDateTime.now(), 0
        );
        mockMvc.perform(post("/api/posts")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(post))
                )
                .andExpect(status().isCreated());
    }

    @Test
    void shouldUpdatePost() throws Exception {
        // Mock existing post in the postRepository
        Post existingPost = posts.get(0);
        // Mock findById()
        when(postRepository.findById(1L)).thenReturn(Optional.of(existingPost));

        // Mock save operation
        Post updatedPost = new Post(
                1L, "Updated Title", "Updated Content", 99L,
                existingPost.createdAt(), // Preserve createdAt
                LocalDateTime.now(), // Updated at
                existingPost.version() // Preserve version
        );
        // Mock save
        when(postRepository.save(ArgumentMatchers.any(Post.class))).thenReturn(updatedPost);

        // New post data for update
        Post updatePostRequest = new Post(
                null, "Updated Title", "Updated Content", 99L,
                null, null, 0
        );
        mockMvc.perform(put("/api/posts/1")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(updatePostRequest))
                )
                .andExpect(status().isAccepted());
    }

    @Test
    void shouldDeletePost() throws Exception {
        Post exisitingPost = posts.get(0);
        when(postRepository.findById(1L)).thenReturn(Optional.of(exisitingPost));

        // Mock delete operation
        doNothing().when(postRepository).delete(exisitingPost);

        mockMvc.perform(delete("/api/posts/1"))
                .andExpect(status().isOk());
    }
}