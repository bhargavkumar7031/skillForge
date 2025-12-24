package org.skillforge.service;

import org.skillforge.repository.ChallengeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChallengeService {
    @Autowired
    private ChallengeRepository challengeRepository;

    
}
