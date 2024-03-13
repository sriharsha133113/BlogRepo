package com.example.blog.blogproject.service;

import com.example.blog.blogproject.dao.PostRepository;
import com.example.blog.blogproject.dao.UserRepository;
import com.example.blog.blogproject.entity.Post;
import com.example.blog.blogproject.entity.User;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository theUserRepository) {
        userRepository = theUserRepository;
    }


    @Override
    @Transactional
    public void save(User theUser) {

        userRepository.save(theUser);

    }
}
