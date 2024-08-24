package ru.kata.spring.boot_security.demo.service;


import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

public interface UserService extends UserDetailsService {


    User findUserByUsername(String username);

    List<User> getAllUsers();

    User getUserById(int id);

    void saveUser(User user);

    User updateUser(User user, List<Integer> roleIds);

    void deleteUser(int id);

    void addUser(User user, List<Integer> roleIds);

    UserDetails loadUserByUsername(String email);
}
