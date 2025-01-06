package com.ningzhi.blog_server.post;

import jakarta.validation.constraints.NotEmpty;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;

import java.time.LocalDateTime;

public record Post(
        @Id
        Long id,

        @NotEmpty
        String title,

        @NotEmpty
        String content,

        @NotEmpty
        Long author,

        LocalDateTime createdAt,
        LocalDateTime updatedAt,

        @Version
        Integer version
) {
    public Post {
        // validate only if both fields are provided
        if(createdAt != null && updatedAt != null && createdAt.isAfter(updatedAt)) {
            throw new IllegalArgumentException("createdAt must be before updatedAt");
        }
    }
}
