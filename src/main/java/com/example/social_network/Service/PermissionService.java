package com.example.social_network.Service;

import com.example.social_network.Dto.request.PermissionRequest;
import com.example.social_network.Dto.response.PermissionResponse;
import com.example.social_network.Entity.Permission;
import com.example.social_network.Mapper.PermissionMapper;
import com.example.social_network.Repository.PermissionReponsitory;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PermissionService {
    PermissionReponsitory permissionReponsitory;
    PermissionMapper permissionMapper;

    public PermissionResponse create(PermissionRequest request){
        Permission permission = permissionMapper.toPermission(request);
        permission = permissionReponsitory.save(permission);
        return permissionMapper.toPermissionResponse(permission);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public List<PermissionResponse> getAll(){
        var permission = permissionReponsitory.findAll();
        return  permission.stream().map(permissionMapper::toPermissionResponse).toList();
    }

    public void deletePermission(String permission){
        permissionReponsitory.deleteById(permission);
    }

}
