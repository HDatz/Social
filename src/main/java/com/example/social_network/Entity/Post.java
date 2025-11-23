package com.example.social_network.Entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Entity
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String postId;

    String imagePost;

    String content;

    LocalDateTime timePost;

    LocalDateTime timeUpdatePost;

    @ManyToOne
    User user;

    @OneToMany(mappedBy = "post")
    List<Comment> comments;

    @OneToMany(mappedBy = "post")
    List<Expression> expressions;

    @PrePersist
    protected  void onTimePost(){
        this.timePost = LocalDateTime.now();
    }

    @PreUpdate
    protected  void onUpdatePost(){
        this.timeUpdatePost = LocalDateTime.now();
    }
}
