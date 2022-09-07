package ru.job4j.dto;

import java.time.LocalDateTime;

public class MessageDTO {
    private int id;
    private String text;
    private int authorId;

    public MessageDTO() {
    }

    public MessageDTO(int id, String text, LocalDateTime created, int authorId) {
        this.id = id;
        this.text = text;
        this.authorId = authorId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getAuthorId() {
        return authorId;
    }

    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }

}
