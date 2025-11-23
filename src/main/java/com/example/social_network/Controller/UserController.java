package com.example.social_network.Controller;

import com.example.social_network.Dto.request.UserCreateRequest;
import com.example.social_network.Dto.request.UserUpdateRequest;
import com.example.social_network.Dto.response.ApiResponse;
import com.example.social_network.Dto.response.UserResponse;
import com.example.social_network.Service.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserController {

    UserService userService;

    @PostMapping
    ApiResponse<UserResponse> createUser(
             @RequestBody UserCreateRequest request){
        return ApiResponse.<UserResponse>builder()
                .result(userService.createUser(request))
                .build();
    }

    @GetMapping
    ApiResponse<List<UserResponse>> getUsers(){
        return ApiResponse.<List<UserResponse>>builder()
                .result(userService.getUsers())
                .build();
    }

    @GetMapping("/{userId}")
    ApiResponse<UserResponse> getUser(@PathVariable("userId") String userId) throws Exception {
        return ApiResponse.<UserResponse>builder()
                .result(userService.getUser(userId))
                .build();
    }

    @GetMapping("/search/{keyword}")
    ApiResponse<List<UserResponse>> searchUser(@PathVariable String keyword){
        var result = userService.searchUser(keyword);
        return ApiResponse.<List<UserResponse>>builder()
                .result(result)
                .build();
    }

    @DeleteMapping("/{userId}")
    ApiResponse<String> delete(@PathVariable String userId){
        userService.deleteUser(userId);
        return ApiResponse.<String>builder()
                .result("User has been deleted")
                .build();
    }

    @PutMapping("/{userId}")
    ApiResponse<UserResponse> updateUser(@PathVariable String userId, @RequestBody UserUpdateRequest request)
            throws Exception {
        return ApiResponse.<UserResponse>builder()
                .result(userService.updateUser(userId,request))
                .build();
    }
}
