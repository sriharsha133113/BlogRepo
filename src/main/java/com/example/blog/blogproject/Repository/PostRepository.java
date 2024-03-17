package com.example.blog.blogproject.Repository;

import com.example.blog.blogproject.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Integer> {
    List<Post> findAllByOrderByPublishedAtDesc();

    List<Post> findAllByOrderByPublishedAtAsc();

    List<Post> findByTitleContainingOrContentContainingOrTagsNameContaining(String title, String content, String tagName);

//    Post findById(int theId);


}
