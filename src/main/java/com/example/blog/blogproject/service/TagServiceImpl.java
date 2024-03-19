package com.example.blog.blogproject.service;

import com.example.blog.blogproject.Repository.TagRepository;
import com.example.blog.blogproject.entity.Tags;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TagServiceImpl implements TagService{

    private TagRepository tagRepository;
    @Autowired
    public TagServiceImpl(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }


    @Override
    public Optional<Tags> findByName(String name) {
        return tagRepository.findByName(name);
    }

    @Override
    public List<Tags> getAllTags() {
        return tagRepository.findAll();
    }

    @Override
    public void saveTag(Tags tag) {
        tagRepository.save(tag);
    }


}
