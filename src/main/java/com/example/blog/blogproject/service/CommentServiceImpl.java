package com.example.blog.blogproject.service;

import com.example.blog.blogproject.Repository.CommentRepository;
import com.example.blog.blogproject.entity.Comments;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommentServiceImpl implements CommentService {
    private CommentRepository commentRepository;
    @Autowired
    public CommentServiceImpl(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @Override
    @Transactional
    public void deleteComment(int theId) {

        commentRepository.deleteById(theId);
    }

    @Override
    public Comments findById(int theId) {
        Optional<Comments> result = commentRepository.findById(theId);
        Comments comment = null;

        if (result.isPresent()) {
            comment = result.get();
        } else {
            throw new RuntimeException("Did not find comment id - " + theId);
        }

        return comment;
    }

    @Override
    public void save(Comments updatedComment) {
        commentRepository.save(updatedComment);

    }

    @Override
    public List<Comments> findAll() {
        return commentRepository.findAll();
    }

}
