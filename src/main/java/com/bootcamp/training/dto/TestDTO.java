package com.bootcamp.training.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class TestDTO {

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    public static class DataSiswaRequest {
        private String nama;
        private String kelas;
        private int umur;
        private double nilaiRataRata;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    public static class ProfesiResponse {
        private String nama;
        private String kelas;
        private int umur;
        private ProfesiKesimpulan kesimpulanProfesi;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    public static class ProfesiKesimpulan {
        private boolean dokter;
        private boolean guru;
        private boolean chef;
    }
}
