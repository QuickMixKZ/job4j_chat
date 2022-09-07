package ru.job4j.service;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.job4j.dto.UserDTO;
import ru.job4j.model.Role;
import ru.job4j.model.User;
import ru.job4j.repository.UserRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final RoleService roleService;

    public UserService(UserRepository userRepository, RoleService roleService) {
        this.userRepository = userRepository;
        this.roleService = roleService;
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findById(int id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }
        return user.get();
    }

    public User save(User user) {
        Optional<User> userDb = userRepository.findUserByUsername(user.getUsername());
        if (userDb.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Username already exists");
        }
        return userRepository.save(user);
    }

    public void update(User user) {
        if (userRepository.existsById(user.getId())) {
            userRepository.save(user);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }
    }

    public User patch(UserDTO userDTO) {
        if (userDTO.getId() == 0) {
            throw new NullPointerException("User's id mustn't be empty");
        }
        User currentUser = findById(userDTO.getId());
        Optional.ofNullable(userDTO.getUsername()).ifPresent(currentUser::setUsername);
        Optional.ofNullable(userDTO.getPassword()).ifPresent(currentUser::setPassword);
        Set<Role> roles = new HashSet<>();
        for (Integer roleId : userDTO.getRoles()) {
            Role currentRole = roleService.findById(roleId);
            roles.add(currentRole);
        }
        if (roles.size() > 0) {
            currentUser.setRoles(roles);
        }
        update(currentUser);
        return currentUser;
    }

    public User findByName(String name) {
        Optional<User> user = userRepository.findUserByUsername(name);
        if (user.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Username not found");
        }
        return user.get();
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        return findByName(username);
    }
}
