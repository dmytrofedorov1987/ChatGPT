package com.example.chatgpt.Service;

import com.example.chatgpt.DTO.HomeworkFileDto;
import com.example.chatgpt.Model.HomeworkFile;
import com.example.chatgpt.Repository.FileRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;


@Service
public class FileService implements FileServiceInterface {

    private final FileRepository fileRepository;

    public FileService(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    @Override
    @Transactional
    public void saveAttachment(MultipartFile file) throws Exception {

        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        try {
            if (fileName.contains("..")) {
                throw new Exception("Filename contains invalid path sequence " + fileName);
            }
            if (file.getBytes().length > 10240000) {
                throw new Exception("File size exceeds maximum limit");
            }
            HomeworkFile attachment = new HomeworkFile(fileName, file.getContentType(), file.getBytes());
            fileRepository.save(attachment);
        } catch (MaxUploadSizeExceededException e) {
            throw new MaxUploadSizeExceededException(file.getSize());
        } catch (Exception e) {
            throw new Exception("Could not save File: " + fileName);
        }
    }

    @Override
    @Transactional
    public HomeworkFileDto getFile(Long id) {
       HomeworkFile homeworkFile = fileRepository.findById(id).orElseThrow(() -> new NullPointerException("File is not fount"));
       return homeworkFile.toDto();
    }
}
