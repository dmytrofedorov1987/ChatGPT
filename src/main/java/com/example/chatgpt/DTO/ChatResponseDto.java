package com.example.chatgpt.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
public class ChatResponseDto {
    private List<Choice> choices;

    public ChatResponseDto() {
    }

}


