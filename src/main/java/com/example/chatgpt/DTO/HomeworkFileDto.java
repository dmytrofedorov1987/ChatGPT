package com.example.chatgpt.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HomeworkFileDto {
    Long id;
    private String fileName;
    private String fileType;
    private byte[] data;

    public HomeworkFileDto() {
    }

    public HomeworkFileDto(Long id, String fileName, String fileType, byte[] data) {
        this.id = id;
        this.fileName = fileName;
        this.fileType = fileType;
        this.data = data;
    }
}
