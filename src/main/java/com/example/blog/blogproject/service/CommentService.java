package com.example.blog.blogproject.service;

import com.example.blog.blogproject.entity.Comments;
import com.example.blog.blogproject.entity.Post;

import java.util.List;

public interface CommentService {
    void deleteComment(int theId);

    Comments findById(int theId);

    void save(Comments updatedComment);

    List<Comments> findAll();

//    Comments findById(int theId);
}
