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

import com.bootcamp.training.dto.MataPelajaranDTO;
import com.bootcamp.training.service.MataPelajaranService;

@RestController
@RequestMapping(path = "/api/mata-pelajaran")
public class MataPelajaranController {

    @Autowired
    private MataPelajaranService service;

    @PostMapping(path = "/addNew")
    public ResponseEntity<?> save(@RequestBody MataPelajaranDTO.MataPelajaranCreate value) {
        try {
            int result = service.save(value);
            if (result > 0) {
                return new ResponseEntity<>("Mata pelajaran berhasil ditambahkan", HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>("Gagal menambahkan mata pelajaran", HttpStatus.BAD_REQUEST);
            }
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (SQLException e) {
            return new ResponseEntity<>("Error database: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        try {
            int result = service.delete(id);
            if (result > 0) {
                return new ResponseEntity<>("Mata pelajaran berhasil dihapus", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Gagal menghapus mata pelajaran", HttpStatus.BAD_REQUEST);
            }
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (DataAccessException e) {
            return new ResponseEntity<>("Error database: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Long id, @RequestBody MataPelajaranDTO.MataPelajaranUpdate value) {
        try {
            int result = service.update(id, value);
            if (result > 0) {
                return new ResponseEntity<>("Mata pelajaran berhasil diupdate", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Gagal mengupdate mata pelajaran", HttpStatus.BAD_REQUEST);
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
            Optional<MataPelajaranDTO.MataPelajaranDetail> mapel = service.findById(id);
            if (mapel.isPresent()) {
                return new ResponseEntity<>(mapel.get(), HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Mata pelajaran tidak ditemukan", HttpStatus.NOT_FOUND);
            }
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (EmptyResultDataAccessException e) {
            return new ResponseEntity<>("Mata pelajaran tidak ditemukan", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/findAll")
    public ResponseEntity<?> findAll() {
        try {
            List<MataPelajaranDTO.MataPelajaranResponse> mapelList = service.findAll();
            return new ResponseEntity<>(mapelList, HttpStatus.OK);
        } catch (EmptyResultDataAccessException e) {
            return new ResponseEntity<>(List.of(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
