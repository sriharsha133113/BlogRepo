package com.example.blog.blogproject.controller;

import com.example.blog.blogproject.entity.Post;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/blog")
public class BlogController {
    @GetMapping("/createpost")
    public  String createPost(Model theModel){
        Post thepost=new Post();
        theModel.addAttribute("post",thepost);
        return "create_post";

    }
}
