package com.example.social_network.Dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ExpressionCreateRequest {

    String postId;

    String commentId;

    String expressionName;
}
