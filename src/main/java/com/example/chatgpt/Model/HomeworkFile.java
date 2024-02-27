package com.example.chatgpt.Model;

import com.example.chatgpt.DTO.HomeworkFileDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class HomeworkFile implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(nullable = false)
    private String fileName;
    private String fileType;
    @Lob
    @Column(length = 10240000)
    private byte[] data;

    public HomeworkFile(String fileName, String fileType, byte[] data) {
        this.fileName = fileName;
        this.fileType = fileType;
        this.data = data;
    }

    public HomeworkFileDto toDto() {
        return new HomeworkFileDto(this.id, this.fileName, this.fileType, this.data);
    }
}
