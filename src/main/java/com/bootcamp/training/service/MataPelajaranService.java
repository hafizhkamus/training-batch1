package com.bootcamp.training.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.bootcamp.training.dao.MataPelajaranDao;
import com.bootcamp.training.dto.MataPelajaranDTO;

@Service
public class MataPelajaranService {

    @Autowired
    private MataPelajaranDao dao;

    public int save(MataPelajaranDTO.MataPelajaranCreate value) throws SQLException {
        // Validation
        if (value.getNamaMapel() == null || value.getNamaMapel().trim().isEmpty()) {
            throw new IllegalArgumentException("Nama mata pelajaran harus diisi");
        }
        
        if (value.getNamaMapel().length() > 255) {
            throw new IllegalArgumentException("Nama mata pelajaran maksimal 255 karakter");
        }
        
        if (value.getGuru() != null && value.getGuru().length() > 255) {
            throw new IllegalArgumentException("Nama guru maksimal 255 karakter");
        }
        
        return dao.save(value);
    }

    public int delete(Long id) throws DataAccessException {
        // Check if mata pelajaran exists before deleting
        Optional<MataPelajaranDTO.MataPelajaranDetail> existing = dao.findById(id);
        if (existing.isEmpty()) {
            throw new IllegalArgumentException("Mata pelajaran dengan ID " + id + " tidak ditemukan");
        }
        
        // TODO: You might want to check if there are nilai records for this mata pelajaran
        // before allowing deletion
        
        return dao.delete(id);
    }

    public int update(Long id, MataPelajaranDTO.MataPelajaranUpdate value) throws DataAccessException {
        // Check if mata pelajaran exists
        Optional<MataPelajaranDTO.MataPelajaranDetail> existing = dao.findById(id);
        if (existing.isEmpty()) {
            throw new IllegalArgumentException("Mata pelajaran dengan ID " + id + " tidak ditemukan");
        }
        
        // Validation
        if (value.getNamaMapel() != null) {
            if (value.getNamaMapel().trim().isEmpty()) {
                throw new IllegalArgumentException("Nama mata pelajaran tidak boleh kosong");
            }
            
            if (value.getNamaMapel().length() > 255) {
                throw new IllegalArgumentException("Nama mata pelajaran maksimal 255 karakter");
            }
        }
        
        if (value.getGuru() != null && value.getGuru().length() > 255) {
            throw new IllegalArgumentException("Nama guru maksimal 255 karakter");
        }
        
        return dao.update(id, value);
    }

    public Optional<MataPelajaranDTO.MataPelajaranDetail> findById(Long id) throws EmptyResultDataAccessException {
        return dao.findById(id);
    }

    public List<MataPelajaranDTO.MataPelajaranResponse> findAll() throws EmptyResultDataAccessException {
        return dao.findAll();
    }
}
