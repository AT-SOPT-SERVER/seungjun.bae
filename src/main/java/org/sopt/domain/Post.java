package org.sopt.domain;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String body;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @CreationTimestamp
    @Column(name="post_time", nullable = false)
    private LocalDateTime postTime;

    public Post(){
    }

    public Post(String title, String body, User user) {
        this.title = title;
        this.body = body;
        this.user = user;
    }

    @PrePersist
    protected void OnCreate(){
        this.postTime = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return this.title;
    }

    public User getUser() {
        return user;
    }

    public String getBody() {
        return body;
    }

    public LocalDateTime getPostTime() {
        return postTime;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}