package com.example.social_network.Mapper;

import com.example.social_network.Dto.request.ExpressionCreateRequest;
import com.example.social_network.Dto.response.ExpressionResponse;
import com.example.social_network.Entity.Expression;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ExpressionMapper {
    @Mapping(source = "expressionType.expressionName", target = "expressionName" )
    @Mapping(source = "expressionType.icon", target = "icon")
    @Mapping(source = "user.userId", target = "userId")
    @Mapping(target = "fullName", expression = "java(expression.getUser().getFullName())")
    @Mapping(source = "post.postId", target = "postId")
    @Mapping(source = "comment.commentId", target = "commentId")
    ExpressionResponse toResponse(Expression expression);

    @Mapping(target = "expressionId", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "post", ignore = true)
    @Mapping(target = "comment", ignore = true)
    @Mapping(target = "expressionType", ignore = true)
    @Mapping(target = "timeExpression", ignore = true)
    Expression toExpression(ExpressionCreateRequest request);
}
