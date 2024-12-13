package com.example.petmanagement.service;

import com.example.petmanagement.entity.MyUser;
import com.example.petmanagement.repository.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public MyUser createUser(MyUser myUser) {
        myUser.setPassword(passwordEncoder.encode(myUser.getPassword()));
        return userRepository.save(myUser);
    }

    @Override
    public MyUser resetPassword(String username, String newPassword) {
        MyUser myUser = userRepository.findById(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        myUser.setPassword(passwordEncoder.encode(newPassword));
        return userRepository.save(myUser);
    }

    @Override
    public MyUser toggleLock(String username) {
        MyUser myUser = userRepository.findById(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        myUser.setUnlocked(!myUser.isUnlocked());
        return userRepository.save(myUser);
    }

    @Override
    public void deleteUser(String username) {
        userRepository.deleteById(username);
    }

    @Override
    public Optional<MyUser> findByUsername(String username) {
        return userRepository.findById(username);
    }

    @Override
    public List<MyUser> findAll() {
        return userRepository.findAll();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        MyUser user = userRepository.findById(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
        return new User(user.getUsername(), user.getPassword(), user.isEnabled(),
                user.isAccountNonExpired(),user.isCredentialsNonExpired(), user.isUnlocked(),
                Collections.singletonList(new SimpleGrantedAuthority(user.getRole())));
    }
}

