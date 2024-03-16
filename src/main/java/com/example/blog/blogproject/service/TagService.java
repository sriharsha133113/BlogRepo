package com.example.blog.blogproject.service;

import com.example.blog.blogproject.entity.Tags;

import java.util.Optional;

public interface TagService {
//    Tags findByName(String name);

    void saveTag(Tags tag);

    Optional<Tags> findByName(String name);

}
