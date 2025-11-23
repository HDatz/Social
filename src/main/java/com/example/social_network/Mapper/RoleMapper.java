package com.example.social_network.Mapper;

import com.example.social_network.Dto.request.RoleRequest;
import com.example.social_network.Dto.response.RoleResponse;
import com.example.social_network.Entity.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    @Mapping(target = "permissions", ignore = true)
    Role toRole(RoleRequest request);

    RoleResponse toRoleResponse(Role role);
}
