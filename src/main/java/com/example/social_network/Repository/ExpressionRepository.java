package com.example.social_network.Repository;

import com.example.social_network.Entity.Expression;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ExpressionRepository extends JpaRepository<Expression, String> {
    Optional<Expression> findByUser_UserIdAndPost_PostId(String userId, String postId);
    Optional<Expression> findByUser_UserIdAndComment_CommentId(String userId, String commentId);

    boolean existsByUser_UserIdAndPost_PostId(String userId, String postId);
    boolean existsByUser_UserIdAndComment_CommentId(String userId, String commentId);
}
