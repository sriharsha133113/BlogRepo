package com.example.blog.blogproject.Repository;

import com.example.blog.blogproject.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {

    User findUserByName(String name);
}
