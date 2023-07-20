package com.example.TaskPP314.service;

import com.example.TaskPP314.model.Role;
import com.example.TaskPP314.model.User;
import com.example.TaskPP314.repository.RoleRepository;
import com.example.TaskPP314.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository,
                           PasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        User user = userRepository.findByName(name);
        if (user == null) {
            throw new UsernameNotFoundException("Invalid username or password.");
        }
        return user;
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public void saveUser(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    @Override
    public void delete(Long id) {
        User user = userRepository.findById(id).get();
        user.setRoles(null);
        userRepository.save(user);
        userRepository.deleteById(id);
    }

    @Override
    public User getAuthUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return userRepository.findByName(auth.getName());
    }

    @Override
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

}