package com.example.blog.blogproject.dao;

import com.example.blog.blogproject.entity.Post;
import com.example.blog.blogproject.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
}
