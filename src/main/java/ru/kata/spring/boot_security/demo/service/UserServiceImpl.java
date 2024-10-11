package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.dao.UserDao;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class UserServiceImpl implements UserService {


    private UserDao userDao;
    private RoleService roleService;

    @Autowired
    public UserServiceImpl(UserDao userDao, RoleService roleService) {
        this.userDao = userDao;
        this.roleService = roleService;
    }

    @Override
    public User findUserByUsername(String username) {
        return userDao.findUserByUsername(username);
    }

    @Override
    public List<User> getAllUsers() {
        return userDao.getAllUsers();
    }

    @Override
    public User getUserById(int id) {
        return userDao.getUserById(id);
    }

    @Override
    public void saveUser(User user) {
        userDao.saveUser(user);
    }

    @Override
    public void addUser(User user, List<Integer> roleIds) {
        List<Role> roles = roleService.findAllRolesById(roleIds);
        user.setRoles(new HashSet<>(roles));
        saveUser(user);
    }

    @Override
    public User updateUser(User updateUser, int id) {
        User user = getUserById(id);
        user.setName(updateUser.getName());
        user.setSurname(updateUser.getSurname());
        user.setAge(updateUser.getAge());
        user.setEmail(updateUser.getEmail());
        user.setPassword(updateUser.getPassword());
        user.setRoles(updateUser.getRoles());
        return userDao.updateUser(user);
    }

    @Override
    public void deleteUser(int id) {
        userDao.deleteUser(id);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userDao.findUserByUsername(email);
        if (user == null) {
            throw new UsernameNotFoundException("пользователь - " + email + "отсутствует!!!");
        }

        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                user.getRoles());
    }
}

