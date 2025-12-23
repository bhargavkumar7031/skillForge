package org.skillforge.service;

import org.skillforge.domain.User;
import org.skillforge.domain.UserStats;
import org.skillforge.repository.UserRepository;
import org.skillforge.repository.UserStatsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Objects;

@Service
public class UserStatsService {
    @Autowired
    private UserStatsRepository userStatsRepository;
    @Autowired
    private UserRepository userRepository;

    private final LocalDate yesterday = LocalDate.now().minusDays(1);

    public void addStreak(String email) {
        User user = userRepository.findByEmail(email).orElseThrow();
        UserStats userStats = new UserStats(0, 1, LocalDate.now(), user);
        userStatsRepository.save(userStats);
    }
    public void updateStreak() {
        String currentUser = Objects.requireNonNull(SecurityContextHolder.getContext().getAuthentication()).getName();
        User user = userRepository.findByEmail(currentUser).orElseThrow();
        UserStats userStats = userStatsRepository.findByUser(user).orElseThrow();

        if(userStats.getLastActivityDate() == yesterday) {
            userStats.setCurrentStreak(userStats.getCurrentStreak() + 1);
        } else {
            userStats.setCurrentStreak(1);
        }
        userStats.setLastActivityDate(LocalDate.now());

        userStatsRepository.save(userStats);
    }
}
