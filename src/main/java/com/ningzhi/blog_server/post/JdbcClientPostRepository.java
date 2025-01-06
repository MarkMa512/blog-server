package com.ningzhi.blog_server.post;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class JdbcClientPostRepository {
    private static final Logger logger = LoggerFactory.getLogger(JdbcClientPostRepository.class);
    private final JdbcClient jdbcClient;

    public JdbcClientPostRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    public List<Post> findAll() {
        return jdbcClient.sql("SELECT * FROM post")
                .query(Post.class)
                .list();
    }

    public Optional<Post> findById(Long id){
        return jdbcClient.sql("SELECT id,title, content, author, created_at, updated_at, version FROM post WHERE id=:id")
                .param("id",id)
                .query(Post.class)
                .optional();
    }

}
