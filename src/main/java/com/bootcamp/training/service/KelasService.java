package com.bootcamp.training.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.bootcamp.training.dao.KelasDao;
import com.bootcamp.training.dto.KelasDTO;

@Service
public class KelasService {

    @Autowired
    private KelasDao dao;

    public int save(KelasDTO.KelasCreate value) throws SQLException {
        // Validation
        if (value.getNamaKelas() == null || value.getNamaKelas().trim().isEmpty()) {
            throw new IllegalArgumentException("Nama kelas harus diisi");
        }
        
        if (value.getNamaKelas().length() > 100) {
            throw new IllegalArgumentException("Nama kelas maksimal 100 karakter");
        }
        
        return dao.save(value);
    }

    public int delete(Long id) throws DataAccessException {
        // Check if kelas exists before deleting
        Optional<KelasDTO.KelasDetail> existing = dao.findById(id);
        if (existing.isEmpty()) {
            throw new IllegalArgumentException("Kelas dengan ID " + id + " tidak ditemukan");
        }
        
        // TODO: You might want to check if there are murid in this kelas
        // before allowing deletion
        
        return dao.delete(id);
    }

    public int update(Long id, KelasDTO.KelasUpdate value) throws DataAccessException {
        // Check if kelas exists
        Optional<KelasDTO.KelasDetail> existing = dao.findById(id);
        if (existing.isEmpty()) {
            throw new IllegalArgumentException("Kelas dengan ID " + id + " tidak ditemukan");
        }
        
        // Validation
        if (value.getNamaKelas() != null) {
            if (value.getNamaKelas().trim().isEmpty()) {
                throw new IllegalArgumentException("Nama kelas tidak boleh kosong");
            }
            
            if (value.getNamaKelas().length() > 100) {
                throw new IllegalArgumentException("Nama kelas maksimal 100 karakter");
            }
        }
        
        return dao.update(id, value);
    }

    public Optional<KelasDTO.KelasDetail> findById(Long id) throws EmptyResultDataAccessException {
        return dao.findById(id);
    }

    public List<KelasDTO.KelasResponse> findAll() throws EmptyResultDataAccessException {
        return dao.findAll();
    }
}
