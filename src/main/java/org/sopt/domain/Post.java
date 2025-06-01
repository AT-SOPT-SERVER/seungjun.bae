package org.sopt.domain;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
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

    @Column(name="post_time", nullable = false)
    private LocalDateTime postTime;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments;

    public Post(){
    }

    public Post(String title, String body, User user) {
        this.title = title;
        this.body = body;
        this.user = user;
    }

    @PrePersist
    @PreUpdate
    protected void onChange() {
        this.postTime = LocalDateTime.now();
    }

    //댓글 연관관계 편의 메서드
    public void addComment(Comment comment) {
        this.comments.add(comment);
        comment.setPost(this);
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setBody(String body) {
        this.body = body;
    }
}