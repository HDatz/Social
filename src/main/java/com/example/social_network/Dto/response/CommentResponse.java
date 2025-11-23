package com.example.social_network.Dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CommentResponse {
    String commentId;

    String content;

    LocalDateTime timeComment;
    LocalDateTime updateComment;

    String postId;

    String userId;
    String fullName;

    String parentCommentId;

    List<CommentResponse> replies;

}
