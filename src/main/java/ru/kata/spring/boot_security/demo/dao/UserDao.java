package ru.kata.spring.boot_security.demo.dao;


import org.springframework.security.core.userdetails.UserDetails;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

public interface UserDao {

    User findUserById(int id);
    List<User> getAllUsers();

    User getUserById(int id);

    void saveUser(User user);

    User updateUser(User user);

    void deleteUser(int id);
}
