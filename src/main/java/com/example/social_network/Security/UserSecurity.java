package com.example.social_network.Security;

import com.example.social_network.Exception.AppException;
import com.example.social_network.Exception.ErrorCode;
import com.example.social_network.Repository.UserReponsitory;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component("userSecurity")
@RequiredArgsConstructor
public class UserSecurity {

    private final UserReponsitory userReponsitory;

    public boolean isOwner(String userId){
        var user = userReponsitory.findById(userId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_DON_EXIST));

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(auth == null  || !auth.isAuthenticated()){
            return false;
        }

        String currentUserId = auth.getName();

        return user.getUserId().equals(currentUserId);
    }
}
