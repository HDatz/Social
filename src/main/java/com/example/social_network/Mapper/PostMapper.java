package com.example.social_network.Mapper;

import com.example.social_network.Dto.request.PostCreateRequest;
import com.example.social_network.Dto.request.PostUpdateRequest;
import com.example.social_network.Dto.response.PostResponse;
import com.example.social_network.Entity.Post;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface PostMapper {

    Post toPost(PostCreateRequest request);

    @Mapping(source = "user.userId", target = "userId")
    @Mapping(expression = "java(post.getUser().getFullName())", target = "fullName")
    PostResponse toPostResponse(Post post);

    void updatePost(@MappingTarget Post post, PostUpdateRequest request);
}
