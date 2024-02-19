package com.example.chatgpt.Service;


import com.example.chatgpt.Model.HomeworkFile;
import org.springframework.web.multipart.MultipartFile;

public interface FileServiceInterface {
    void saveAttachment(MultipartFile file) throws Exception;

    HomeworkFile getFile(Long id);
}
