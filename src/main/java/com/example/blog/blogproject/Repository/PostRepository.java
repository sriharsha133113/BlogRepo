package com.example.blog.blogproject.Repository;

import com.example.blog.blogproject.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
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


//    @Query("SELECT p FROM Post p JOIN p.author a JOIN p.tags t WHERE " +
//            "(:query IS NULL OR p.title LIKE %:query% OR p.content LIKE %:query% OR t.name LIKE %:query%) " +
//            "AND (:authors IS NULL OR a.name IN :authors) AND (:tags IS NULL OR t.name IN :tags)" +  "AND (:startDateTime IS NULL OR p.publishedAt >= :startDateTime) AND (:endDateTime IS NULL OR p.publishedAt <= :endDateTime)" + "ORDER BY CASE WHEN :sortType = 'latest' THEN p.publishedAt END DESC, CASE WHEN :sortType = 'oldest' THEN p.publishedAt END ASC")
//    List<Post> filterAndSearchPosts(@Param("query") String query, @Param("authors") List<String> authors, @Param("tags") List<String> tags, @Param("startDateTime") LocalDateTime startDateTime, @Param("endDateTime") LocalDateTime endDateTime, @Param("sortType") String sortType);

//    @Query("SELECT p FROM Post p JOIN p.author a JOIN p.tags t WHERE " +
//            "(:query IS NULL OR p.title LIKE %:query% OR p.content LIKE %:query% OR t.name LIKE %:query%) " +
//            "AND (:authors IS NULL OR a.name IN :authors) AND (:tags IS NULL OR t.name IN :tags)" +  "AND (:startDateTime IS NULL OR p.publishedAt >= :startDateTime) AND (:endDateTime IS NULL OR p.publishedAt <= :endDateTime)" + "ORDER BY CASE WHEN :sort = 'latest' THEN p.publishedAt  END DESC, CASE WHEN :sort = 'oldest' THEN p.publishedAt END ASC")
//    Page<Post> filterAndSearchPosts(@Param("query") String query, @Param("authors") List<String> authors, @Param("tags") List<String> tags, @Param("startDateTime") LocalDateTime startDateTime, @Param("endDateTime") LocalDateTime endDateTime, @Param("sort") String sort,Pageable pageable);

    @Query("SELECT p FROM Post p JOIN p.author a JOIN p.tags t WHERE " +
            "(:query IS NULL OR p.title LIKE %:query% OR p.content LIKE %:query% OR t.name LIKE %:query%) " +
            "AND (:authors IS NULL OR a.name IN :authors) AND (:tags IS NULL OR t.name IN :tags)" +
            "AND (:startDateTime IS NULL OR p.publishedAt >= :startDateTime) AND (:endDateTime IS NULL OR p.publishedAt <= :endDateTime)")
    Page<Post> filterAndSearchPosts(@Param("query") String query, @Param("authors") List<String> authors,
                                    @Param("tags") List<String> tags, @Param("startDateTime") LocalDateTime startDateTime,
                                    @Param("endDateTime") LocalDateTime endDateTime, Pageable pageable);



}
