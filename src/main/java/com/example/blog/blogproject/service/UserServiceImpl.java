package com.example.blog.blogproject.service;

import com.example.blog.blogproject.Repository.UserRepository;
import com.example.blog.blogproject.entity.User;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
    @Override
    public User FindUser() {
        return userRepository.findById(2).get();
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
