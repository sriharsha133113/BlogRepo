package com.example.blog.blogproject.rest;

import com.example.blog.blogproject.Repository.UserRepository;
import com.example.blog.blogproject.entity.Comments;
import com.example.blog.blogproject.entity.Post;
import com.example.blog.blogproject.service.CommentService;
import com.example.blog.blogproject.service.PostService;
import com.example.blog.blogproject.service.TagService;
import com.example.blog.blogproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api")
public class CommentRestController {

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

    @GetMapping("/comments")
    public ResponseEntity<List<Comments>> showAllComments(){
        return new ResponseEntity<>(commentService.findAll(), HttpStatus.OK);
    }


    @GetMapping("/comments/{postId}")
    public ResponseEntity<List<Comments>> showAllCommentsByPostId(@PathVariable("postId") Integer postId){

        Post post = postService.findById(postId);

        if(post == null){
            return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
        }

        return  new ResponseEntity<>(post.getComments(),HttpStatus.OK);
    }


    @PostMapping("/comments/{postId}")
    public ResponseEntity<String> saveNewComment(@PathVariable("postId") Integer postId,
                                                 @RequestBody Comments comment){

        Post currentPost = postService.findById(postId);

        if( currentPost == null){
            return new ResponseEntity<>("Invalid Post id",HttpStatus.BAD_REQUEST);
        }

        Comments currentComment = new Comments();
        currentComment.setComment(comment.getComment());
        currentComment.setName(currentPost.getAuthor().getName());
        currentComment.setEmail(currentPost.getAuthor().getEmail());
        currentComment.setCreatedAt(LocalDateTime.now());
        currentPost.addComment(currentComment);
        postService.save(currentPost);
        return new ResponseEntity<>("Comment saved successfully", HttpStatus.CREATED);
    }

    @PutMapping("/comments/{postId}/{commentId}")
    public ResponseEntity<String> updateCommentById(@PathVariable("postId") Integer postId,
                                                    @PathVariable("commentId") Integer commentId,
                                                    @RequestBody Comments comment){

        Post post = postService.findById(postId);
        Comments oldComment = commentService.findById(commentId);

        if(post==null || oldComment == null){
            return new ResponseEntity<>("Invalid Request",HttpStatus.BAD_REQUEST);
        }

        oldComment.setUpdatedAt(LocalDateTime.now());
        oldComment.setComment(comment.getComment());
        commentService.save(oldComment);
        return new ResponseEntity<>("Comment updated successfully",HttpStatus.OK);
    }

    @DeleteMapping("/comments/{postId}/{commentId}")
    public ResponseEntity<String> deleteCommentById(@PathVariable("postId") Integer postId,
                                                    @PathVariable("commentId") Integer commentId){

        if(postService.findById(postId) == null){
            return new ResponseEntity<>("Invalid Post Id",HttpStatus.BAD_REQUEST);
        }

        if(commentService.findById(commentId) == null){
            return new ResponseEntity<>("Invalid Comment Id",HttpStatus.BAD_REQUEST);
        }
            commentService.deleteComment(commentId);
            return new ResponseEntity<>("Comment deleted successfully",HttpStatus.OK);

    }

}
