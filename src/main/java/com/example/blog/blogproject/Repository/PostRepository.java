package com.example.blog.blogproject.Repository;

import com.example.blog.blogproject.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Integer> {
    List<Post> findAllByOrderByPublishedAtDesc();

    List<Post> findAllByOrderByPublishedAtAsc();

    List<Post> findByTitleContainingOrContentContainingOrTagsNameContaining(String title, String content, String tagName);

    List<Post> findByAuthorIdIn(List<Integer> authorIds);
    List<Post> findByTagsIdIn(List<Integer> tagIds);

    List<Post> findByAuthorName(String authorName);


    List<Post> findByTagsInAndAuthorIn(List<String> selectedTags, List<String> selectedAuthors);

    List<Post> findByAuthorNameInAndTagsNameIn(List<String> authors, List<String> tags);

    List<Post> findByAuthorNameIn(List<String> authors);

    List<Post> findByTagsNameIn(List<String> tags);
}
