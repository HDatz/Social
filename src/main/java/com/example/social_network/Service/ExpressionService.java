package com.example.social_network.Service;

import com.example.social_network.Dto.request.ExpressionCreateRequest;
import com.example.social_network.Dto.response.ExpressionResponse;
import com.example.social_network.Exception.AppException;
import com.example.social_network.Exception.ErrorCode;
import com.example.social_network.Mapper.ExpressionMapper;
import com.example.social_network.Repository.*;
import com.example.social_network.Security.SecurityUtils;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ExpressionService {

    ExpressionRepository expressionRepository;
    ExpressionMapper expressionMapper;
    UserReponsitory userReponsitory;
    CommentRepository commentRepository;
    PostReponsitory postReponsitory;
    ExpressionTypeRepository expressionTypeRepository;

    public ExpressionResponse createExpression(ExpressionCreateRequest request){
        String userId = SecurityUtils.getCurrentUserId();

        var user = userReponsitory.findById(userId)
                .orElseThrow(() -> new AppException(ErrorCode.UNAUTHENTICATED));

        var expression = expressionMapper.toExpression(request);
        expression.setUser(user);

        // post expression
        if (request.getPostId() != null && !request.getPostId().isBlank()) {

            if (expressionRepository.existsByUser_UserIdAndPost_PostId(userId, request.getPostId())) {
                throw new AppException(ErrorCode.EXPRESSION_TYPE_NOT_FOUND);
            }

            var post = postReponsitory.findById(request.getPostId())
                    .orElseThrow(() -> new AppException(ErrorCode.POST_NOT_FOUND));
            expression.setPost(post);
        }
        // comment expression
        else if (request.getCommentId() != null && !request.getCommentId().isBlank()) {

            if (expressionRepository.existsByUser_UserIdAndComment_CommentId(userId, request.getCommentId())) {
                throw new AppException(ErrorCode.EXPRESSION_TYPE_NOT_FOUND);
            }

            var comment = commentRepository.findById(request.getCommentId())
                    .orElseThrow(() -> new AppException(ErrorCode.COMMENT_NOT_FOUND));
            expression.setComment(comment);
        }
        else {
            throw new AppException(ErrorCode.INVALID_EXPRESSION);
        }

        var type = expressionTypeRepository.findById(request.getExpressionName())
                .orElseThrow(() -> new AppException(ErrorCode.EXPRESSION_TYPE_NOT_FOUND));

        expression.setExpressionType(type);

        var saved = expressionRepository.save(expression);

        return expressionMapper.toResponse(saved);
    }

}
