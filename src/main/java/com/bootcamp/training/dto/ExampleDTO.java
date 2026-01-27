package com.bootcamp.training.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class ExampleDTO {
    
    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    public static class StudentResponse {
        private String murid;
        private int nem;
        private List<PelajaranResponse> pelajaran;
    }


    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    public static class PelajaranResponse {
        private String mapel;
        private int nilai;
    }

}




