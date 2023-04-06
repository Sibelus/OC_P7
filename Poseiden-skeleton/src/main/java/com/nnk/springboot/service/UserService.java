package com.nnk.springboot.service;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;
import com.nnk.springboot.service.exceptions.NonExistantUserException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements IUserService {

    @Autowired
    private UserRepository userRepository;
    Logger logger  = LoggerFactory.getLogger(UserService.class);


    @Override
    public User getUserById(int id) throws NonExistantUserException {
        if (userRepository.existsById(id) == false) {
            throw new NonExistantUserException(" User id: " + id + " doesn't have a match in db");
        }

        logger.debug("Get user with id: {}", id);
        User user = userRepository.findById(id).get();
        return user;
    }

    @Override
    public List<User> getAllUsers() {
        logger.debug("Get all users");
        List<User> users = userRepository.findAll();
        return users;
    }

    @Override
    public void addUser(User user) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String password = passwordEncoder.encode(user.getPassword());
        user.setPassword(password);
        logger.debug("Save new user");
        userRepository.save(user);
    }

    @Override
    public void updateUser(User user) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String password = passwordEncoder.encode(user.getPassword());
        user.setPassword(password);
        logger.debug("Update user infos");
        userRepository.save(user);
    }

    @Override
    public void deleteUser(User user) {
        logger.debug("Delete user from db");
        userRepository.delete(user);
    }
}
