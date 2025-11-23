package com.example.social_network.Dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserUpdateRequest {
    String firstName;

    String lastName;

    String password;
    String phoneNumber;
    String email;
    String avatar;
    LocalDate dob;

    List<String> roles;
}
