package com.security.jwtauthentication.service;

import com.security.jwtauthentication.model.User;
import com.security.jwtauthentication.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.security.jwtauthentication.model.UserDTO;

@Service
public class UserService {

    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    private final User user = new User();
    private final AuthenticationManager authenticationManager;

    @Autowired
    public UserService(UserRepo userRepo, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager) {
        System.out.println("loadUserByUsername");
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }

    public Boolean validateUser(UserDTO userDTO) {
        System.out.println("validateUser");
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userDTO.getUsername(), userDTO.getPassword())
        );
        return authentication.isAuthenticated();
    }

    public String createUser(UserDTO userDTO) {
        if(userRepo.findByUsername(userDTO.getUsername())!=null) {
            return "Username already exist";
        }
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setUsername(userDTO.getUsername());
        userRepo.save(user);
        return "User registered successfully";
    }
}
