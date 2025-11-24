package com.example.social_network.Service;

import com.example.social_network.Dto.request.PostCreateRequest;
import com.example.social_network.Dto.request.PostUpdateRequest;
import com.example.social_network.Dto.response.PostResponse;
import com.example.social_network.Entity.Post;
import com.example.social_network.Exception.AppException;
import com.example.social_network.Exception.ErrorCode;
import com.example.social_network.Mapper.PostMapper;
import com.example.social_network.Repository.CommentRepository;
import com.example.social_network.Repository.PostReponsitory;
import com.example.social_network.Repository.UserReponsitory;
import com.example.social_network.Security.SecurityUtils;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PostService {

    PostReponsitory postReponsitory;
    PostMapper postMapper;
    CommentRepository commentRepository;
    UserReponsitory userReponsitory;

    public PostResponse createPost(PostCreateRequest request){
          var post = postMapper.toPost(request);

          String userId = SecurityUtils.getCurrentUserId();

          if(userId == null){
              throw  new AppException(ErrorCode.UNAUTHENTICATED);
          }

          var user = userReponsitory.findById(userId)
                  .orElseThrow(() -> new AppException(ErrorCode.USER_DON_EXIST));

          post.setUser(user);
          post = postReponsitory.save(post);

          PostResponse response = postMapper.toPostResponse(post);

          response.setTotalComment(0);

          return  response;
    }

    public List<PostResponse> getAll(){
        return postReponsitory.findAll().stream().map(
                post -> {
                    PostResponse r = postMapper.toPostResponse(post);
                    int total = commentRepository.countByPost_PostId(post.getPostId());
                    r.setTotalComment(total);
                    return r;
                })
                .toList();
    }

    public PostResponse getPost(String postId){
        Post post= postReponsitory.findById(postId)
                .orElseThrow(() ->new AppException(ErrorCode.POST_DON_EXIST));

        PostResponse response = postMapper.toPostResponse(post);

        int total = commentRepository.countByPost_PostId(postId);
        response.setTotalComment(total);
        return response;
    }

    @PreAuthorize(" @postSecurity.isOwner(#postId)")
    public PostResponse updatePost(String postId, PostUpdateRequest request){
        Post post = postReponsitory.findById(postId).orElseThrow(() -> new AppException(ErrorCode.POST_DON_EXIST));

        postMapper.updatePost(post, request);

        return postMapper.toPostResponse(postReponsitory.save(post));

    }

    @PreAuthorize(
            "hasRole('ADMIN') or @postSecurity.isOwner(#postId)"
    )
    public void deletePost(String postId){
        postReponsitory.deleteById(postId);
    }



}
