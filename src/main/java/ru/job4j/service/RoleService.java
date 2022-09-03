package ru.job4j.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.job4j.repository.RoleRepository;
import ru.job4j.model.Role;

import java.util.List;
import java.util.Optional;

@Service
public class RoleService {

    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public List<Role> findAll() {
        return (List<Role>) roleRepository.findAll();
    }

    public Role findById(int id) {
        Optional<Role> role = roleRepository.findById(id);
        if (role.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Role not found");
        }
        return role.get();
    }

    public Role findByName(String roleName) {
        Optional<Role> role = roleRepository.findByName(roleName);
        if (role.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Role not found");
        }
        return role.get();
    }
}
