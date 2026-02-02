package com.bootcamp.training.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.bootcamp.training.dao.KelasDao;
import com.bootcamp.training.dao.MuridDao;
import com.bootcamp.training.dto.MuridDTO;

@Service
public class MuridService {

    @Autowired
    private MuridDao dao;

    @Autowired
    private KelasDao kelasDao;

    public int save(MuridDTO.MuridCreate value) throws SQLException {
        // Validation: Check if kelas exists
        if (value.getIdKelas() != null && !kelasDao.existsById(value.getIdKelas())) {
            throw new IllegalArgumentException("Kelas dengan ID " + value.getIdKelas() + " tidak ditemukan");
        }
        
        // Additional validation
        if (value.getNama() == null || value.getNama().trim().isEmpty()) {
            throw new IllegalArgumentException("Nama murid harus diisi");
        }
        
        if (value.getUmur() != null && (value.getUmur() < 4 || value.getUmur() > 25)) {
            throw new IllegalArgumentException("Umur murid harus antara 4 - 25 tahun");
        }
        
        return dao.save(value);
    }

    public int delete(Long id) throws DataAccessException {
        // Check if murid exists before deleting
        Optional<MuridDTO.MuridDetail> existing = dao.findById(id);
        if (existing.isEmpty()) {
            throw new IllegalArgumentException("Murid dengan ID " + id + " tidak ditemukan");
        }
        
        return dao.delete(id);
    }

    public int update(Long id, MuridDTO.MuridUpdate value) throws DataAccessException {
        // Check if murid exists
        Optional<MuridDTO.MuridDetail> existing = dao.findById(id);
        if (existing.isEmpty()) {
            throw new IllegalArgumentException("Murid dengan ID " + id + " tidak ditemukan");
        }
        
        // Validation: Check if kelas exists
        if (value.getIdKelas() != null && !kelasDao.existsById(value.getIdKelas())) {
            throw new IllegalArgumentException("Kelas dengan ID " + value.getIdKelas() + " tidak ditemukan");
        }
        
        // Additional validation
        if (value.getNama() != null && value.getNama().trim().isEmpty()) {
            throw new IllegalArgumentException("Nama murid tidak boleh kosong");
        }
        
        if (value.getUmur() != null && (value.getUmur() < 4 || value.getUmur() > 25)) {
            throw new IllegalArgumentException("Umur murid harus antara 4 - 25 tahun");
        }
        
        return dao.update(id, value);
    }

    public Optional<MuridDTO.MuridDetail> findById(Long id) throws EmptyResultDataAccessException {
        return dao.findById(id);
    }

    public List<MuridDTO.MuridResponse> findAll() throws EmptyResultDataAccessException {
        return dao.findAll();
    }

    public List<MuridDTO.MuridResponse> findByKelasId(Long kelasId) {
        // Check if kelas exists
        if (!kelasDao.existsById(kelasId)) {
            throw new IllegalArgumentException("Kelas dengan ID " + kelasId + " tidak ditemukan");
        }
        
        return dao.findByKelasId(kelasId);
    }
}
