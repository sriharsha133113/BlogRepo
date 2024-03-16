package com.example.blog.blogproject.Repository;

import com.example.blog.blogproject.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Integer> {

//    Post findById(int theId);


}
