package com.bootcamp.training.service;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.bootcamp.training.dao.MataPelajaranDao;
import com.bootcamp.training.dao.MuridDao;
import com.bootcamp.training.dao.NilaiMuridDao;
import com.bootcamp.training.dto.MataPelajaranDTO;
import com.bootcamp.training.dto.MuridDTO;
import com.bootcamp.training.dto.NilaiMuridDTO;

@Service
public class NilaiMuridService {

    @Autowired
    private NilaiMuridDao dao;

    @Autowired
    private MuridDao muridDao;

    @Autowired
    private MataPelajaranDao mapelDao;

    public int save(NilaiMuridDTO.NilaiMuridCreate value) throws SQLException {
        Optional<MuridDTO.MuridDetail> murid = muridDao.findById(value.getIdMurid());
        if (murid.isEmpty()) {
            throw new IllegalArgumentException("Murid dengan ID " + value.getIdMurid() + " tidak ditemukan");
        }
        
        Optional<MataPelajaranDTO.MataPelajaranDetail> mapel = mapelDao.findById(value.getIdMapel());
        if (mapel.isEmpty()) {
            throw new IllegalArgumentException("Mata pelajaran dengan ID " + value.getIdMapel() + " tidak ditemukan");
        }
        
        if (value.getNilai() != null) {
            if (value.getNilai() < 0 || value.getNilai() > 100) {
                throw new IllegalArgumentException("Nilai harus antara 0 - 100");
            }
        }
        
        if (value.getSemester() == null || value.getSemester() < 1 || value.getSemester() > 8) {
            throw new IllegalArgumentException("Semester harus antara 1 - 8");
        }
        
        if (value.getCreatedBy() == null || value.getCreatedBy().trim().isEmpty()) {
            value.setCreatedBy("SYSTEM");
        }
        
        return dao.save(value);
    }

    public NilaiMuridDTO.NilaiMuridDeleteResponse softDelete(Long id, NilaiMuridDTO.NilaiMuridSoftDelete value) throws DataAccessException {
        // Check if nilai exists
        Optional<NilaiMuridDTO.NilaiMuridDetail> existing = dao.findById(id);
        if (existing.isEmpty()) {
            throw new IllegalArgumentException("Nilai dengan ID " + id + " tidak ditemukan");
        }
        
        // Check if already deleted
        if (existing.get().getIsDeleted()) {
            throw new IllegalArgumentException("Nilai dengan ID " + id + " sudah dihapus sebelumnya");
        }
        
        // Set deletedBy if not provided
        if (value.getDeletedBy() == null || value.getDeletedBy().trim().isEmpty()) {
            value.setDeletedBy("SYSTEM");
        }
        
        // Perform soft delete
        int rowsAffected = dao.softDelete(id, value);
        
        if (rowsAffected > 0) {
            NilaiMuridDTO.NilaiMuridDeleteResponse response = new NilaiMuridDTO.NilaiMuridDeleteResponse();
            response.setId(id);
            response.setMessage("Nilai berhasil dihapus (soft delete)");
            response.setDeletedDate(LocalDateTime.now());
            response.setDeletedBy(value.getDeletedBy());
            return response;
        } else {
            throw new DataAccessException("Gagal melakukan soft delete") {};
        }
    }

    public int hardDelete(Long id) throws DataAccessException {
        // Check if nilai exists
        Optional<NilaiMuridDTO.NilaiMuridDetail> existing = dao.findById(id);
        if (existing.isEmpty()) {
            throw new IllegalArgumentException("Nilai dengan ID " + id + " tidak ditemukan");
        }
        
        return dao.hardDelete(id);
    }

    public int update(Long id, NilaiMuridDTO.NilaiMuridUpdate value) throws DataAccessException {
        // Check if nilai exists
        Optional<NilaiMuridDTO.NilaiMuridDetail> existing = dao.findById(id);
        if (existing.isEmpty()) {
            throw new IllegalArgumentException("Nilai dengan ID " + id + " tidak ditemukan");
        }
        
        // Check if already deleted
        if (existing.get().getIsDeleted()) {
            throw new IllegalArgumentException("Tidak dapat mengupdate nilai yang sudah dihapus");
        }
        
        // Additional validation
        if (value.getNilai() != null) {
            if (value.getNilai() < 0 || value.getNilai() > 100) {
                throw new IllegalArgumentException("Nilai harus antara 0 - 100");
            }
        }
        
        if (value.getSemester() != null && (value.getSemester() < 1 || value.getSemester() > 8)) {
            throw new IllegalArgumentException("Semester harus antara 1 - 8");
        }
        
        return dao.update(id, value);
    }

    public Optional<NilaiMuridDTO.NilaiMuridDetail> findById(Long id) throws EmptyResultDataAccessException {
        return dao.findById(id);
    }

    public List<NilaiMuridDTO.NilaiMuridResponse> findAll() throws EmptyResultDataAccessException {
        return dao.findAll();
    }

    public List<NilaiMuridDTO.NilaiMuridResponse> findAllWithFilter(NilaiMuridDTO.NilaiMuridFilter filter) {
        return dao.findAllWithFilter(filter);
    }

    public List<NilaiMuridDTO.NilaiMuridResponse> findByMuridId(Long muridId) {
        // Check if murid exists
        Optional<MuridDTO.MuridDetail> murid = muridDao.findById(muridId);
        if (murid.isEmpty()) {
            throw new IllegalArgumentException("Murid dengan ID " + muridId + " tidak ditemukan");
        }
        
        return dao.findByMuridId(muridId);
    }

    public List<NilaiMuridDTO.NilaiMuridResponse> findByMapelId(Long mapelId) {
        // Check if mata pelajaran exists
        Optional<MataPelajaranDTO.MataPelajaranDetail> mapel = mapelDao.findById(mapelId);
        if (mapel.isEmpty()) {
            throw new IllegalArgumentException("Mata pelajaran dengan ID " + mapelId + " tidak ditemukan");
        }
        
        NilaiMuridDTO.NilaiMuridFilter filter = new NilaiMuridDTO.NilaiMuridFilter();
        filter.setIdMapel(mapelId);
        filter.setIncludeDeleted(false);
        
        return dao.findAllWithFilter(filter);
    }

    // Calculate average nilai for a murid
    public Double calculateAverageNilai(Long muridId) {
        List<NilaiMuridDTO.NilaiMuridResponse> nilaiList = findByMuridId(muridId);
        
        if (nilaiList.isEmpty()) {
            return null;
        }
        
        double total = 0;
        int count = 0;
        
        for (NilaiMuridDTO.NilaiMuridResponse nilai : nilaiList) {
            if (nilai.getNilai() != null) {
                total += nilai.getNilai();
                count++;
            }
        }
        
        return count > 0 ? total / count : null;
    }

    // Calculate average nilai for a murid per semester
    public Double calculateAverageNilaiPerSemester(Long muridId, Integer semester) {
        NilaiMuridDTO.NilaiMuridFilter filter = new NilaiMuridDTO.NilaiMuridFilter();
        filter.setIdMurid(muridId);
        filter.setSemester(semester);
        filter.setIncludeDeleted(false);
        
        List<NilaiMuridDTO.NilaiMuridResponse> nilaiList = dao.findAllWithFilter(filter);
        
        if (nilaiList.isEmpty()) {
            return null;
        }
        
        double total = 0;
        int count = 0;
        
        for (NilaiMuridDTO.NilaiMuridResponse nilai : nilaiList) {
            if (nilai.getNilai() != null) {
                total += nilai.getNilai();
                count++;
            }
        }
        
        return count > 0 ? total / count : null;
    }
}
