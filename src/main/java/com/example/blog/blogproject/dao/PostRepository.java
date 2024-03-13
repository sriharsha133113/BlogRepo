package com.example.blog.blogproject.dao;

import com.example.blog.blogproject.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Integer> {
}
