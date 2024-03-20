package com.example.blog.blogproject.service;

import com.example.blog.blogproject.entity.Post;
import com.example.blog.blogproject.entity.Tags;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

public interface PostService {

    void save(Post thepost);

    List<Post> getAllPosts();
//    void setTags(Post post, String tags);

//    void checkForTags(List<Tags> tags);
    List<Tags> checkedForTags(List<Tags> tags);

    Post findById(int theId);

//    void deleteById(int theId);

    void deletePost(Post post);

    List<Post> getLatestPosts();

    List<Post> getOldestPosts();

    List<Post> searchPosts(String query);

    List<Post> searchPostsByAuthor(String query);

    List<Post> getPostsByAuthors(List<Integer> authorIds);
    List<Post> getPostsByTags(List<Integer> tagIds);


    List<Post> findByTagsAndAuthors(List<String> selectedTags, List<String> selectedAuthors);

    List<Post> searchPostsByAuthorsAndTags(List<String> authors, List<String> tags);

    List<Post> filterAndSearchPosts(String query, List<String> authors, List<String> tags, LocalDateTime startDateTime, LocalDateTime endDateTime);

}
