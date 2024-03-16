package com.example.blog.blogproject.service;

import com.example.blog.blogproject.entity.Post;
import com.example.blog.blogproject.entity.Tags;

import java.util.List;

public interface PostService {

    void save(Post thepost);

    List<Post> getAllPosts();
//    void setTags(Post post, String tags);

    void checkForTags(List<Tags> tags);
    List<Tags> checkedForTags(List<Tags> tags);

    Post findById(int theId);

//    void deleteById(int theId);

    void deletePost(Post post);
}
