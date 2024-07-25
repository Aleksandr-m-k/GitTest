package ru.kata.spring.boot_security.demo.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.ArrayList;
import java.util.List;

@Controller
public class UserController {
    UserService userService;

    @GetMapping(value = "/")
    public String printWelcome(ModelMap model) {
        List<String> messages = new ArrayList<>();
        messages.add("Hello!");
        messages.add("I'm Spring MVC application");
        messages.add("5.2.0 version by sep'19 ");
        model.addAttribute("messages", messages);
        return "index";
    }
    @GetMapping("/user/")
    public String infoUser(@AuthenticationPrincipal User user, ModelMap model) {
        model.addAttribute("user", user);
        return "user";
    }
    @GetMapping("/user/{name}")
    public String getUserById(@PathVariable("name") String name, Model model) {
        model.addAttribute("userById", userService.getUserById(id));
        return "user";
    }
}
