package com.example.petmanagement.controller;

import com.example.petmanagement.entity.Household;
import com.example.petmanagement.entity.MyUser;
import com.example.petmanagement.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<MyUser>> getAllUsers() {
        return ResponseEntity.ok(userService.findAll());
    }

    @PostMapping
    public ResponseEntity<MyUser> createUser(@RequestBody MyUser myUser) {
        return ResponseEntity.ok(userService.createUser(myUser));
    }

    @PutMapping("/{username}/reset-password")
    public ResponseEntity<MyUser> resetPassword(@PathVariable String username, @RequestBody String newPassword) {
        return ResponseEntity.ok(userService.resetPassword(username, newPassword));
    }

    @PutMapping("/{username}/toggle-lock")
    public ResponseEntity<MyUser> toggleLock(@PathVariable String username) {
        return ResponseEntity.ok(userService.toggleLock(username));
    }

    @DeleteMapping("/{username}")
    public ResponseEntity<Void> deleteUser(@PathVariable String username) {
        userService.deleteUser(username);
        return ResponseEntity.ok().build();
    }
}

