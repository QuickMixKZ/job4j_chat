package ru.job4j.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.job4j.dto.RoomDTO;
import ru.job4j.model.Room;
import ru.job4j.model.User;
import ru.job4j.repository.RoomRepository;

import java.util.List;
import java.util.Optional;

@Service
public class RoomService {
    private final RoomRepository roomRepository;
    private final UserService userService;

    public RoomService(RoomRepository roomRepository, UserService userService) {
        this.roomRepository = roomRepository;
        this.userService = userService;
    }

    public List<Room> findAll() {
        return (List<Room>) roomRepository.findAll();
    }

    public Room findById(int id) {
        Optional<Room> room = roomRepository.findById(id);
        if (room.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Room not found.");
        }
        return room.get();
    }

    public Room save(Room room) {
        Optional<Room> roomDb = roomRepository.findByName(room.getName());
        if (roomDb.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Room with such name already exists");
        }
        return roomRepository.save(room);
    }

    public void deleteById(int id) {
        if (!roomRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Room not found");
        }
        roomRepository.deleteById(id);
    }

    public Room patch(RoomDTO roomDTO) {
        Optional<Room> room = roomRepository.findById(roomDTO.getId());
        if (room.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Room not found");
        }
        Room currentRoom = room.get();
        Optional.ofNullable(roomDTO.getName()).ifPresent(currentRoom::setName);
        Optional.of(roomDTO.getCreatorId()).ifPresent(userId -> {
            if (userId != 0) {
                User user = userService.findById(userId);
                currentRoom.setUser(user);
            }
        });
        roomRepository.save(currentRoom);
        return currentRoom;
    }
}
