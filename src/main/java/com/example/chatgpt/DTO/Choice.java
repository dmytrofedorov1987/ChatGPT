package com.example.chatgpt.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class Choice {
    private int index;
    private Message message;

    public Choice() {
    }
}

