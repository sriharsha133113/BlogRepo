package com.example.blog.blogproject.controller;

import com.example.blog.blogproject.Repository.UserRepository;
import com.example.blog.blogproject.entity.Comments;
import com.example.blog.blogproject.entity.Post;
import com.example.blog.blogproject.service.CommentService;
import com.example.blog.blogproject.service.PostService;
import com.example.blog.blogproject.service.TagService;
import com.example.blog.blogproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
@Controller
public class CommentController {

    @Autowired
    private PostService postService;
    @Autowired
    private UserService userService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private TagService tagService;
    @Autowired
    private UserRepository userRepository;



    @PostMapping("/addComments")
    public String addComments(Model themodel, @ModelAttribute("post") Post thepost, @ModelAttribute("comments") Comments thecomment){
        Post currentPost = postService.findById(thepost.getId());
        Comments currentComment = new Comments();
        currentComment.setComment(thecomment.getComment());
        currentComment.setName(currentPost.getAuthor().getName());
        currentComment.setEmail(currentPost.getAuthor().getEmail());
        currentComment.setCreatedAt(LocalDateTime.now());
        currentPost.addComment(currentComment);
        postService.save(currentPost);
        themodel.addAttribute("post", currentPost);
        return "personal_post";

    }

    @GetMapping("/updateComment")
    public String updateComment(@RequestParam("commentId") int theId, Model themodel){

        Comments comment = commentService.findById(theId);
        themodel.addAttribute("commentId",comment.getId());
        themodel.addAttribute("commentcontent",comment.getComment());
        return "update_comment_form";

    }

    @PostMapping("/saveupdateComment")
    public String saveUpdatedComment(@RequestParam("presentcommentId") int theId,@RequestParam("presentcomment") String thecomment){

        Comments comment = commentService.findById(theId);
        comment.setUpdatedAt(LocalDateTime.now());
        comment.setComment(thecomment);
        commentService.save(comment);
        return "redirect:/blog/";
    }

    @GetMapping("/deleteComment")
    public String deleteComment(@RequestParam("commentId") int commentId,Model themodel) {
        commentService.deleteComment(commentId);
        return "redirect:/blog/";
    }


}
