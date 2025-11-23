package com.example.social_network.Mapper;

import com.example.social_network.Dto.request.PermissionRequest;
import com.example.social_network.Dto.response.PermissionResponse;
import com.example.social_network.Entity.Permission;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PermissionMapper {
    Permission toPermission(PermissionRequest request);

    PermissionResponse toPermissionResponse(Permission permission);
}
