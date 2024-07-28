package ru.kata.spring.boot_security.demo.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repositories.RoleRepository;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.security.Principal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final RoleRepository roleRepository;
    @Autowired
    public AdminController(UserService userService, RoleRepository roleRepository) {
        this.userService = userService;
        this.roleRepository = roleRepository;
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
        model.addAttribute("rolesList", roleRepository.findAll());
        return "new";
    }
    @PostMapping()
    public String addUser(@ModelAttribute("createUser") User user, @RequestParam Set<Integer> roleIds) {
        Set<Role> roles = user.getRoleIds().stream()
                .map(roleId -> roleRepository.findById(roleId).orElse(null))
                .collect(Collectors.toSet());
            user.setRoles(roles);
            userService.saveUser(user);
        return "redirect:/admin";
    }

    @GetMapping("/{id}/edit")
    public String editUser(Model model, @PathVariable("id") int id) {
        model.addAttribute("user", userService.getUserById(id));
        model.addAttribute("rolesList", roleRepository.findAll());
        return "edit";
    }

    @PostMapping("/{id}")
    public String updateUser(@ModelAttribute User user, @RequestParam List<Integer> roleIds) {
        List<Role> roles = roleRepository.findAllById(roleIds);
        user.setRoles(new HashSet<>(roles));
        userService.updateUser(user);
        return "redirect:/admin";

    }

    @PostMapping("/{id}/delete")
    public String deleteUser(@PathVariable("id") int id) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }
}
