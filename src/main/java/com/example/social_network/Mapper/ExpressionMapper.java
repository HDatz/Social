package com.example.social_network.Mapper;

import com.example.social_network.Dto.response.ExpressionResponse;
import com.example.social_network.Entity.Expression;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ExpressionMapper {
    @Mapping(source = "expressionType.expressionName", target = "expressionName" )
    @Mapping(source = "expressionType.icon", target = "icon")
    @Mapping(source = "user.userId", target = "userId")
    @Mapping(expression = "java(expression.getUser().getFullName())", target = "fullName")
    @Mapping(source = "post.postId", target = "postId")
    @Mapping(source = "comment.commentId", target = "commentId")
    ExpressionResponse toResponse(Expression expression);
}
