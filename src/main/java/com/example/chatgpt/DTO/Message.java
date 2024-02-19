package com.example.chatgpt.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class Message {
    private String role;
    private String content;

    public Message() {
    }
}
