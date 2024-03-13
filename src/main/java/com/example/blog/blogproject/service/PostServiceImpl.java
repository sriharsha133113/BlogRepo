package com.example.blog.blogproject.service;

import com.example.blog.blogproject.dao.PostRepository;
import com.example.blog.blogproject.entity.Post;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PostServiceImpl implements  PostService {

    private PostRepository postRepository;

    @Autowired
    public PostServiceImpl(PostRepository thePostRepository) {
        postRepository = thePostRepository;
    }

    @Override
    @Transactional
    public void save(Post thePost) {

        postRepository.save(thePost);
    }
}
