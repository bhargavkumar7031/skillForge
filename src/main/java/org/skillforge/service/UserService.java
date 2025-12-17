package org.skillforge.service;

import org.skillforge.domain.User;
import org.skillforge.dto.authRequestDTO;
import org.skillforge.exceptions.InvalidInputException;
import org.skillforge.repository.UserRepository;
import org.skillforge.validators.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private UserValidator userValidator;


    public void add(authRequestDTO signupRequest) {
        User user = new User();
        userValidator.userValidation(signupRequest);

        if(userRepo.existsByEmail(signupRequest.getEmail())) {
            throw new InvalidInputException("User already exists");
        };

        user.setEmail(signupRequest.getEmail());
        user.setPassword(signupRequest.getPassword());
        user.setCreatedAt(new Date());

        userRepo.save(user);
    }

    public String verify(authRequestDTO loginRequest){
        userValidator.userValidation(loginRequest);

        User user = userRepo.findByEmail(loginRequest.getEmail()).orElseThrow();
        if(loginRequest.getPassword().equals(user.getPassword())){
            return "Password Matched!! Welcome User" + user.getEmail();
        }

        return "Incorrect credentials. Please try again";
    }
}
