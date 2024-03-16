package com.example.blog.blogproject.service;

import com.example.blog.blogproject.entity.Comments;
import com.example.blog.blogproject.entity.Post;

public interface CommentService {
    void deleteComment(int theId);

    Comments findById(int theId);

    void save(Comments updatedComment);

//    Comments findById(int theId);
}
