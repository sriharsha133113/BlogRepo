package com.example.blog.blogproject.Repository;

import com.example.blog.blogproject.entity.Post;
import com.example.blog.blogproject.entity.Tags;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TagRepository extends JpaRepository<Tags, Integer> {
    Optional<Tags> findByName(String name);

}
