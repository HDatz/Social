package com.example.social_network.Repository;

import com.example.social_network.Entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface CommentRepository extends JpaRepository<Comment, String> {
   List<Comment> findByPost_PostIdAndParentCommentIsNullOrderByTimeCommentAsc(String postId);

   int countByPost_PostId(String postId);

}
