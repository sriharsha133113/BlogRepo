package com.example.blog.blogproject.entity;

import com.example.blog.blogproject.service.TagService;
import jakarta.persistence.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Entity
@Table(name = "posts")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "title")
    private String title;

    @Column(name = "excerpt")
    private String excerpt;

    @Column(name = "content")
    private String content;

    @Column(name = "is_published")
    private boolean isPublished;

    @Column(name = "published_at")
    private LocalDateTime publishedAt;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY,cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
    @JoinColumn(name = "author_id")
    private User author ;

    @ManyToMany(fetch =FetchType.LAZY,cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
    @JoinTable(name = "post_tags",
            joinColumns = @JoinColumn(name = "post_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id"))
    private List<Tags> tags;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "post_id")
    private List<Comments> comments;

    public Post() {
    }

    public Post(String title, String excerpt, String content, boolean isPublished, LocalDateTime publishedAt, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.title = title;
        this.excerpt = excerpt;
        this.content = content;
        this.isPublished = isPublished;
        this.publishedAt = publishedAt;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getExcerpt() {
        return excerpt;
    }

    public void setExcerpt(String excerpt) {
        this.excerpt = excerpt;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Boolean isPublished() {
        return isPublished;
    }

    public void setPublished(boolean published) {
        isPublished = published;
    }

    public LocalDateTime getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(LocalDateTime publishedAt) {
        this.publishedAt = publishedAt;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User authors) {

        this.author = authors;
    }

    public List<Tags> getNormalTags() {
        return tags;
    }

    public String getTags() {
        StringBuilder tagsString = new StringBuilder();
        if(this.tags==null){
            return null;
        }
        for (Tags tag : this.tags) {
            tagsString.append(tag.getName()).append(",");
        }
        if (tagsString.length() > 0) {
            tagsString.deleteCharAt(tagsString.length() - 1);
        }
        return tagsString.toString();

    }

    public void setTags(String tags) {
        List<String> tagNames = Arrays.asList(tags.split(","));
        List<Tags> tag = new ArrayList<>();
        for (String tagName : tagNames) {

            Tags newTag = new Tags(tagName, LocalDateTime.now(), LocalDateTime.now());
            tag.add(newTag);
        }
        this.tags = tag;
    }

    public void setTagsList(List<Tags> tags)
    {
        this.tags = tags;
    }

    public void addComment(Comments comment)
    {
        if(comments==null)
        {
            comments = new ArrayList<>();
        }
        comments.add(comment);
    }


    public List<Comments> getComments() {
        return comments;
    }

    public void setComments(List<Comments> comments) {
        this.comments = comments;
    }

    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", excerpt='" + excerpt + '\'' +
                ", content='" + content + '\'' +
                ", isPublished=" + isPublished +
                ", publishedAt=" + publishedAt +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", author=" + author +
                ", tags=" + tags +
                ", comments=" + comments +
                '}';
    }
}
