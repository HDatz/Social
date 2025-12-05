package com.example.social_network.Service;

import com.example.social_network.Dto.request.UserCreateRequest;
import com.example.social_network.Dto.request.UserUpdateRequest;
import com.example.social_network.Dto.response.UserResponse;
import com.example.social_network.Entity.User;
import com.example.social_network.Exception.AppException;
import com.example.social_network.Exception.ErrorCode;
import com.example.social_network.Mapper.UserMapper;
import com.example.social_network.Repository.RoleReponsitory;
import com.example.social_network.Repository.UserReponsitory;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class UserService {

    UserReponsitory userReponsitory;
    UserMapper userMapper;
    PasswordEncoder passwordEncoder;
    RoleReponsitory roleReponsitory;

    public UserResponse createUser(UserCreateRequest request){
        User  user = userMapper.toUser(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        var roles = roleReponsitory.findAllById(request.getRoles());
        user.setRoles(new HashSet<>(roles));

        try{
            user = userReponsitory.save(user);
        }catch (DataIntegrityViolationException exception){
            throw new AppException(ErrorCode.USER_EXISTED);
        }
        return userMapper.toUserResponse(user);
    }

    public List<UserResponse> getUsers(){
        return userReponsitory.findAll().stream().map(userMapper::toUserResponse).toList();
    }

    @PreAuthorize("hasRole('ADMIN')")
    public UserResponse getUser(String id) {
        return userMapper.toUserResponse(
                userReponsitory.findById(id).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND)));
    }

    public List<UserResponse> searchUser(String keyword){
        var byEmail = userReponsitory.findByemail(keyword);
        if(byEmail.isPresent()){
            return List.of(userMapper.toUserResponse(byEmail.get()));
        }

        var byPhone = userReponsitory.findByphoneNumber(keyword);
        if(byPhone.isPresent()){
            return List.of(userMapper.toUserResponse(byPhone.get()));
        }

        var users = userReponsitory.searcbByFullName(keyword);
        return users.stream().map(userMapper::toUserResponse).toList();
    }

    @PreAuthorize("@userSecurity.isOwner(#userId) or hasRole('ADMIN')")
    public UserResponse updateUser(String id, UserUpdateRequest request) {
        User user = userReponsitory.findById(id).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        userMapper.updateUser(user, request);

        if(request.getPassword() != null){
            user.setPassword(passwordEncoder.encode(request.getPassword()));
        }
        var roles = roleReponsitory.findAllById(request.getRoles());
        user.setRoles(new HashSet<>(roles));

        return  userMapper.toUserResponse(userReponsitory.save(user));
    }


    @PreAuthorize("@userSecurity.isOwner(#userId) or hasRole('ADMIN')")
    public void deleteUser(String userId){
        userReponsitory.deleteById(userId);
    }
}
