package com.example.social_network.Dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PostResponse {
    String postId;

    String imagePost;

    String content;

    LocalDateTime timePost;

    LocalDateTime timeUpdatePost;

    String userId;
    String fullName;

    int totalComment;
}
