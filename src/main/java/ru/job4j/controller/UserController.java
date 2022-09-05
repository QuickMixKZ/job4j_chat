package ru.job4j.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import ru.job4j.service.RoleService;
import ru.job4j.service.UserService;
import ru.job4j.model.User;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final RoleService roleService;
    private final BCryptPasswordEncoder encoder;

    public UserController(UserService userService, RoleService roleService, BCryptPasswordEncoder encoder) {
        this.userService = userService;
        this.roleService = roleService;
        this.encoder = encoder;
    }

    @GetMapping("/")
    public List<User> findAll() {
        return userService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> findById(@PathVariable("id") int id) {
         return new ResponseEntity<>(
                 userService.findById(id),
                 HttpStatus.OK
         );
    }

    @PostMapping("/sign-up")
    public ResponseEntity<User> signUp(@RequestBody User user) {
        user.setPassword(encoder.encode(user.getPassword()));
        user.addRole(roleService.findByName("ROLE_USER"));
        return new ResponseEntity<>(
                userService.save(user),
                HttpStatus.CREATED
        );
    }

    @PutMapping("/")
    public ResponseEntity<User> updateUser(@RequestBody User user) {
        userService.update(user);
        return ResponseEntity.ok().build();
    }
}
