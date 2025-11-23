package com.example.social_network.Security;

import com.example.social_network.Exception.AppException;
import com.example.social_network.Exception.ErrorCode;
import com.example.social_network.Repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component("commentSecurity")
@RequiredArgsConstructor
public class CommentSecurity {

    private final CommentRepository commentRepository;

   public boolean canUpdate(String commentId){

       Authentication auth = SecurityContextHolder.getContext().getAuthentication();
       if(auth == null || !auth.isAuthenticated()){
           throw new AppException(ErrorCode.UNAUTHENTICATED);
       }

       String currentUserId = auth.getName();

       var comment = commentRepository.findById(commentId)
               .orElseThrow(() -> new AppException(ErrorCode.COMMENT_NOT_FOUND));

       String commentOwnerId = comment.getUser() != null ? comment.getUser().getUserId() : null;

       return  currentUserId.equals(commentOwnerId);
   }

   public boolean canDelete(String commentId){
       Authentication auth = SecurityContextHolder.getContext().getAuthentication();
       if(auth == null || !auth.isAuthenticated()){
           throw  new AppException(ErrorCode.UNAUTHENTICATED);
       }

       String currentUserId = auth.getName();

       var comment = commentRepository.findById(commentId)
               .orElseThrow(() -> new AppException(ErrorCode.COMMENT_NOT_FOUND));

       String commentOwnerId = comment.getUser() != null ? comment.getUser().getUserId() : null;

       String postOwner = (comment.getPost() != null && comment.getPost().getUser() != null )
               ? comment.getPost().getUser().getUserId() : null;

       return  currentUserId.equals(commentOwnerId) || currentUserId.equals(postOwner);

   }

}
