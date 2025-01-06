package com.ningzhi.blog_server.post;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class JdbcClientPostRepository {
    private static final Logger logger = LoggerFactory.getLogger(JdbcClientPostRepository.class);
    private final JdbcClient jdbcClient;

    public JdbcClientPostRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    public List<Post> findAll() {
        return jdbcClient.sql("SELECT * FROM Post")
                .query(Post.class)
                .list();
    }

}
