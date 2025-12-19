package org.skillforge.service;

import jakarta.transaction.Transactional;
import org.skillforge.domain.RefreshToken;
import org.skillforge.domain.User;
import org.skillforge.dto.AuthResponse;
import org.skillforge.repository.RefreshTokenRepository;
import org.skillforge.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.sql.Ref;
import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

@Service
@Transactional
public class RefreshTokenService {
    private static final long REFRESH_TOKEN_EXPIRY = 7 * 24 * 60 * 60 * 1000;

    private final RefreshTokenRepository refreshTokenRepository;
    private final UserRepository userRepository;
    private final JwtService jwtService;

    public RefreshTokenService(RefreshTokenRepository refreshTokenRepository, UserRepository userRepository, JwtService jwtService) {
        this.refreshTokenRepository = refreshTokenRepository;
        this.userRepository = userRepository;
        this.jwtService = jwtService;
    }

    public AuthResponse verifyAndCreateRefreshToken(String RefreshToken) {
        RefreshToken refreshToken = refreshTokenRepository.findByToken(RefreshToken).map(this::verifyExpiration).orElseThrow();
        User user = refreshToken.getUser();

        String newAccessToken = jwtService.GenerateToken(org.springframework.security.core.userdetails.User.withUsername(user.getEmail()).password(user.getPassword()).build());

        return new AuthResponse(newAccessToken, createRefreshToken(user.getEmail()).getToken());
    }

    public RefreshToken createRefreshToken(String username) {
        User user = userRepository.findByEmail(username).orElseThrow();
        deleteByUser(user);


        RefreshToken token = new RefreshToken();
        token.setUser(user);
        token.setToken(UUID.randomUUID().toString());
        token.setExpiryDate(Instant.now().plusMillis(REFRESH_TOKEN_EXPIRY));

        return refreshTokenRepository.save(token);
    }

    public void deleteByUser(User user) {
        refreshTokenRepository.deleteByUser(user);
        refreshTokenRepository.flush();
    }

    public RefreshToken verifyExpiration(RefreshToken token){
        if(token.getExpiryDate().isBefore(Instant.now())) {
            refreshTokenRepository.delete(token);
            throw new RuntimeException("Refresh token expired");
        }

        return token;
    }

    public ResponseEntity<String> logoutUser() {
        try {
            String username = Objects.requireNonNull(SecurityContextHolder.getContext().getAuthentication()).getName();
            System.out.println(username);
            User user = userRepository.findByEmail(username).orElseThrow();
            System.out.println(user);
            deleteByUser(user);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body("Logout Successful");
        } catch(Exception e) {
            System.out.println(e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Logout Failed");
        }

    }
}
