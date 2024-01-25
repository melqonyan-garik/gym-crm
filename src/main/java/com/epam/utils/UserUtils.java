package com.epam.utils;

import com.epam.dao.UserDao;
import com.epam.exceptions.InvalidPasswordException;
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
        String finalPassword = password.toString();
        if (!isValidPassword(finalPassword)){
            throw new InvalidPasswordException("Invalid password criteria. Please choose a stronger password.");
        }
        return finalPassword;
    }

    public String generateUsername(String firstname, String lastname) {
        String baseUsername = getBaseUsername(firstname, lastname);
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

    public static String getBaseUsername(String firstname, String lastname) {
        return firstname + "." + lastname;
    }
    public static boolean isValidPassword(String password) {
        // Add your password criteria checks here
        return password.length() >= 6 && password.matches(".*[A-Z].*") && password.matches(".*[a-z].*");
    }

}
