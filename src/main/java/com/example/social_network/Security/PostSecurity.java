package com.example.social_network.Security;

import com.example.social_network.Exception.AppException;
import com.example.social_network.Exception.ErrorCode;
import com.example.social_network.Repository.PostReponsitory;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component("postSecurity")
@RequiredArgsConstructor
public class PostSecurity {

    private final PostReponsitory postReponsitory;

    public boolean isOwner(String postId){
        var userId = SecurityContextHolder.getContext().getAuthentication().getName();

        var post = postReponsitory.findById(postId)
                .orElseThrow(() -> new AppException(ErrorCode.POST_NOT_FOUND));

        if(!post.getUser().getUserId().equals(userId)) {
            throw new AppException(ErrorCode.UNAUTHORIZED);
        }
        return true;
    }
}
