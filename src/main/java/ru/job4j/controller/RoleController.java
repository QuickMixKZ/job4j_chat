package ru.job4j.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.job4j.model.Role;
import ru.job4j.service.RoleService;

import java.util.List;

@RestController
@RequestMapping("/role")
public class RoleController {

    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping("/")
    public List<Role> findAll() {
        return roleService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Role> findById(@PathVariable("id") int id) {
        return new ResponseEntity<>(
                roleService.findById(id),
                HttpStatus.OK
        );
    }

    @GetMapping("/findByName/{roleName}")
    public ResponseEntity<Role> findByName(@PathVariable("roleName") String roleName) {
        return new ResponseEntity<>(
                roleService.findByName(roleName),
                HttpStatus.OK
        );
    }
}
