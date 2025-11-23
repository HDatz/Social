package com.example.social_network.Mapper;

import com.example.social_network.Dto.request.CommentCreateRequest;
import com.example.social_network.Dto.request.CommentUpdateRequest;
import com.example.social_network.Dto.response.CommentResponse;
import com.example.social_network.Entity.Comment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CommentMapper {
    @Mapping(target = "parentComment", ignore = true)
    @Mapping(target = "replies", ignore = true)
    Comment toComment(CommentCreateRequest request);


    @Mapping(source = "user.userId", target = "userId")
    @Mapping(expression = "java(comment.getUser().getFullName())", target = "fullName")
    @Mapping(expression = "java(comment.getPost() != null ? comment.getPost().getPostId() : null)", target = "postId")
    @Mapping(source = "parentComment.commentId", target = "parentCommentId")
    @Mapping(target = "replies",
            expression = "java( comment.getReplies() != null ? toCommentResponses(comment.getReplies()): null)")
    CommentResponse toCommentResponse(Comment comment);

    List<CommentResponse> toCommentResponses(List<Comment> comments);

    void updateComment(@MappingTarget Comment comment, CommentUpdateRequest request);

}
