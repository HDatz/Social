package com.example.social_network.Repository;

import com.example.social_network.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserReponsitory extends JpaRepository<User, String> {
    Optional<User> findByemail(String email);
    Optional<User> findByphoneNumber(String phoneNumber);

    @Query("SELECT u FROM User u WHERE LOWER(CONCAT(u.firstName, ' ', u.lastName)) LIKE LOWER(CONCAT('%', :fullName, '%'))")
    List<User> searcbByFullName(@Param("fullName") String fullName);
}
