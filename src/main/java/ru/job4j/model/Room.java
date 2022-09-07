package ru.job4j.model;

import ru.job4j.handler.Operation;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "rooms")
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotBlank(message = "Name must not be empty.",
            groups = {Operation.OnCreate.class})
    @Column(name = "name")
    private String name;
    @Column(name = "created")
    private LocalDate created;
    @ManyToOne
    @JoinColumn(name = "creator_id")
    private User user;
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "rooms_messages",
            joinColumns = {@JoinColumn(name = "room_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "message_id", referencedColumnName = "id")})
    private Set<Message> messages = new HashSet<>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getCreated() {
        return created;
    }

    public void setCreated(LocalDate created) {
        this.created = created;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<Message> getMessages() {
        return messages;
    }

    public void setMessages(Set<Message> messages) {
        this.messages = messages;
    }

    public void addMessage(Message messages) {
        this.messages.add(messages);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Room room = (Room) o;
        return id == room.id
                && Objects.equals(name, room.name)
                && Objects.equals(created, room.created)
                && Objects.equals(user, room.user)
                && Objects.equals(messages, room.messages);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, created, user, messages);
    }
}
