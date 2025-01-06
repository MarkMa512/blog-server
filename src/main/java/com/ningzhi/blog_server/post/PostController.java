package com.ningzhi.blog_server.post;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody Post post) {
        LocalDateTime now = LocalDateTime.now();
        postRepository.save(new Post(
                post.id(),
                post.title(),
                post.content(),
                post.author(),
                post.createdAt() != null ? post.createdAt():now,
                post.updatedAt() != null ? post.updatedAt():now,
                post.version()
        ));
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void update(@PathVariable Long id, @RequestBody Post post) {
        if (post.title()==null || post.content()==null || post.author()==null){
            throw new IllegalArgumentException("Title, content, and author cannot be null");
        }
        Optional<Post> existingPost = postRepository.findById(id);
        if (existingPost.isEmpty()){
            throw new PostNotFoundException();
        }
        Post updatedPost = new Post(
                id,
                post.title(),
                post.content(),
                post.author(),
                existingPost.get().createdAt(), // preserve the createAt time
                LocalDateTime.now(),
                existingPost.get().version()
        );
        postRepository.save(updatedPost);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable Long id) {
        Optional<Post> postOptional = postRepository.findById(id);
        if (postOptional.isEmpty()){
            throw new PostNotFoundException();
        }
        postRepository.delete(postOptional.get());
    }
}
