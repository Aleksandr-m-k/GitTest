package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.dao.UserDaoImpl;
import ru.kata.spring.boot_security.demo.model.Role;

import java.util.List;

@Service
@Transactional
public class RoleServiceImpl implements RoleService {
    private final UserDaoImpl userDao;

    @Autowired
    public RoleServiceImpl(UserDaoImpl userDao) {
        this.userDao = userDao;
    }

    @Override
    public List<Role> findAllRolesById(List<Integer> ids) {
        return userDao.findAllRolesById(ids);
    }

    @Override
    public List<Role> findAllRoles() {
        return userDao.findAllRoles();
    }
}
