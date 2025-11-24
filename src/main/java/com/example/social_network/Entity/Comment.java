package com.example.social_network.Entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String commentId;

    String content;
    LocalDateTime timeComment;
    LocalDateTime updateComment;


    @ManyToOne
    User user;

    @ManyToOne
    Post post;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    Comment parentComment;

    @OneToMany(mappedBy = "parentComment", cascade = CascadeType.ALL)
    List<Comment> replies;

    @OneToMany(mappedBy = "comment")
    List<Expression> expressions;

    @PrePersist
    protected void ontimeConmment(){
        this.timeComment = LocalDateTime.now();
    }

    @PreUpdate
    protected void onupdateCommen(){
        this.updateComment = LocalDateTime.now();
    }
}
