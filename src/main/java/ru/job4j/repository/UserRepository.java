package ru.job4j.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.CrudRepository;
import ru.job4j.model.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Integer> {

    @Override
    List<User> findAll();

    @EntityGraph(attributePaths = {"roles"})
    Optional<User> findUserByUsername(String name);

}
