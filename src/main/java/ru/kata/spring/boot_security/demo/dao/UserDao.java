package ru.kata.spring.boot_security.demo.dao;


import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

public interface UserDao {

    List<Role> findAllRolesById(List<Integer> roleIds);

    List<Role> findAllRoles();

    User findUserByUsername(String email);

    List<User> getAllUsers();

    User getUserById(int id);

    void saveUser(User user);

    User updateUser(User user);

    void deleteUser(int id);
}
