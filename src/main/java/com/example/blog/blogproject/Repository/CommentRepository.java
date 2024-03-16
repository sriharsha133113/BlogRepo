package com.example.blog.blogproject.Repository;

import com.example.blog.blogproject.entity.Comments;
import com.example.blog.blogproject.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comments, Integer> {
}
