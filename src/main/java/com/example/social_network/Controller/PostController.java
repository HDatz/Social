package com.example.social_network.Controller;

import com.example.social_network.Dto.request.PostCreateRequest;
import com.example.social_network.Dto.request.PostUpdateRequest;
import com.example.social_network.Dto.response.ApiResponse;
import com.example.social_network.Dto.response.PostResponse;
import com.example.social_network.Service.PostService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)

public class PostController {

    PostService postService;

    @PostMapping
    ApiResponse<PostResponse> createPost(@RequestBody PostCreateRequest request){
        return ApiResponse.<PostResponse>builder()
                .result(postService.createPost(request))
                .build();
    }

    @GetMapping
    ApiResponse<List<PostResponse>> getAllPost(){
        return ApiResponse.<List<PostResponse>>builder()
                .result(postService.getAll())
                .build();
    }

    @GetMapping("/{postId}")
    ApiResponse<PostResponse> getPost(@PathVariable String postId){
        return ApiResponse.<PostResponse>builder()
                .result(postService.getPost(postId))
                .build();
    }


    @PutMapping("/{postId}")
    ApiResponse<PostResponse> updatePost(@PathVariable String postId, @RequestBody PostUpdateRequest request){
        return ApiResponse.<PostResponse>builder()
                .result(postService.updatePost(postId,request))
                .build();
    }

    @DeleteMapping("/{postId}")
    ApiResponse<String> deledePost(@PathVariable String postId){
        postService.deletePost(postId);
        return ApiResponse.<String>builder()
                .result("post has been deleted")
                .build();
    }
}
