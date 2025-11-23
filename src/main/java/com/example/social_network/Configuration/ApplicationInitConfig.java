package com.example.social_network.Configuration;

import com.example.social_network.Constant.PredefinedRole;
import com.example.social_network.Entity.Role;
import com.example.social_network.Entity.User;
import com.example.social_network.Repository.RoleReponsitory;
import com.example.social_network.Repository.UserReponsitory;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;

@Slf4j
@Configuration
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ApplicationInitConfig {
    PasswordEncoder passwordEncoder;

    @NonFinal
    static final String ADMIN_EMAIL = "dathoang.dev3@gmail.com";

    @NonFinal
    static final String ADMIN_PASSWORD = "admin";

    @Bean
    ApplicationRunner applicationRunner(UserReponsitory userReponsitory, RoleReponsitory roleReponsitory){
        return  args -> {
            if(userReponsitory.findByemail(ADMIN_EMAIL).isEmpty()){
                roleReponsitory.save(Role.builder()
                        .roleName(PredefinedRole.USER_ROLE)
                        .descriptionRole("User role")
                        .build());

                Role adminRole = roleReponsitory.save(Role.builder()
                        .roleName(PredefinedRole.ADMIN_ROLE)
                        .descriptionRole("Admin Role")
                        .build());

                var roles = new HashSet<Role>();
                roles.add(adminRole);

                User user = User.builder()
                        .firstName("Hoang")
                        .lastName("Dat")
                        .email(ADMIN_EMAIL)
                        .password(passwordEncoder.encode(ADMIN_PASSWORD))
                        .roles(roles)
                        .build();
                userReponsitory.save(user);
                log.warn("admin user has been created with default password: admin, please change it");
            }
            log.info("Application initialization completed .....");
        };
    }



}
