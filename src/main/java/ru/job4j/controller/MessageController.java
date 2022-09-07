package ru.job4j.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.job4j.dto.MessageDTO;
import ru.job4j.handler.Operation;
import ru.job4j.model.Message;
import ru.job4j.model.Room;
import ru.job4j.model.User;
import ru.job4j.service.MessageService;
import ru.job4j.service.RoomService;
import ru.job4j.service.UserService;

import java.time.LocalDateTime;

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
                                          @Validated(Operation.OnCreate.class) @RequestBody Message message) {

        Room room = roomService.findById(idRoom);
        User currentUser = userService.findByName(SecurityContextHolder.getContext().getAuthentication().getName());
        message.setUser(currentUser);
        message.setCreated(LocalDateTime.now());
        room.addMessage(message);
        return new ResponseEntity<>(
                roomService.save(room),
                HttpStatus.CREATED
        );
    }

    @PatchMapping("/patch")
    public ResponseEntity<Message> addMessage(@RequestBody MessageDTO messageDTO) {
        return new ResponseEntity<>(
                messageService.patch(messageDTO),
                HttpStatus.OK
        );
    }
}

