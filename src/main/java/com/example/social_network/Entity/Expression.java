package com.example.social_network.Entity;


import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "expression",
        uniqueConstraints = {
            @UniqueConstraint(columnNames = {"user_id", "post_id"}),
            @UniqueConstraint(columnNames = {"user_id", "comment_id"})
        },indexes = {
            @Index(columnList = "post_id"),
            @Index(columnList = "comment_id"),
            @Index(columnList = "expressionName")
        })
public class Expression {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String expressionId;

    @Column(nullable = false)
    String expressionName;

    String description;
    String icon;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    User user;

    @ManyToOne
    @JoinColumn(name = "post_id")
    Post post;

    @ManyToOne
    @JoinColumn(name = "comment_id")
    Comment comment;

    LocalDateTime timeExpression;

    @PrePersist
    protected  void onCreate(){
        timeExpression = LocalDateTime.now();
    }

}
