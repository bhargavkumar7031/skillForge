package org.skillforge.controller;

import org.skillforge.domain.User;
import org.skillforge.dto.signupRequestDTO;
import org.skillforge.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/signup")
    public void addUser(@RequestBody signupRequestDTO signupRequest){
        userService.add(signupRequest);
    }

}
