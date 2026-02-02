package com.bootcamp.training.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

public class NilaiMuridDTO {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class NilaiMuridResponse {
        private Long id;
        private Long idMapel;
        private String namaMapel;
        private Long idMurid;
        private String namaMurid;
        private Double nilai;
        private Integer semester;
        
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        private LocalDateTime createdDate;
        
        private String createdBy;
        private Boolean isDeleted;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class NilaiMuridDetail {
        private Long id;
        private Long idMapel;
        private String namaMapel;
        private String guruMapel;
        private Long idMurid;
        private String namaMurid;
        private String kelasMurid;
        private Integer umurMurid;
        private Double nilai;
        private Integer semester;
        
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        private LocalDateTime createdDate;
        
        private String createdBy;
        private Boolean isDeleted;
        
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        private LocalDateTime deletedDate;
        
        private String deletedBy;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class NilaiMuridCreate {
        private Long idMapel;
        private Long idMurid;
        private Double nilai;
        private Integer semester;
        private String createdBy;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class NilaiMuridUpdate {
        private Double nilai;
        private Integer semester;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class NilaiMuridSoftDelete {
        private String deletedBy;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class NilaiMuridFilter {
        private Long idMurid;
        private Long idMapel;
        private Integer semester;
        private Integer tahunAjaran;
        private Double nilaiMin;
        private Double nilaiMax;
        private Boolean includeDeleted;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class NilaiMuridDeleteResponse {
        private Long id;
        private String message;
        
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        private LocalDateTime deletedDate;
        
        private String deletedBy;
    }
    
}
