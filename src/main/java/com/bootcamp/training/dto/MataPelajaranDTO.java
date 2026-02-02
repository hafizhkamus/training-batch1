package com.bootcamp.training.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

public class MataPelajaranDTO {
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class MataPelajaranResponse {
        private Long id;
        private String namaMapel;
        private String guru;
        private Integer jumlahNilai;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class MataPelajaranDetail {
        private Long id;
        private String namaMapel;
        private String guru;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class MataPelajaranCreate {
        private String namaMapel;
        private String guru;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class MataPelajaranUpdate {
        private String namaMapel;
        private String guru;
    }
}
