package ru.job4j.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import ru.job4j.handler.GlobalExceptionHandler;
import ru.job4j.model.User;
import ru.job4j.service.RoleService;
import ru.job4j.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final RoleService roleService;
    private final BCryptPasswordEncoder encoder;

    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class.getSimpleName());

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
        validateUser(user);
        user.setPassword(encoder.encode(user.getPassword()));
        user.addRole(roleService.findByName("ROLE_USER"));
        return new ResponseEntity<>(
                userService.save(user),
                HttpStatus.CREATED
        );
    }

    @PutMapping("/")
    public ResponseEntity<User> updateUser(@RequestBody User user) {
        validateUser(user);
        userService.update(user);
        return ResponseEntity.ok().build();
    }

    @ExceptionHandler(value = { IllegalArgumentException.class })
    public void exceptionHandler(Exception e, HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        response.setContentType("application/json");
        ObjectMapper objectMapper = new ObjectMapper();
        response.getWriter().write(objectMapper.writeValueAsString(new HashMap<>() { {
            put("message", e.getMessage());
            put("type", e.getClass());
        }}));
        LOGGER.error(e.getMessage());
    }

    private void validateUser(User user) {
        if (user.getUsername() == null || user.getPassword() == null) {
            throw new NullPointerException("Username and password mustn't be empty");
        }
        if (user.getPassword().length() < 5) {
            throw new IllegalArgumentException("Password must have at least 5 characters");
        }
    }
}
