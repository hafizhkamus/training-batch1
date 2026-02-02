package com.bootcamp.training.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

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

    @Column(name = "nilai", precision = 10, scale = 2)
    private Double nilai;

    @Column(name = "semester", nullable = false)
    private Integer semester;

    @CreationTimestamp
    @Column(name = "created_date", updatable = false)
    private LocalDateTime createdDate;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "is_deleted")
    private Boolean isDeleted = false;

    @Column(name = "deleted_date")
    private LocalDateTime deletedDate;

    @Column(name = "deleted_by")
    private String deletedBy;
}