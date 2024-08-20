package ru.kata.spring.boot_security.demo.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.ArrayList;
import java.util.List;


@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }


    @GetMapping()
    public String getAllUsers(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "users";
    }

    @GetMapping("/{id}")
    public String getUserById(@PathVariable("id") int id, Model model) {
        model.addAttribute("userById", userService.getUserById(id));
        return "userById";

    }

    @GetMapping("/new")
    public String createUser(Model model) {
        model.addAttribute("createUser", new User());
        model.addAttribute("rolesList", roleService.findAllRoles());
        return "new";
    }

    @PostMapping()
    public String addUser(@ModelAttribute("createUser") User user, @RequestParam List<Integer> roleIds) {
        userService.addUser(user, roleIds);
        return "redirect:/admin";
    }

    @GetMapping("/{id}/editUser")
    public String editUser(Model model, @PathVariable("id") int id) {
        System.out.println("editUser method called___________________________");

        List<Role> rolesList = roleService.findAllRoles();
        System.out.println("Roles (before check): _________________" + rolesList); // Проверяем результат вызова метода

        if (rolesList == null || rolesList.isEmpty()) {
            System.out.println("Roles list is null or empty!______________________"); // Сообщение в случае проблемы
        } else {
            System.out.println("Roles count: _________________________" + rolesList.size());
        }

        model.addAttribute("user", userService.getUserById(id));
        model.addAttribute("rolesList", rolesList);
        return "editUser";
    }


    @PostMapping("/editUser")
    public String updateUser(@ModelAttribute User user, @RequestParam List<Integer> roleIds) {
        userService.updateUser(user, roleIds);
        return "redirect:/admin";

    }

    @PostMapping("/{id}/delete")
    public String deleteUser(@PathVariable("id") int id) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }
}
