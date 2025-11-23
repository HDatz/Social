package com.example.social_network.Dto.response;

import com.example.social_network.Entity.Role;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserResponse {
    String userId;
    String firstName;
    String lastName;

    String fullName;

    String phoneNumber;
    String email;
    String avatar;
    LocalDate dob;
    LocalDateTime createAt;
    LocalDateTime updateAt;
    Set<RoleResponse> roles;
}
