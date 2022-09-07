package ru.job4j.dto;

import java.util.HashSet;
import java.util.Set;

public class UserDTO {

    private int id;
    private String username;
    private String password;
    private Set<Integer> roles = new HashSet<>();

    public UserDTO() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Integer> getRoles() {
        return roles;
    }

    public void setRoles(Set<Integer> roles) {
        this.roles = roles;
    }


}
