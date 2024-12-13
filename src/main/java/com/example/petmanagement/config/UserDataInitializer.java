package com.example.petmanagement.config;

import com.example.petmanagement.entity.MyUser;
import com.example.petmanagement.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserDataInitializer {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserDataInitializer(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }
    @Transactional
    public void initializeUsers() {
        // Create Admin users
        MyUser admin1 = new MyUser();
        admin1.setUsername("admin1");
        admin1.setPassword(passwordEncoder.encode("adminpass1"));
        admin1.setRole("ROLE_ADMIN");
        admin1.setUnlocked(true);

        MyUser admin2 = new MyUser();
        admin2.setUsername("admin2");
        admin2.setPassword(passwordEncoder.encode("adminpass2"));
        admin2.setRole("ROLE_ADMIN");
        admin2.setUnlocked(true);

        // Create regular users
        MyUser myUser1 = new MyUser();
        myUser1.setUsername("user1");
        myUser1.setPassword(passwordEncoder.encode("userpass1"));
        myUser1.setRole("ROLE_USER");
        myUser1.setUnlocked(true);

        MyUser myUser2 = new MyUser();
        myUser2.setUsername("user2");
        myUser2.setPassword(passwordEncoder.encode("userpass2"));
        myUser2.setRole("ROLE_USER");
        myUser2.setUnlocked(true);

        // Save users to the database
        userRepository.save(admin1);
        userRepository.save(admin2);
        userRepository.save(myUser1);
        userRepository.save(myUser2);
    }
}
