package com.example.blog.blogproject.controller;

import com.example.blog.blogproject.entity.User;
import com.example.blog.blogproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LoginController {

    @Autowired
    private UserService userService;

    @GetMapping("/showMyLoginPage")
    public String showMyLoginPage(){
        return "fancy-login";
    }

    @GetMapping("/access-denied")
    public String showAccessDenied(){

        return "access-denied";
    }

    @PostMapping("/registerForm")
    public String showRegistrationForm(Model themodel){
        User user=new User();
        themodel.addAttribute("user",user);
        return "register-form";
    }

    @PostMapping("/saveUser")
    public String saveUserInDataBase(@ModelAttribute("user") User theuser, Model themodel){
        userService.save(theuser);
        return "redirect:/blog/showMyLoginPage";

    }

}
