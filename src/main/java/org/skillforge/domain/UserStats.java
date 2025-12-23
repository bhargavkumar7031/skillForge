package org.skillforge.domain;

import jakarta.persistence.*;
import org.springframework.data.repository.query.DefaultParameters;

import java.time.LocalDate;
import java.util.Date;

@Entity
public class UserStats {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    private double Xp;
    private int currentStreak;
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
    private LocalDate lastActivityDate;

    public UserStats( double xp, int currentStreak, LocalDate lastActivityDate, User user) {
        this.Xp = xp;
        this.currentStreak = currentStreak;
        this.lastActivityDate = lastActivityDate;
        this.user = user;
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public double getXp() {
        return Xp;
    }

    public void setXp(double xp) {
        Xp = xp;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getCurrentStreak() {
        return currentStreak;
    }

    public void setCurrentStreak(int currentStreak) {
        this.currentStreak = currentStreak;
    }

    public LocalDate getLastActivityDate() {
        return lastActivityDate;
    }

    public void setLastActivityDate(LocalDate lastActivityDate) {
        this.lastActivityDate = lastActivityDate;
    }
}
