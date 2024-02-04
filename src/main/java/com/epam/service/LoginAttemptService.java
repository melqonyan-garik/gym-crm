package com.epam.service;

import org.springframework.stereotype.Service;

@Service
public class LoginAttemptService {
    private final BlockedUsersCacheService blockedUsersCacheService;

    public LoginAttemptService(BlockedUsersCacheService blockedUsersCacheService) {
        this.blockedUsersCacheService = blockedUsersCacheService;
    }

    public void loginFailed(String username) {
        blockedUsersCacheService.loginFailed(username);
    }

    public void loginSucceeded(String username) {
        blockedUsersCacheService.loginSucceeded(username);
    }

    public boolean isBlocked(String username) {
        return blockedUsersCacheService.isBlocked(username);
    }
}