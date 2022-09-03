package ru.job4j.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.job4j.service.RoleService;
import ru.job4j.service.UserService;
import ru.job4j.model.User;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final RoleService roleService;

    public UserController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
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

    @PostMapping("/")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        user.addRole(roleService.findByName("ROLE_USER"));
        return new ResponseEntity<>(
                this.userService.save(user),
                HttpStatus.CREATED
        );
    }

    @PutMapping("/")
    public ResponseEntity<User> updateUser(@RequestBody User user) {
        userService.update(user);
        return ResponseEntity.ok().build();
    }
}
