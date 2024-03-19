package com.example.blog.blogproject.service;

import com.example.blog.blogproject.entity.Post;
import com.example.blog.blogproject.entity.User;

import java.util.List;

public interface UserService {

    void save(User theUser);

    User FindUser();

    List<User> getAllUsers();
}
