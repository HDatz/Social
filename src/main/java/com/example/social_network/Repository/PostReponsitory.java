package com.example.social_network.Repository;

import com.example.social_network.Entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostReponsitory extends JpaRepository<Post, String> {
}
