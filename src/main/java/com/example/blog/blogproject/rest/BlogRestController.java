package com.example.blog.blogproject.rest;

import com.example.blog.blogproject.Repository.UserRepository;
import com.example.blog.blogproject.entity.*;
import com.example.blog.blogproject.service.CommentService;
import com.example.blog.blogproject.service.PostService;
import com.example.blog.blogproject.service.TagService;
import com.example.blog.blogproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api")
public class BlogRestController {

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

    @PostMapping("/posts")
    public ResponseEntity<String> saveNewPost(@RequestBody Post post){


            if (!post.isPublished()) {
                post.setPublished(true);
                post.setPublishedAt(LocalDateTime.now());
                User theuser = post.getAuthor();
                post.setAuthor(theuser);
            } else {
                post.setUpdatedAt(LocalDateTime.now());
            }
            postService.save(post);
            return new ResponseEntity<>("New Post added successfully", HttpStatus.CREATED);
        }

    @GetMapping("/posts")
    public ResponseEntity<List<Post>> showAllPosts(){

//        PostService postService;
        List<Post> posts= postService.getAllPosts();

        return new ResponseEntity<>(posts, HttpStatus.OK);

    }

    @GetMapping("/posts/{postId}")
    public ResponseEntity<Post> showPostById(@PathVariable("postId") Integer postId){

        Post post = postService.findById(postId);

        if(post==null){
            return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(post,HttpStatus.OK);
    }

    @PutMapping("/posts/{postId}")
    public ResponseEntity<String> updatePostById(@PathVariable("postId") Integer postId,
                                                         @RequestBody Post post)
    {

        Post postById = postService.findById(postId);

        if( postById == null){
            return new ResponseEntity<>("Invalid Post id,Please Give correct post ID",HttpStatus.BAD_REQUEST);
        } else{
            postById.setTitle(post.getTitle());
            postById.setContent(post.getContent());
           postById.setTagsList(post.getNormalTags());
            postById.setUpdatedAt(LocalDateTime.now());
            postService.save(postById);
            return new ResponseEntity<>("Post updated successfully",HttpStatus.OK);

        }

    }

    @DeleteMapping("/posts/{postId}")
    public ResponseEntity<String> deleteExistingPostById(@AuthenticationPrincipal UserDetails userDetails,
                                                         @PathVariable("postId") Integer postId){


        Post postById = postService.findById(postId);

        if( postById == null){
            return new ResponseEntity<>("Invalid Post id,Please Give Correct PostId",HttpStatus.BAD_REQUEST);
        }

        boolean isAuthorized = postService.isUserAuthorized(userDetails, postId);

        if(isAuthorized){
            postService.deletePost(postById);
            return new ResponseEntity<>("Post deleted successfully",HttpStatus.OK);
        }

        return new ResponseEntity<>("Access Denied",HttpStatus.UNAUTHORIZED);
    }

}
