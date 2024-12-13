package com.example.petmanagement.service;

import com.example.petmanagement.entity.MyUser;

import java.util.List;
import java.util.Optional;

public interface UserService {
    MyUser createUser(MyUser myUser);
    MyUser resetPassword(String username, String newPassword);
    MyUser toggleLock(String username);
    void deleteUser(String username);
    Optional<MyUser> findByUsername(String username);

    List<MyUser> findAll();
}

