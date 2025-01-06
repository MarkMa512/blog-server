
-- Long id,
-- String title,
-- String content,
-- Long author,
-- LocalDateTime createdAt,
-- LocalDateTime updatedAt,
-- Integer version

CREATE TABLE IF NOT EXISTS post (
    id SERIAL NOT NULL, -- Auto-incremented primary key
    title VARCHAR(250) NOT NULL, -- Title of the post
    content TEXT NOT NULL, -- Main content of the post
    author VARCHAR(100) NOT NULL, -- Author of the post
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP, -- Timestamp for post creation
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP, -- Timestamp for last update
    version INT, -- Version for optimistic locking or tracking updates
    PRIMARY KEY (id) -- Primary key for the table
);