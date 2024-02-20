package com.example.chatgpt.Controller;

import com.example.chatgpt.DTO.ChatRequestDto;
import com.example.chatgpt.DTO.ChatResponseDto;
import com.example.chatgpt.Service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class ChatController {
    private final FileService fileService;
    @Autowired
    @Qualifier("openaiRestTemplate")
    private RestTemplate restTemplate;

    @Value(value = "${openai.model}")
    private String model;

    @Value(value = "${openai.api.url}")
    private String apiUrl;
    private String s = "Look at the code and answer \"OK\" if the code is correct, if the code is not correct, advise what to fix. task conditions: " + "\n";


    @PostMapping("/chat")
    public String chat(@RequestPart("file") MultipartFile file) throws Exception {

        if (file.isEmpty()) {
            throw new NullPointerException("File is empty or not select.");
        }
        if (!checkFile(file)) {
            throw new IllegalArgumentException("Not correct file's format.");
        }
        String text = "";
        try {
            InputStream initialStream = file.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(initialStream, StandardCharsets.UTF_8));
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line).append("\n");
            }
            bufferedReader.close();
            text = s + stringBuilder.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }

        ChatRequestDto request = new ChatRequestDto(model, text);
        ChatResponseDto response = restTemplate.postForObject(apiUrl, request, ChatResponseDto.class);
        if (response == null || response.getChoices() == null || response.getChoices().isEmpty()) {
            return "No response";
        }
        if (response.getChoices().get(0).getMessage().getContent().equals("OK")) {
            fileService.saveAttachment(file);
        } else {
            return response.getChoices().get(0).getMessage().getContent();
        }
        return "Homework is correct and save.";
    }

    public static boolean checkFile(MultipartFile file) {
        String fileName = file.getOriginalFilename();
        if (fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0) {
            String typeFile = fileName.substring(fileName.lastIndexOf(".") + 1);
            List<String> format = List.of("doc", "docx", "docm", "dot", "dotx", "txt");
            if (format.stream().anyMatch(a -> a.equals(typeFile))) {
                return true;
            }
        }
        return false;
    }

}