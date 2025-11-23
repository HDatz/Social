package com.example.social_network.Controller;

import com.example.social_network.Dto.request.CommentCreateRequest;
import com.example.social_network.Dto.request.CommentUpdateRequest;
import com.example.social_network.Dto.response.ApiResponse;
import com.example.social_network.Dto.response.CommentResponse;
import com.example.social_network.Service.CommentService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comment")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CommentController {
    CommentService commentService;

    @PostMapping
    ApiResponse<CommentResponse> createComment(@RequestBody CommentCreateRequest request){
        return ApiResponse.<CommentResponse>builder()
                .result(commentService.createComment(request))
                .build();
    }

    //-------- lay comment trong 1 bai post-------
    @GetMapping("/{postId}")
    ApiResponse<List<CommentResponse>> getCommentOfPost(@PathVariable  String postId){
       return ApiResponse.<List<CommentResponse>>builder()
               .result(commentService.getCommentsByPost(postId))
               .build();
    }

    @PutMapping("/{commentId}")
    ApiResponse<CommentResponse>  updateComment(@PathVariable String commentId, @RequestBody CommentUpdateRequest request){
        var result = commentService.updateComment(commentId, request);
        return  ApiResponse.<CommentResponse>builder()
                .result(result)
                .build();

    }
    @DeleteMapping("/{commentId}")
    ApiResponse<String> deleteComment(@PathVariable String commentId){
        commentService.deleteComment(commentId);
        return ApiResponse.<String>builder()
                .result("Delete Comment Success")
                .build();

    }
}
