package org.skillforge.repository;

import org.skillforge.domain.User;
import org.skillforge.domain.UserStats;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserStatsRepository extends JpaRepository<UserStats, Long> {
    Optional<UserStats> findByUser(User user);
}
