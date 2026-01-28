package com.bootcamp.training.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bootcamp.training.dto.TestDTO;
import com.bootcamp.training.service.TestService;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@RestController
@RequestMapping(path = "/test")
public class TestController {

    @Autowired
    private TestService testService;

    @GetMapping("/api/greet/json")
    public GreetingResponse greetJson(
            @RequestParam(name = "name", defaultValue = "Guest") String name,
            @RequestParam(name = "age", defaultValue = "20") Integer age) {
        GreetingResponse hilman = new GreetingResponse();
        return hilman;
    }
    
    @GetMapping("/api/greet/json2")
    public String greetJson2(
            @RequestParam(name = "name", defaultValue = "Guest") String name,
            @RequestParam(name = "age", defaultValue = "20") Integer age) {
        GreetingResponse hilman = new GreetingResponse();
        GreetingResponse hilman2 = new GreetingResponse(
            name + " KELAS B", 
            age + 180  
        );
        hilman2.setName("INI DIUBAH LAGI");
        hilman.setName(name + " KELAS B");
        hilman.setAge(age + 180);
        return hilman2.getName();
    }

    
    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    public static class GreetingResponse {
        private String name;
        private int age;
    }

    @PostMapping("/api/analisis-profesi")
    public TestDTO.ProfesiResponse analisisProfesi(@RequestBody TestDTO.DataSiswaRequest request) {
        return testService.analisis(request);
    }
}