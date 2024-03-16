package com.example.blog.blogproject.service;

import com.example.blog.blogproject.Repository.PostRepository;
import com.example.blog.blogproject.entity.Post;
import com.example.blog.blogproject.entity.Tags;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class PostServiceImpl implements  PostService {



    private PostRepository postRepository;
    private TagService tagService;


    @Autowired
    public PostServiceImpl(PostRepository postRepository, TagService tagService) {
        this.postRepository = postRepository;
        this.tagService = tagService;
    }

    @Override
    @Transactional
    public void save(Post thePost) {

        postRepository.save(thePost);
    }


    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

//    public void setTags(Post post, String tags) {
//        List<String> tagNames = Arrays.asList(tags.split(","));
//        List<Tags> tagList = new ArrayList<>();
//
//        for (String tagName : tagNames) {
//            Tags existingTag = tagService.findByName(tagName);
//
//            if (existingTag == null) {
//                // Tag already exists, reuse it
////                tagList.add(existingTag);
//                Tags newTag = new Tags(tagName, LocalDateTime.now(), LocalDateTime.now());
//                tagService.saveTag(newTag);
//                tagList.add(newTag);
//            }
////            else {
////                // Tag doesn't exist, create a new one
////                Tags newTag = new Tags(tagName, LocalDateTime.now(), LocalDateTime.now());
////                tagService.saveTag(newTag);
////                tagList.add(newTag);
////            }
//        }
//
//        post.setTags(tagList.toString());
//    }


    public void checkForTags(List<Tags> tags)
    {
        for(Tags tag:tags) {
            Optional<Tags> tagName = tagService.findByName(tag.getName());
            if (!tagName.isEmpty()) {
                tags.remove(tag);
                tags.add(tagName.get());
            }
        }
    }

    public List<Tags> checkedForTags(List<Tags> tags) {
        List<Tags> newTags = new ArrayList<>();
        for (Tags tag : tags) {
            Optional<Tags> tagName = tagService.findByName(tag.getName());
            if (!tagName.isEmpty()) {
                newTags.add(tagName.get());
            } else {
                newTags.add(tag);
            }
        }
        return newTags;
    }

    @Override
    public Post findById(int theId) {
        Optional<Post> result = postRepository.findById(theId);
        Post post = null;

        if (result.isPresent()) {
            post = result.get();
        }
        else {
            // we didn't find the employee
            throw new RuntimeException("Did not find employee id - " + theId);
        }
        return post;
//        return postRepository.findById(theId);
    }

//    @Override
//    @Transactional
//    public void deleteById(int theId) {
//        postRepository.deleteById(theId);
//    }


//    @Override
//    @Transactional
//    public void deleteById(Post post) {
//
//            postRepository.deleteById(pos);
//        }
//    }

    @Override
    @Transactional
    public void deletePost(Post post) {
        System.out.println("delte serveImpl");
//        System.out.println(post);
        postRepository.delete(post);

    }

}
