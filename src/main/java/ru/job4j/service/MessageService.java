package ru.job4j.service;

import org.springframework.stereotype.Service;
import ru.job4j.model.Message;
import ru.job4j.repository.MessageRepository;

@Service
public class MessageService {

    private final MessageRepository messageRepository;

    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public Message saveOrUpdate(Message message) {
        return messageRepository.save(message);
    }

}
