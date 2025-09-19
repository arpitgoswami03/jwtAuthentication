package com.security.jwtauthentication.service;

import com.security.jwtauthentication.model.User;
import com.security.jwtauthentication.model.UserPrinciple;
import com.security.jwtauthentication.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserPrincipleService implements UserDetailsService {

    private final UserRepo userRepo;

    @Autowired
    public UserPrincipleService(UserRepo userRepo) {
        System.out.println("UserPrincipleService");
        this.userRepo = userRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("loadUserByUsername");
        User user = userRepo.findByUsername(username);

        if(user == null){
            System.out.println("User not found with username: " + username);
            throw new UsernameNotFoundException("User not found with username: " + username);
        }

        return new UserPrinciple(user);
    }
}
