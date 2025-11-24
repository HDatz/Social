package com.example.social_network.Dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ExpressionResponse {
    String expressionId;

    String expressionName;

    String icon;

    String userId;

    String fullName;

    String postId;

    String commentId;
}
