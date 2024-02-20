package com.epam.controller;

import com.epam.dao.UserDao;
import com.epam.exceptions.InvalidPasswordException;
import com.epam.exceptions.WrongPasswordException;
import com.epam.model.User;
import com.epam.utils.UserUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class LoginController {
    private final UserDao userDao;

    @PutMapping("/change-password")
    public ResponseEntity<String> changePassword(
            @RequestParam String username,
            @RequestParam String oldPassword,
            @RequestParam String newPassword) {
        Optional<User> optionalUser = userDao.findByUsername(username);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            if (user.getPassword().equals(oldPassword)) {
                if (UserUtils.isValidPassword(newPassword)) {
                    user.setPassword(newPassword);
                    userDao.update(user);
                } else {
                    throw new InvalidPasswordException("Invalid password criteria. Please choose a stronger password.");
                }
            } else {
                throw new WrongPasswordException("Old password is incorrect");
            }
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");

    }
}
