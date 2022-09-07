package ru.job4j.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.job4j.dto.MessageDTO;
import ru.job4j.model.Message;
import ru.job4j.repository.MessageRepository;

import java.util.Optional;

@Service
public class MessageService {

    private final MessageRepository messageRepository;

    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public Message saveOrUpdate(Message message) {
        return messageRepository.save(message);
    }

    public Message patch(MessageDTO messageDTO) {
        Optional<Message> message = messageRepository.findById(messageDTO.getId());
        if (message.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Message not found");
        }
        Message currentMessage = message.get();
        Optional.of(messageDTO.getText()).ifPresent(currentMessage::setText);
        saveOrUpdate(currentMessage);
        return currentMessage;
    }

}
