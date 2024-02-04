package com.epam.service;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.util.concurrent.*;

@Service
public class BlockedUsersCacheService {
    private final ConcurrentMap<String, Integer> attemptsCache = new ConcurrentHashMap<>();
    private final ConcurrentMap<String, Long> unblockTimeCache = new ConcurrentHashMap<>();
    private static final int THRESHOLD = 3;
    private static final int BLOCK_TIME_MINUTES = 5;

    @PostConstruct
    private void initialize() {
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);
        scheduledExecutorService.scheduleAtFixedRate(this::removeUnblocked, 0, 1, TimeUnit.MINUTES);
    }

    public void loginFailed(String username) {
        synchronized (username.intern()) {
            Integer attempts = attemptsCache.get(username);
            attempts = attempts == null ? 1 : attempts + 1;
            attemptsCache.put(username, attempts);
            if (attempts == THRESHOLD) {
                unblockTimeCache.put(username, System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(BLOCK_TIME_MINUTES));
            }
        }
    }

    public void loginSucceeded(String username) {
        synchronized (username.intern()) {
            attemptsCache.remove(username);
            unblockTimeCache.remove(username);
        }
    }

    public boolean isBlocked(String username) {
        synchronized (username.intern()) {
            if (!unblockTimeCache.containsKey(username)) {
                return false;
            }
            Long unblockTime = unblockTimeCache.get(username);
            if (unblockTime <= System.currentTimeMillis()) {
                unblockTimeCache.remove(username);
                return false;
            }
            return true;
        }
    }

    private void removeUnblocked() {
        long currentTime = System.currentTimeMillis();
        unblockTimeCache.forEach((username, unblockTime) -> {
            if (unblockTime <= currentTime) {
                unblockTimeCache.remove(username);
                attemptsCache.remove(username);
            }
        });
    }
}
