package org.skillforge.controller;

import org.skillforge.domain.RefreshToken;
import org.skillforge.dto.AuthResponse;
import org.skillforge.dto.authRequestDTO;
import org.skillforge.service.RefreshTokenService;
import org.skillforge.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private UserService userService;

    @Autowired
    private RefreshTokenService refreshTokenService;

    @PostMapping("/signup")
    public ResponseEntity<String> addUser(@RequestBody authRequestDTO signupRequest){
        try {
            userService.add(signupRequest);
        } catch(RuntimeException err) {
            log.warn(err.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err.toString());
        }
        return ResponseEntity.status(HttpStatus.ACCEPTED).body("User added successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<String> verifyUser(@RequestBody authRequestDTO signInRequest) {
        String userVerification;
        try {
            userVerification = String.valueOf(userService.verify(signInRequest));
        } catch (Exception e) {
            log.warn(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(userVerification);
    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthResponse> refresh(@RequestBody String request) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(refreshTokenService.verifyAndCreateRefreshToken(request));
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout() {
        return refreshTokenService.logoutUser();
    }
}
