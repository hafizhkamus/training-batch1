package com.bootcamp.training.controller;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bootcamp.training.dto.NilaiMuridDTO;
import com.bootcamp.training.service.NilaiMuridService;

@RestController
@RequestMapping(path = "/api/nilai-murid")
public class NilaiMuridController {

    @Autowired
    private NilaiMuridService service;

    @PostMapping(path = "/addNew")
    public ResponseEntity<?> save(@RequestBody NilaiMuridDTO.NilaiMuridCreate value) {
        try {
            int result = service.save(value);
            if (result > 0) {
                return new ResponseEntity<>("Nilai murid berhasil ditambahkan", HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>("Gagal menambahkan nilai murid", HttpStatus.BAD_REQUEST);
            }
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (SQLException e) {
            return new ResponseEntity<>("Error database: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/softDelete/{id}")
    public ResponseEntity<?> softDelete(@PathVariable("id") Long id, @RequestBody NilaiMuridDTO.NilaiMuridSoftDelete value) {
        try {
            NilaiMuridDTO.NilaiMuridDeleteResponse response = service.softDelete(id, value);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (DataAccessException e) {
            return new ResponseEntity<>("Error database: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/hardDelete/{id}")
    public ResponseEntity<?> hardDelete(@PathVariable("id") Long id) {
        try {
            int result = service.hardDelete(id);
            if (result > 0) {
                return new ResponseEntity<>("Nilai murid berhasil dihapus permanen", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Gagal menghapus nilai murid", HttpStatus.BAD_REQUEST);
            }
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (DataAccessException e) {
            return new ResponseEntity<>("Error database: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Long id, @RequestBody NilaiMuridDTO.NilaiMuridUpdate value) {
        try {
            int result = service.update(id, value);
            if (result > 0) {
                return new ResponseEntity<>("Nilai murid berhasil diupdate", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Gagal mengupdate nilai murid", HttpStatus.BAD_REQUEST);
            }
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (DataAccessException e) {
            return new ResponseEntity<>("Error database: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/findById/{id}")
    public ResponseEntity<?> findById(@PathVariable("id") Long id) {
        try {
            Optional<NilaiMuridDTO.NilaiMuridDetail> nilai = service.findById(id);
            if (nilai.isPresent()) {
                return new ResponseEntity<>(nilai.get(), HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Nilai murid tidak ditemukan", HttpStatus.NOT_FOUND);
            }
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (EmptyResultDataAccessException e) {
            return new ResponseEntity<>("Nilai murid tidak ditemukan", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/findAll")
    public ResponseEntity<?> findAll() {
        try {
            List<NilaiMuridDTO.NilaiMuridResponse> nilaiList = service.findAll();
            return new ResponseEntity<>(nilaiList, HttpStatus.OK);
        } catch (EmptyResultDataAccessException e) {
            return new ResponseEntity<>(List.of(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/findByMurid/{muridId}")
    public ResponseEntity<?> findByMuridId(@PathVariable("muridId") Long muridId) {
        try {
            List<NilaiMuridDTO.NilaiMuridResponse> nilaiList = service.findByMuridId(muridId);
            return new ResponseEntity<>(nilaiList, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("Error: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/findByMapel/{mapelId}")
    public ResponseEntity<?> findByMapelId(@PathVariable("mapelId") Long mapelId) {
        try {
            List<NilaiMuridDTO.NilaiMuridResponse> nilaiList = service.findByMapelId(mapelId);
            return new ResponseEntity<>(nilaiList, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("Error: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/findByFilter")
    public ResponseEntity<?> findByFilter(@RequestBody NilaiMuridDTO.NilaiMuridFilter filter) {
        try {
            List<NilaiMuridDTO.NilaiMuridResponse> nilaiList = service.findAllWithFilter(filter);
            return new ResponseEntity<>(nilaiList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/average/{muridId}")
    public ResponseEntity<?> getAverageNilai(@PathVariable("muridId") Long muridId) {
        try {
            Double average = service.calculateAverageNilai(muridId);
            if (average != null) {
                return new ResponseEntity<>(String.format("Rata-rata nilai murid: %.2f", average), HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Tidak ada nilai untuk murid ini", HttpStatus.OK);
            }
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("Error: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/average/{muridId}/semester/{semester}")
    public ResponseEntity<?> getAverageNilaiPerSemester(@PathVariable("muridId") Long muridId, 
                                                       @PathVariable("semester") Integer semester) {
        try {
            Double average = service.calculateAverageNilaiPerSemester(muridId, semester);
            if (average != null) {
                return new ResponseEntity<>(String.format("Rata-rata nilai semester %d: %.2f", semester, average), HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Tidak ada nilai untuk semester ini", HttpStatus.OK);
            }
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("Error: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
