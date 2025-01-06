package com.ningzhi.blog_server.post;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/posts")
public class PostController {
    private final PostRepository postRepository;

    @Autowired
    public PostController(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @GetMapping("")
    List<Post> findAll() {
        return postRepository.findAll();
    }

    @GetMapping("/{id}")
    Optional<Post> findById(@PathVariable Long id) {
        Optional<Post> post = postRepository.findById(id);
        if (post.isEmpty()){
            throw new PostNotFoundException();
        }
        return post;
    }

    @PostMapping("")
    public Post create(@RequestBody Post post) {
        LocalDateTime now = LocalDateTime.now();
        return postRepository.save(new Post(
                post.id(),
                post.title(),
                post.content(),
                post.author(),
                post.createdAt() != null ? post.createdAt():now,
                post.updatedAt() != null ? post.updatedAt():now,
                post.version()
        ));
    }
}
