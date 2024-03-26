package com.example.blog.blogproject.controller;

import com.example.blog.blogproject.Repository.UserRepository;
import com.example.blog.blogproject.entity.Comments;
import com.example.blog.blogproject.entity.Post;
import com.example.blog.blogproject.entity.Tags;
import com.example.blog.blogproject.entity.User;
import com.example.blog.blogproject.service.CommentService;
import com.example.blog.blogproject.service.PostService;
import com.example.blog.blogproject.service.TagService;
import com.example.blog.blogproject.service.UserService;
import org.apache.catalina.Store;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;


@Controller
@RequestMapping("/blog")

public class BlogController {
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

    @GetMapping("/")
    public  String viewHomePage(Model model){

        return  getPostsAndFilter(null,null,null,null,null,null,1,model);

    }

    @GetMapping("/createpost")
    public  String createPost(Model theModel){
        Post thepost=new Post();
        thepost.setCreatedAt(LocalDateTime.now());
        theModel.addAttribute("post",thepost);
        return "create_post";

    }

    @PostMapping("/savepost")
    public String savePost(@ModelAttribute("post") Post post,@AuthenticationPrincipal UserDetails userDetails) {
        List<Tags> tag = postService.checkedForTags(post.getNormalTags());
        post.setTagsList(tag);

        if (!post.isPublished()) {
            post.setPublished(true);
            post.setPublishedAt(LocalDateTime.now());
            User theuser = userRepository.findUserByName(userDetails.getUsername());
            post.setAuthor(theuser);
        } else {

            post.setUpdatedAt(LocalDateTime.now());
        }
        postService.save(post);
        return "redirect:/blog/";
    }

    @GetMapping("/readForm")
    public String readForms(@RequestParam("formId") int theId, Model themodel, @AuthenticationPrincipal UserDetails userDetails){

        Post thepost = postService.findById(theId);
        Comments thecomment = new Comments();
        themodel.addAttribute("comments",thecomment);
        themodel.addAttribute("post",thepost);
        if(userDetails!=null){
            themodel.addAttribute("currentUser",userDetails.getUsername());
        }else{
            themodel.addAttribute("currentUser","null");
        }

        return "personal_post";
    }

    @GetMapping("/updateForm")
    public String updateForms(@RequestParam("formId") int theId,Model themodel){

        Post thepost = postService.findById(theId);
        themodel.addAttribute("post",thepost);
        return "update_post";

    }

    @PostMapping("/updatePost")
    public String updatePost(@ModelAttribute("post") Post updatedPost) {

        Post existingPost = postService.findById(updatedPost.getId());
        existingPost.setTitle(updatedPost.getTitle());
        existingPost.setContent(updatedPost.getContent());
        List<Tags> updatedTags = postService.checkedForTags(updatedPost.getNormalTags());
        existingPost.setTagsList(updatedTags);
        existingPost.setUpdatedAt(LocalDateTime.now());
        postService.save(existingPost);
        return "redirect:/blog/";
    }

    @GetMapping("/deleteForm")
    public String deleteForms(@RequestParam("formId") int theId,Model themodel){

        Post thepost = postService.findById(theId);
        System.out.println("post delete form"+theId);
        postService.deletePost(thepost);
        return "redirect:/blog/";

    }

    @RequestMapping(value = {
            "/filter/{pageNo}"}, method = {RequestMethod.GET})
    public String getPostsAndFilter(@RequestParam(value = "query", required = false) String query,
                                    @RequestParam(value = "author", required = false) List<String> selectedAuthors,
                                    @RequestParam(value = "taglist", required = false) List<String> selectedTags,
                                    @RequestParam(value = "startDateTime", required = false) LocalDateTime startDateTime,
                                    @RequestParam(value = "endDateTime", required = false) LocalDateTime endDateTime,
                                    @RequestParam(value = "sort", defaultValue = "oldest") String sort,
                                    @PathVariable("pageNo") int pageNo,
                                    Model model) {

        int pageSize=2;
        Page<Post> pageposts = null;
        if (query != null || selectedAuthors != null || selectedTags != null || startDateTime != null || endDateTime != null) {

            pageposts = postService.filterAndSearchPosts(query, selectedAuthors, selectedTags, startDateTime, endDateTime, sort, pageNo, pageSize);
        } else {

            pageposts = postService.getAllPosts(pageNo, 10);
        }

        List<Post> posts=pageposts.getContent();

        for (Post post : posts) {
            String content = post.getContent();
            if (content != null && content.length() >= 5) {
                post.setExcerpt(content.substring(0, 5));
            } else {
                post.setExcerpt(content);
            }
        }

        List<Tags> tags = tagService.getAllTags();
        Set<String> uniqueAuthorNames = new HashSet<>();
        List<User> authors = userService.getAllUsers();
        for (User author : authors) {
            uniqueAuthorNames.add(author.getName());
        }
        List<String> authorNames = new ArrayList<>(uniqueAuthorNames);

        model.addAttribute("posts", posts);
        model.addAttribute("authors", authorNames);
        model.addAttribute("tags", tags);
        model.addAttribute("sort",sort);

        model.addAttribute("totalPages", pageposts.getTotalPages());
        model.addAttribute("totalItems", pageposts.getTotalElements());
        model.addAttribute("currentPage", pageNo);

        return "get_posts";
    }


}


