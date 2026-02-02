package com.bootcamp.training.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


public class MuridDTO {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class MuridResponse {
        private Long id;
        private String nama;
        private Long idKelas;
        private String namaKelas;
        private Integer umur;
        
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        private LocalDateTime tanggalMasuk;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class MuridDetail {
        private Long id;
        private String nama;
        private Long idKelas;
        private String namaKelas;
        private Integer umur;
        
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        private LocalDateTime tanggalMasuk;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class MuridCreate {
        private String nama;
        private Long idKelas;
        private Integer umur;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class MuridUpdate {
        private String nama;
        private Long idKelas;
        private Integer umur;
    }
}
