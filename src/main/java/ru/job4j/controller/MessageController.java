package ru.job4j.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.job4j.model.Message;
import ru.job4j.model.Room;
import ru.job4j.model.User;
import ru.job4j.service.MessageService;
import ru.job4j.service.RoomService;
import ru.job4j.service.UserService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

@RestController
@RequestMapping("/message")
public class MessageController {

    private final RoomService roomService;
    private final UserService userService;
    private final MessageService messageService;

    public MessageController(RoomService roomService, UserService userService, MessageService messageService) {
        this.roomService = roomService;
        this.userService = userService;
        this.messageService = messageService;
    }

    @PostMapping("/")
    public ResponseEntity<Room> addMessage(@RequestParam("idRoom") int idRoom,
                                           @RequestBody Message message) {
        Room room = roomService.findById(idRoom);
        User user = userService.findById(message.getUser().getId());
        message.setUser(user);
        message.setCreated(LocalDateTime.now());
        room.addMessage(message);
        return new ResponseEntity<>(
                roomService.save(room),
                HttpStatus.CREATED
        );
     }
}
