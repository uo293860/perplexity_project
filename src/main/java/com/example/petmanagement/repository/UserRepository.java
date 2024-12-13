package com.example.petmanagement.repository;

import com.example.petmanagement.entity.MyUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<MyUser, String> {
}
