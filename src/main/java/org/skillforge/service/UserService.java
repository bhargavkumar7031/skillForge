package org.skillforge.service;

import org.skillforge.domain.User;
import org.skillforge.dto.signupRequestDTO;
import org.skillforge.exceptions.InvalidInputException;
import org.skillforge.repository.UserRepository;
import org.skillforge.validators.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.regex.Pattern;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private UserValidator userValidator;


    public void add(signupRequestDTO signupRequest) {
        User user = new User();
        userValidator.userValidation(signupRequest);
        user.setEmail(signupRequest.getEmail());
        user.setPassword(signupRequest.getPassword());
        user.setCreatedAt(new Date());

        userRepo.save(user);
    }
}
