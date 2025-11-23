package com.example.social_network.Mapper;

import com.example.social_network.Dto.request.UserCreateRequest;
import com.example.social_network.Dto.request.UserUpdateRequest;
import com.example.social_network.Dto.response.UserResponse;
import com.example.social_network.Entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "roles", ignore = true)
    User toUser(UserCreateRequest request);

    @Mapping(target = "fullName", expression = "java(user.getFullName())")
    UserResponse toUserResponse(User user);

    @Mapping(target = "roles", ignore = true)
    void updateUser(@MappingTarget User user, UserUpdateRequest userUpdateRequest);

}
