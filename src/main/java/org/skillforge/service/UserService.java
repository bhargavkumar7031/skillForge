package org.skillforge.service;

import org.skillforge.domain.User;
import org.skillforge.dto.AuthResponse;
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

    private final RefreshTokenService refreshTokenService;


    public UserService(UserRepository userRepo, UserValidator userValidator, AuthenticationManager authenticationManager, JwtService jwtService, PasswordEncoder passwordEncoder, RefreshTokenService refreshTokenService) {
        this.userRepo = userRepo;
        this.userValidator = userValidator;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
        this.refreshTokenService = refreshTokenService;
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

    public AuthResponse verify(authRequestDTO loginRequest){
        userValidator.userValidation(loginRequest);

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

        User user = userRepo.findByEmail(loginRequest.getEmail()).orElseThrow();
        if(authentication.isAuthenticated()){
            return new AuthResponse(jwtService.GenerateToken(userDetails(user)), refreshTokenService.createRefreshToken(user.getEmail()).getToken());
        }
        return new AuthResponse("", "");
    }

    public UserDetails userDetails(User user) {
        return org.springframework.security.core.userdetails.User.withUsername(user.getEmail()).password(user.getPassword()).build();
    }
}
