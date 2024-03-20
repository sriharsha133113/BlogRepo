package com.example.blog.blogproject.controller;

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

    @GetMapping("/createpost")
    public  String createPost(Model theModel){
        Post thepost=new Post();
        thepost.setCreatedAt(LocalDateTime.now());
        theModel.addAttribute("post",thepost);
        return "create_post";

    }

    @PostMapping("/savepost")
    public String savePost(@ModelAttribute("post") Post post) {
        List<Tags> tag = postService.checkedForTags(post.getNormalTags());
        post.setTagsList(tag);

        if (!post.isPublished()) {
            post.setPublished(true);
            post.setPublishedAt(LocalDateTime.now());
            User theuser = new User("user1", "email1", "password1");
            post.setAuthor(theuser);
        } else {

            post.setUpdatedAt(LocalDateTime.now());
        }
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
        List<Tags> tags = tagService.getAllTags();
        Set<String> uniqueAuthorNames = new HashSet<>();
        List<User> authors = userService.getAllUsers();
        for (User author : authors) {
            uniqueAuthorNames.add(author.getName());
        }
        List<String> authorNames = new ArrayList<>(uniqueAuthorNames);
//        List<User> authors = userService.getAllUsers();
        model.addAttribute("posts", posts);
        model.addAttribute("authors", authorNames);
        model.addAttribute("tags", tags);

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
        return "redirect:/blog/getposts";
    }

    @GetMapping("/deleteForm")
    public String deleteForms(@RequestParam("formId") int theId,Model themodel){

        Post thepost = postService.findById(theId);
        System.out.println("post delete form"+theId);
        postService.deletePost(thepost);
        return "redirect:/blog/getposts";

    }


    @GetMapping("/sort")
    public String sortPosts(@RequestParam("sort") String sortType, @RequestParam("query") String query, Model model) {
        List<Post> filteredPosts = postService.searchPosts(query);
        if ("latest".equals(sortType)) {
            Collections.sort(filteredPosts, Comparator.comparing(Post::getPublishedAt).reversed());
        } else if ("oldest".equals(sortType)) {
            Collections.sort(filteredPosts, Comparator.comparing(Post::getPublishedAt));
        }
        model.addAttribute("posts", filteredPosts);

        List<Tags> tags = tagService.getAllTags();
        Set<String> uniqueAuthorNames = new HashSet<>();
        List<User> authors = userService.getAllUsers();
        for (User author : authors) {
            uniqueAuthorNames.add(author.getName());
        }
        List<String> authorNames = new ArrayList<>(uniqueAuthorNames);
        model.addAttribute("authors", authorNames);
        model.addAttribute("tags", tags);

        return "get_posts";
    }

    @PostMapping("/search")
    public String searchPosts(@RequestParam("query") String query, Model model) {
        List<Post> searchResults = postService.searchPosts(query);
        model.addAttribute("posts", searchResults);

        List<Tags> tags = tagService.getAllTags();
        Set<String> uniqueAuthorNames = new HashSet<>();
        List<User> authors = userService.getAllUsers();
        for (User author : authors) {
            uniqueAuthorNames.add(author.getName());
        }
        List<String> authorNames = new ArrayList<>(uniqueAuthorNames);
        model.addAttribute("authors", authorNames);
        model.addAttribute("tags", tags);

        return "get_posts";
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

    @GetMapping("/updateComment")
    public String updateComment(@RequestParam("commentId") int theId,Model themodel){

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
        return "redirect:/blog/getposts";
    }

    @GetMapping("/deleteComment")
    public String deleteComment(@RequestParam("commentId") int commentId) {
        commentService.deleteComment(commentId);
        return "redirect:/blog/getposts";
    }


@PostMapping("/filter")
public String filterAndSearchPosts(@RequestParam(value = "query", required = false) String query,
                                   @RequestParam(value = "author", required = false) List<String> selectedAuthors,
                                   @RequestParam(value = "taglist", required = false) List<String> selectedTags,
                                   @RequestParam(value = "startDateTime", required = false) LocalDateTime startDateTime,
                                   @RequestParam(value = "endDateTime", required = false) LocalDateTime endDateTime,
                                   Model model) {

    List<Post> filteredPosts = postService.filterAndSearchPosts(query, selectedAuthors, selectedTags, startDateTime, endDateTime);

    List<Tags> tags = tagService.getAllTags();
        Set<String> uniqueAuthorNames = new HashSet<>();
        List<User> authors = userService.getAllUsers();
        for (User author : authors) {
            uniqueAuthorNames.add(author.getName());
        }
        List<String> authorNames = new ArrayList<>(uniqueAuthorNames);
        model.addAttribute("authors", authorNames);
        model.addAttribute("tags", tags);

    model.addAttribute("posts", filteredPosts);
    return "get_posts";
}

}



