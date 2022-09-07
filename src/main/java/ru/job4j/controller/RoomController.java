package ru.job4j.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.job4j.dto.RoomDTO;
import ru.job4j.model.Room;
import ru.job4j.service.RoomService;
import ru.job4j.service.UserService;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/room")
public class RoomController {

    private final RoomService roomService;
    private final UserService userService;

    public RoomController(RoomService roomService, UserService userService) {
        this.roomService = roomService;
        this.userService = userService;
    }

    @GetMapping("/")
    public List<Room> findAll() {
        return roomService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Room> findById(@PathVariable("id") int id) {
        return new ResponseEntity<>(
                roomService.findById(id),
                HttpStatus.OK
        );
    }

    @PostMapping("/")
    public ResponseEntity<Room> createRoom(@RequestBody Room room) {
        if (room.getUser() == null) {
            throw new NullPointerException("User mustn't be empty.");
        }
        if (room.getName() == null) {
            throw new NullPointerException("Name mustn't be empty.");
        }
        userService.findById(room.getUser().getId());
        room.setCreated(LocalDate.now());
        return new ResponseEntity<>(
                roomService.save(room),
                HttpStatus.CREATED
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") int id) {
        roomService.deleteById(id);
        return new ResponseEntity<>(
                HttpStatus.OK
        );
    }

    @PatchMapping("/patch")
    public ResponseEntity<Room> patch(@RequestBody RoomDTO roomDTO) {
        return new ResponseEntity<>(
                roomService.patch(roomDTO),
                HttpStatus.OK
        );
    }
}
