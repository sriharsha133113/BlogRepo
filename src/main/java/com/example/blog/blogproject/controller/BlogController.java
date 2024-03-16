package com.example.blog.blogproject.controller;

import com.example.blog.blogproject.entity.Comments;
import com.example.blog.blogproject.entity.Post;
import com.example.blog.blogproject.entity.Tags;
import com.example.blog.blogproject.entity.User;
import com.example.blog.blogproject.service.CommentService;
import com.example.blog.blogproject.service.PostService;
import com.example.blog.blogproject.service.UserService;
import org.apache.catalina.Store;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/blog")

public class BlogController {
    @Autowired
    private PostService postService;
    @Autowired
    private UserService userService;

    @Autowired
    private CommentService commentService;


    @GetMapping("/createpost")
    public  String createPost(Model theModel){
        Post thepost=new Post();
        User theuser=new User("user2","email2","password2");
        thepost.setAuthor(theuser);
        System.out.println(theuser);
        userService.save(theuser);
        thepost.setCreatedAt(LocalDateTime.now());
        theModel.addAttribute("post",thepost);
        return "create_post";

    }


    @PostMapping("/savepost")
    public String savePost(@ModelAttribute("post") Post post, @RequestParam("tags") String tags) {


        post.setPublishedAt(LocalDateTime.now());
        List<Tags> tag=postService.checkedForTags(post.getNormalTags());
        // post.setAuthor(userService.FindUser());
        post.setTagsList(tag);
        User theuser=new User("user2","email2","password2");
        post.setAuthor(theuser);
        postService.save(post);

        return "redirect:/blog/getposts";
    }

    @GetMapping("/getposts")
    public String getAllPosts(Model model) {
        List<Post> posts = postService.getAllPosts();
        for (Post post : posts) {
            String content = post.getContent();
            if (content != null && content.length() >= 5) {
                post.setExcerpt(content.substring(0, 5));
            } else {
                post.setExcerpt(content);
            }
        }

        model.addAttribute("posts", posts);
        return "get_posts";
    }

    @GetMapping("/readForm")
    public String readForms(@RequestParam("formId") int theId,Model themodel){

        Post thepost = postService.findById(theId);
        Comments thecomment = new Comments();
        themodel.addAttribute("comments",thecomment);
        themodel.addAttribute("post",thepost);
        return "personal_post";

    }


    @GetMapping("/updateForm")
    public String updateForms(@RequestParam("formId") int theId,Model themodel){

        Post thepost = postService.findById(theId);

        themodel.addAttribute("post",thepost);
        return "create_post";

    }


    @GetMapping("/deleteForm")
    public String deleteForms(@RequestParam("formId") int theId,Model themodel){

        Post thepost = postService.findById(theId);
        System.out.println("post delete form"+theId);
        postService.deletePost(thepost);

        return "redirect:/blog/getposts";

    }

    @PostMapping("/addComments")
    public String addComments(Model themodel,@ModelAttribute("post")Post thepost,@ModelAttribute("comments")Comments thecomment){
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
//
//    @GetMapping("/updateComment")
//    public String updateComment(@RequestParam("commentId") int theId,Model themodel){
//
//        Comments comment = commentService.findById(theId);
//        themodel.addAttribute("commentId",comment.getId());
//
//        themodel.addAttribute("comments",comment);
//        return "update_comment_form";
//
//    }

    @GetMapping("/updateComment")
    public String updateComment(@RequestParam("commentId") int theId,Model themodel){

        Comments comment = commentService.findById(theId);
        themodel.addAttribute("commentId",comment.getId());

        themodel.addAttribute("commentcontent",comment.getComment());
        return "update_comment_form";

    }

    @PostMapping("/saveupdateComment")
    public String saveUpdatedComment(@RequestParam("presentcommentId") int theId,@RequestParam("presentcomment") String thecomment){
//        commentService.save(updatedComment); // Save the updated comment
//        return "redirect:/blog/getposts";
//    int commentId = Integer.parseInt( updatedComment.getId());
////
////    // Set the updated comment ID
//    updatedComment.setId(commentId);
        Comments comment = commentService.findById(theId);

        comment.setUpdatedAt(LocalDateTime.now());
        comment.setComment(thecomment);

        commentService.save(comment);

        return "redirect:/blog/getposts";
    }



//    @PostMapping("/saveupdateComment")
//    public String saveUpdatedComment(@ModelAttribute("comment") Comments updatedComment){
////        commentService.save(updatedComment); // Save the updated comment
////        return "redirect:/blog/getposts";
////    int commentId = Integer.parseInt( updatedComment.getId());
//////
//////    // Set the updated comment ID
////    updatedComment.setId(commentId);
//
//        updatedComment.setUpdatedAt(LocalDateTime.now());
//
//
//
//    commentService.save(updatedComment);
//
//    return "redirect:/blog/getposts";
//    }


    @GetMapping("/deleteComment")
    public String deleteComment(@RequestParam("commentId") int commentId) {
        commentService.deleteComment(commentId);
        return "redirect:/blog/getposts";
    }

}
