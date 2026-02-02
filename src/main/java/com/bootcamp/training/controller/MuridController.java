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

import com.bootcamp.training.dto.MuridDTO;
import com.bootcamp.training.service.MuridService;

@RestController
@RequestMapping(path = "/api/murid")
public class MuridController {

    @Autowired
    private MuridService service;

    @PostMapping(path = "/addNew")
    public ResponseEntity<?> save(@RequestBody MuridDTO.MuridCreate value) {
        try {
            int result = service.save(value);
            if (result > 0) {
                return new ResponseEntity<>("Murid berhasil ditambahkan", HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>("Gagal menambahkan murid", HttpStatus.BAD_REQUEST);
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
                return new ResponseEntity<>("Murid berhasil dihapus", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Gagal menghapus murid", HttpStatus.BAD_REQUEST);
            }
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (DataAccessException e) {
            return new ResponseEntity<>("Error database: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Long id, @RequestBody MuridDTO.MuridUpdate value) {
        try {
            int result = service.update(id, value);
            if (result > 0) {
                return new ResponseEntity<>("Murid berhasil diupdate", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Gagal mengupdate murid", HttpStatus.BAD_REQUEST);
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
            Optional<MuridDTO.MuridDetail> murid = service.findById(id);
            if (murid.isPresent()) {
                return new ResponseEntity<>(murid.get(), HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Murid tidak ditemukan", HttpStatus.NOT_FOUND);
            }
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (EmptyResultDataAccessException e) {
            return new ResponseEntity<>("Murid tidak ditemukan", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/findAll")
    public ResponseEntity<?> findAll() {
        try {
            List<MuridDTO.MuridResponse> muridList = service.findAll();
            return new ResponseEntity<>(muridList, HttpStatus.OK);
        } catch (EmptyResultDataAccessException e) {
            return new ResponseEntity<>(List.of(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/findByKelas/{kelasId}")
    public ResponseEntity<?> findByKelasId(@PathVariable("kelasId") Long kelasId) {
        try {
            List<MuridDTO.MuridResponse> muridList = service.findByKelasId(kelasId);
            return new ResponseEntity<>(muridList, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("Error: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}