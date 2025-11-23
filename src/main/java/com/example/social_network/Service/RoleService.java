package com.example.social_network.Service;

import com.example.social_network.Dto.request.RoleRequest;
import com.example.social_network.Dto.response.RoleResponse;
import com.example.social_network.Mapper.RoleMapper;
import com.example.social_network.Repository.PermissionReponsitory;
import com.example.social_network.Repository.RoleReponsitory;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RoleService {

    RoleReponsitory roleReponsitory;
    PermissionReponsitory permissionReponsitory;
    RoleMapper roleMapper;

    public RoleResponse create(RoleRequest request){
        var role = roleMapper.toRole(request);

        var permissions = permissionReponsitory.findAllById(request.getPermissions());
        role.setPermissions(new HashSet<>(permissions));

        role = roleReponsitory.save(role);
        return  roleMapper.toRoleResponse(role);
    }

    public List<RoleResponse> getAll(){
        return roleReponsitory.findAll().stream().map(roleMapper::toRoleResponse).toList();
    }

    public void deleteRole(String role){
        roleReponsitory.deleteById(role);
    }
}
