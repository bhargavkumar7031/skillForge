package org.skillforge.controller;

import org.skillforge.dto.authRequestDTO;
import org.skillforge.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<String> addUser(@RequestBody authRequestDTO signupRequest){
        try {
            userService.add(signupRequest);
        } catch(RuntimeException err) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err.toString());
        }
        return ResponseEntity.status(HttpStatus.ACCEPTED).body("User added successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<String> verifyUser(@RequestBody authRequestDTO signInRequest) {
        String userVerification;
        try {
            userVerification = userService.verify(signInRequest);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.toString());
        }

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(userVerification);
    }

}
