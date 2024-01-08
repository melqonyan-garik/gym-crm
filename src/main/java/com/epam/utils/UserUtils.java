package com.epam.utils;

import com.epam.dao.UserDao;
import com.epam.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Random;
import java.util.Set;

@Component
public class UserUtils {
    @Autowired
    private UserDao userDao;

    public static String generateRandomPassword() {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder password = new StringBuilder();
        Random random = new Random();

        for (int i = 0; i < 10; i++) {
            int index = random.nextInt(characters.length());
            password.append(characters.charAt(index));
        }
        return password.toString();
    }
    public String generateUsername(User user) {
        String baseUsername = user.getFirstname() + "." + user.getLastname();
        String username = baseUsername;
        Set<String> existingUsernames = userDao.getAllUsernames();

        // Add serial number if the username already exists
        int serialNumber = 1;
        while (existingUsernames.contains(username)) {
            username = baseUsername + serialNumber;
            serialNumber++;
        }

        return username;
    }
}
