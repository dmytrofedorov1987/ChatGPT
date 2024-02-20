package com.example.chatgpt.Service;


import com.example.chatgpt.DTO.HomeworkFileDto;
import org.springframework.web.multipart.MultipartFile;

public interface FileServiceInterface {
    void saveAttachment(MultipartFile file) throws Exception;

    HomeworkFileDto getFile(Long id);
}
