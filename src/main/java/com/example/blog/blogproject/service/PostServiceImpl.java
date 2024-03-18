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

            throw new RuntimeException("Did not find employee id - " + theId);
        }
        return post;
    }

    @Override
    @Transactional
    public void deletePost(Post post) {
        System.out.println("delte serveImpl");
        postRepository.delete(post);

    }

    @Override
    public List<Post> getLatestPosts() {
        return postRepository.findAllByOrderByPublishedAtDesc();
    }

    @Override
    public List<Post> getOldestPosts() {
        return postRepository.findAllByOrderByPublishedAtAsc();
    }

    @Override
    public List<Post> searchPosts(String query) {
        return postRepository.findByTitleContainingOrContentContainingOrTagsNameContaining(query, query, query);
    }

}
