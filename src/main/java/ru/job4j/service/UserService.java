package ru.job4j.service;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.job4j.model.User;
import ru.job4j.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
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

    public Optional<User> findByName(String name) {
        return userRepository.findUserByUsername(name);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = findByName(username);
        if (user.isEmpty()) {
            throw new UsernameNotFoundException("Username not found");
        }
        return user.get();
    }
}
