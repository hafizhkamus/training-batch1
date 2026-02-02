package com.bootcamp.training.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "nilai_murid")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NilaiMurid {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "id_mapel", nullable = false)
    private Long idMapel;

    @Column(name = "id_murid", nullable = false)
    private Long idMurid;

    @Column(name = "nilai", columnDefinition = "NUMERIC(10,2)")
    private Double nilai;

    @Column(name = "semester", nullable = false)
    private Integer semester;

    @CreationTimestamp
    @Column(name = "created_date", updatable = false)
    private LocalDateTime createdDate;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "is_deleted")
    private Boolean isDeleted;

    @Column(name = "deleted_date")
    private LocalDateTime deletedDate;

    @Column(name = "deleted_by")
    private String deletedBy;
}