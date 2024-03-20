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

    @Override
    public List<Post> searchPostsByAuthor(String query) {
        return postRepository.findByAuthorName(query);
    }

    @Override
    public List<Post> getPostsByAuthors(List<Integer> authorIds) {
        return postRepository.findByAuthorIdIn(authorIds);
    }

    @Override
    public List<Post> getPostsByTags(List<Integer> tagIds) {
        return postRepository.findByTagsIdIn(tagIds);
    }

    @Override
    public List<Post> findByTagsAndAuthors(List<String> selectedTags, List<String> selectedAuthors) {
        return postRepository.findByTagsInAndAuthorIn(selectedTags, selectedAuthors);
    }


    @Override
    public List<Post> searchPostsByAuthorsAndTags(List<String> authors, List<String> tags) {
        if (authors == null && tags == null) {
            return postRepository.findAll();
        } else if (authors != null && tags != null) {
            return postRepository.findByAuthorNameInAndTagsNameIn(authors, tags);
        } else if (authors != null) {
            return postRepository.findByAuthorNameIn(authors);
        } else {
            return postRepository.findByTagsNameIn(tags);
        }
    }

    @Override
    public List<Post> filterAndSearchPosts(String query, List<String> authors, List<String> tags, LocalDateTime startDateTime, LocalDateTime endDateTime) {
        return postRepository.filterAndSearchPosts(query, authors, tags,startDateTime,endDateTime);
    }


}
