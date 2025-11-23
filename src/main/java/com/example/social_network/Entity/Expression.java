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
public class Expression {
    @Id
    String experssionId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    User user;

    @ManyToOne
    Post post;

    @ManyToOne
    Comment comment;

    LocalDateTime timeExperssion;

}
