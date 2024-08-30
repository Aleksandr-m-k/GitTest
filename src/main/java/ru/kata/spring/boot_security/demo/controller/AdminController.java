package ru.kata.spring.boot_security.demo.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.List;


@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final RoleService roleService;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping()
    public String getAllUsers(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        model.addAttribute("principal", userService.findUserByUsername(userDetails.getUsername()));
        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("rolesList", roleService.findAllRoles());
        model.addAttribute("create", new User());
        return "users";
    }

    @GetMapping("/{id}")
    public String getUserById(@PathVariable("id") int id, Model model) {
        model.addAttribute("userById", userService.getUserById(id));
        return "userById";

    }

    @PostMapping("/newUser/")
    public String addUser(@ModelAttribute("createUser") User user, @RequestParam List<Integer> roleIds) {
        userService.addUser(user, roleIds);
        return "redirect:/admin";
    }

    @GetMapping("/{id}/edit")
    public String editUser(Model model, @PathVariable("id") int id) {
        System.out.println(roleService.findAllRoles());
        model.addAttribute("user", userService.getUserById(id));
        model.addAttribute("rolesList", roleService.findAllRoles());
        return "edit";
    }


    @PostMapping("/{id}")
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
