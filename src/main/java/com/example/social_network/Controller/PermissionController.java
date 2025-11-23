package com.example.social_network.Controller;

import com.example.social_network.Dto.request.PermissionRequest;
import com.example.social_network.Dto.response.ApiResponse;
import com.example.social_network.Dto.response.PermissionResponse;
import com.example.social_network.Service.PermissionService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/permission")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PermissionController {

    PermissionService permissionService;
    @PostMapping
    ApiResponse<PermissionResponse> create(@RequestBody PermissionRequest request){
        return ApiResponse.<PermissionResponse>builder()
                .result(permissionService.create(request))
                .build();
    }

    @GetMapping
    ApiResponse<List<PermissionResponse>> getAllPermission(){
        return ApiResponse.<List<PermissionResponse>>builder()
                .result(permissionService.getAll())
                .build();
    }

    @DeleteMapping("/{permissionName}")
    ApiResponse<Void> delete(@PathVariable("permissionName") String permission){
        permissionService.deletePermission(permission);
        return ApiResponse.<Void>builder()
                .message("Permission delete success")
                .build();
    }

}
