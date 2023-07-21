package com.example.TaskPP314.service;

import com.example.TaskPP314.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {

    List<User> getAllUsers();

    void saveUser(User user);

    void delete(Long id);

    User findByName(String username);

}