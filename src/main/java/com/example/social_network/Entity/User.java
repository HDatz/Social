package com.example.social_network.Entity;


import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String userId;

    String firstName;
    String lastName;
    String password;

    @Column(unique = true)
    String phoneNumber;
    @Column(unique = true)
    String email;

    String avatar;

    LocalDate dob;

    LocalDateTime createAt;
    LocalDateTime updateAt;

    @OneToMany(mappedBy = "user")
    List<Post> posts;

    @OneToMany(mappedBy = "user")
    List<Comment> comments;

    @OneToMany(mappedBy = "receiver")
    List<Notify> notifys;

    @OneToMany(mappedBy = "user")
    List<Expression> expressions;

    @ManyToMany
    Set<Role> roles;

    public String getFullName(){
        return firstName + " " + lastName;
    }

    @PrePersist
    protected void onCreateAt(){
        this.createAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updateAt = LocalDateTime.now();
    }
}
