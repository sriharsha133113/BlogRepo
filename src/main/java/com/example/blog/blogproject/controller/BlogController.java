package com.example.blog.blogproject.controller;

import com.example.blog.blogproject.entity.Post;
import com.example.blog.blogproject.entity.User;
import com.example.blog.blogproject.service.PostService;
import com.example.blog.blogproject.service.UserService;
import org.apache.catalina.Store;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;

@Controller
@RequestMapping("/blog")
public class BlogController {
    @Autowired
    private PostService postService;
    @Autowired
    private UserService userService;
    @GetMapping("/createpost")
    public  String createPost(Model theModel){
        Post thepost=new Post();
        User theuser=new User("user1","email1","password1");
        userService.save(theuser);
        thepost.setCreatedAt(LocalDateTime.now());
        thepost.setAuthor(theuser);

        theModel.addAttribute("post",thepost);
        return "create_post";

    }

    @PostMapping("/savepost")
    public  String savePost(@ModelAttribute("post") Post thepost){
        thepost.setPublished(true);
        thepost.setPublishedAt(LocalDateTime.now());
        System.out.println(thepost);
        postService.save(thepost);

        return "redirect:/blog/createpost";



    }
}
