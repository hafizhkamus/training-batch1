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
@Table(name = "murid")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Murid {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "nama", nullable = false)
    private String nama;

    @Column(name = "id_kelas")
    private Long idKelas;

    @Column(name = "umur")
    private Integer umur;

    @CreationTimestamp
    @Column(name = "tanggal_masuk", updatable = false)
    private LocalDateTime tanggalMasuk;
}