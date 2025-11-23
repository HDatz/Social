package com.example.social_network.Repository;

import com.example.social_network.Entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PermissionReponsitory extends JpaRepository<Permission, String> {
}
