package com.nnk.springboot.service;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.service.exceptions.NonExistantUserException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IUserService {

    User getUserById(int id) throws NonExistantUserException;
    List<User> getAllUsers();
    void addUser(User user);
    void updateUser(User user);
    void deleteUser(User user);
}
