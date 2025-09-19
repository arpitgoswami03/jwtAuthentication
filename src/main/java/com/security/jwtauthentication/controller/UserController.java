package com.security.jwtauthentication.controller;

import com.security.jwtauthentication.model.UserDTO;
import com.security.jwtauthentication.service.JWTService;
import com.security.jwtauthentication.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    private final UserService userService;
    private final JWTService jwtService;

    @Autowired
    public UserController(UserService userService, JWTService jwtService) {
        System.out.println("UserController");
        this.userService = userService;
        this.jwtService = jwtService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody UserDTO userDTO) throws Exception {
        System.out.println("loginUser");
        if(userDTO.getUsername()==null || userDTO.getPassword()==null){
            return new ResponseEntity<>("Incomplete credentials", HttpStatus.BAD_REQUEST);
        }
        if(userService.validateUser(userDTO)){
            System.out.println("Validating user");
            return new ResponseEntity<>(
                    jwtService.generateToken(
                            userDTO.getUsername(), userDTO.getPassword()
                    ),
                    HttpStatus.OK
            );
        }
        return new ResponseEntity<>("User unauthorized", HttpStatus.UNAUTHORIZED);
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody UserDTO userDTO) throws Exception {
        System.out.println("registerUser");
        if(userDTO.getUsername()==null || userDTO.getPassword()==null){
            return new ResponseEntity<>("Incomplete credentials", HttpStatus.BAD_REQUEST);
        }
        userService.createUser(userDTO);
        return ResponseEntity.ok("User registered successfully");
    }
}
