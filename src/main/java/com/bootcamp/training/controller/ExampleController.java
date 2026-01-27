package com.bootcamp.training.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bootcamp.training.dto.ExampleDTO;
import com.bootcamp.training.dto.ExampleDTO.PelajaranResponse;
import com.bootcamp.training.dto.ExampleDTO.StudentResponse;

@RestController
@RequestMapping(path = "/example")
public class ExampleController {

    private final Random random = new Random();

    @GetMapping("/")
    public String home() {
        return "Training Application is running!";
    }

    @GetMapping("/api/hello")
    public String hello() {
        return "Hello from Spring Boot 3.5!";
    }

    @GetMapping("/api/greet")
    public String greetWithRandomNumber(@RequestParam(name = "name", defaultValue = "Guest") String name, 
    @RequestParam(name = "age", defaultValue = "20") Integer age) {
        // int randomNumber = random.nextInt(10000000);
        return String.format("name : %s, age : %d", name, age);
    }

    @GetMapping("/api/list-nama")
    public List<ExampleDTO.StudentResponse> listNama(@RequestParam(name = "totalStudent", defaultValue = "10") Integer totalStudent) {
        List<ExampleDTO.StudentResponse> responseList = new ArrayList<>();
        Random random = new Random();
        String[] subjects = {"IPA", "IPS", "BAHASA"};
        
        for (int i = 1; i <= totalStudent; i++) {
            List<PelajaranResponse> pelajaranList = new ArrayList<>();
            
            for (String subject : subjects) {
                int nilai = random.nextInt(101);
                PelajaranResponse pelajaran = new PelajaranResponse(subject, nilai);
                pelajaranList.add(pelajaran);
            }
            
            double average = pelajaranList.stream()
                .mapToInt(PelajaranResponse::getNilai)
                .average()
                .orElse(0.0);
            
            StudentResponse student = new StudentResponse(
                "murid" + i,
                (int) Math.round(average),
                pelajaranList
            );
            
            responseList.add(student);
        }
        
        return responseList;
    }


}