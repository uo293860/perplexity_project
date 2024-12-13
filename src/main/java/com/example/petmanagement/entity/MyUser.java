package com.example.petmanagement.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class MyUser {
    @Id
    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    private String role;

    private boolean enabled = true;
    private boolean accountNonExpired = true;
    private boolean credentialsNonExpired = true;

    private boolean unlocked = true;

}

