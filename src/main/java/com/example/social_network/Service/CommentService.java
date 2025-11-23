package com.example.social_network.Service;

import com.example.social_network.Dto.request.CommentCreateRequest;
import com.example.social_network.Dto.request.CommentUpdateRequest;
import com.example.social_network.Dto.response.CommentResponse;
import com.example.social_network.Dto.response.UserResponse;
import com.example.social_network.Entity.Comment;
import com.example.social_network.Exception.AppException;
import com.example.social_network.Exception.ErrorCode;
import com.example.social_network.Mapper.CommentMapper;
import com.example.social_network.Repository.CommentRepository;
import com.example.social_network.Repository.PostReponsitory;
import com.example.social_network.Repository.UserReponsitory;
import com.example.social_network.Security.SecurityUtils;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CommentService {
    CommentRepository commentRepository;
    CommentMapper commentMapper;
    UserReponsitory userReponsitory;
    PostReponsitory postReponsitory;


    public CommentResponse createComment(CommentCreateRequest request){
        var comment = commentMapper.toComment(request);

        String userId = SecurityUtils.getCurrentUserId();

        var user = userReponsitory.findById(userId)
                        .orElseThrow(() -> new AppException(ErrorCode.UNAUTHENTICATED));
        comment.setUser(user);

        var post =postReponsitory.findById(request.getPostId())
                .orElseThrow(() -> new AppException(ErrorCode.POST_DON_EXIST));

        comment.setPost(post);

        if(request.getParentCommentId() != null && !request.getParentCommentId().isBlank()){

            var parent = commentRepository.findById(request.getParentCommentId())
                            .orElseThrow(() -> new AppException(ErrorCode.COMMENT_NOT_FOUND));

            comment.setParentComment(parent);

            if(parent.getReplies() == null){
                parent.setReplies(new ArrayList<>());
            }
            parent.getReplies().add(comment);
        }

        Comment saved = commentRepository.save(comment);

        return commentMapper.toCommentResponse(saved);
    }

    public List<CommentResponse> getCommentsByPost(String postId){
        List<Comment> rootComments = commentRepository
                .findByPost_PostIdAndParentCommentIsNullOrderByTimeCommentAsc(postId);

        return rootComments.stream().map(commentMapper::toCommentResponse).toList();
    }

    @PreAuthorize("@commentSecurity.canUpdate(#commentId)")
    public CommentResponse updateComment(String commentId, CommentUpdateRequest request){
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new AppException(ErrorCode.COMMENT_DONT_EXIST));
        commentMapper.updateComment(comment, request);
        return commentMapper.toCommentResponse(commentRepository.save(comment));
    }

    @PreAuthorize("@commentSecurity.canDelete(#commentId)")
    public void deleteComment(String commentId){
        commentRepository.deleteById(commentId);
    }

}
