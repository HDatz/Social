package com.example.social_network.Controller;

import com.example.social_network.Dto.request.RoleRequest;
import com.example.social_network.Dto.response.ApiResponse;
import com.example.social_network.Dto.response.RoleResponse;
import com.example.social_network.Service.RoleService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/roles")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class RoleController {
    RoleService roleService;

    @PostMapping
    ApiResponse<RoleResponse> create(@RequestBody RoleRequest request){
        return ApiResponse.<RoleResponse>builder()
                .result(roleService.create(request))
                .build();
    }

    @GetMapping
    ApiResponse<List<RoleResponse>> getAllRole(){
        return ApiResponse.<List<RoleResponse>>builder()
                .result(roleService.getAll())
                .build();
    }

    @DeleteMapping("/{roleName}")
    ApiResponse<Void> delete(@PathVariable String role){
        roleService.deleteRole(role);
        return ApiResponse.<Void>builder()
                .message("User deleted success")
                .build();
    }
}
