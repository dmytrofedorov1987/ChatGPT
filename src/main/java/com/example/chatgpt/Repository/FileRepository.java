package com.example.chatgpt.Repository;


import com.example.chatgpt.Model.HomeworkFile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<HomeworkFile, Long> {


}
