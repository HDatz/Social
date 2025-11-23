package com.example.social_network.Entity;


import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Notify {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String notifyId;

    String content;

    @CreationTimestamp
    LocalDateTime createAt;

    @ManyToOne
    User receiver;

    @ManyToOne
    User sender;

    @ManyToOne
    Post post;

    @ManyToOne
    Comment comment;

}
