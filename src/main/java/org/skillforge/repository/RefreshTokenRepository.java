package org.skillforge.repository;

import org.skillforge.domain.RefreshToken;
import org.skillforge.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByToken(String Token);

    void deleteByUser(User user);
}
