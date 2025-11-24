package com.example.social_network.Repository;

import com.example.social_network.Entity.ExpressionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExpressionTypeRepository extends JpaRepository<ExpressionType, String> {
}
