package ru.kata.spring.boot_security.demo.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/admin")
public class  MyRestController {
    private final UserService userService;
    private final RoleService roleService;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public MyRestController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }
    // получение инфы о текущем пользователе
    @GetMapping("/currentUser")
    public ResponseEntity<User> getCurrentUser(Principal principal){
        User currentUser = userService.findUserByUsername(principal.getName());
        return new ResponseEntity<>(currentUser, HttpStatus.OK);
    }

//    @GetMapping("/")
//    public ResponseEntity<List<User>> showAllUsers() {
//        List<User> allUsers = userService.getAllUsers();
//        return new ResponseEntity<>(allUsers,HttpStatus.OK);
//    }

    @GetMapping("/")
    public ResponseEntity<List<User>> getUsers() {
        List<User> allUsers = userService.getAllUsers();
        return new ResponseEntity<>(allUsers, HttpStatus.OK);
    }




//    @GetMapping("/{id}")
//    public ResponseEntity<User> showAllUserById(@PathVariable int id) {
//        return new ResponseEntity<>(userService.getUserById(id));
//    }
//    @PostMapping("/")
//    public User addNewUser (@RequestBody User user){
//        userService.saveUser(user);
//        return user;
//    }
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> delUser(@PathVariable ("id") int id) {
        userService.deleteUser(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
