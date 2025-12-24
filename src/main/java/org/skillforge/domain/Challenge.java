package org.skillforge.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Challenge {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private User user;
    private String title;
    private String description;
    private Enum<Difficulty> difficulty;
    private Enum<LifeCycle> lifeCycle;
    private boolean isVisible;

    public Challenge(User user, String title, String description, Enum<Difficulty> difficulty, Enum<LifeCycle> lifeCycle, boolean isVisible) {
        this.user = user;
        this.title = title;
        this.description = description;
        this.difficulty = difficulty;
        this.lifeCycle = lifeCycle;
        this.isVisible = isVisible;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Enum<Difficulty> getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Enum<Difficulty> difficulty) {
        this.difficulty = difficulty;
    }

    public Enum<LifeCycle> getLifeCycle() {
        return lifeCycle;
    }

    public void setLifeCycle(Enum<LifeCycle> lifeCycle) {
        this.lifeCycle = lifeCycle;
    }

    public boolean isVisible() {
        return isVisible;
    }

    public void setVisible(boolean visible) {
        isVisible = visible;
    }
}
