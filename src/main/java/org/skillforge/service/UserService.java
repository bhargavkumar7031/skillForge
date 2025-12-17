package org.skillforge.service;

import org.skillforge.domain.User;
import org.skillforge.dto.authRequestDTO;
import org.skillforge.exceptions.InvalidInputException;
import org.skillforge.repository.UserRepository;
import org.skillforge.validators.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepo;
    private final UserValidator userValidator;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;


    public UserService(UserRepository userRepo, UserValidator userValidator, AuthenticationManager authenticationManager, JwtService jwtService, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.userValidator = userValidator;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
    }

    public void add(authRequestDTO signupRequest) {
        User user = new User();
        userValidator.userValidation(signupRequest);

        if(userRepo.existsByEmail(signupRequest.getEmail())) {
            throw new InvalidInputException("User already exists");
        };

        user.setEmail(signupRequest.getEmail());
        user.setPassword(passwordEncoder.encode(signupRequest.getPassword()));
        user.setCreatedAt(new Date());

        userRepo.save(user);
    }

    public String verify(authRequestDTO loginRequest){
        userValidator.userValidation(loginRequest);

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

        User user = userRepo.findByEmail(loginRequest.getEmail()).orElseThrow();
        if(authentication.isAuthenticated()){
            return jwtService.GenerateToken(userDetails(user));
        }
        return "Incorrect credentials. Please try again";
    }

    public UserDetails userDetails(User user) {
        return org.springframework.security.core.userdetails.User.withUsername(user.getEmail()).password(user.getPassword()).build();
    }
}
