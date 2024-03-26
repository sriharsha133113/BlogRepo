package com.example.blog.blogproject.service;

import com.example.blog.blogproject.Repository.UserRepository;
import com.example.blog.blogproject.entity.Role;
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
    public void save(User user) {
        String name = user.getName();
        User existingUser = userRepository.findUserByName(user.getName());
        if(existingUser == null){
            String password =user.getPassword();
            user.setPassword("{noop}"+password);
            Role role = new Role();
            role.setRole("ROLE_AUTHOR");
            user.setRole(role);
            role.setUsername(user.getName());
            user.setEnabled(true);
            userRepository.save(user);
        }

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
