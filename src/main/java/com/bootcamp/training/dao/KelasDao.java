package com.bootcamp.training.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.bootcamp.training.dto.KelasDTO;

@Repository
public class KelasDao {

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    public int save(KelasDTO.KelasCreate value) throws SQLException {
        String baseQuery = "INSERT INTO kelas(nama_kelas) VALUES(:namaKelas)";

        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("namaKelas", value.getNamaKelas());

        return jdbcTemplate.update(baseQuery, parameterSource);
    }

    public int delete(Long id) throws DataAccessException {
        String baseQuery = "DELETE FROM kelas WHERE id = :id";

        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("id", id);

        return jdbcTemplate.update(baseQuery, parameterSource);
    }

    public int update(Long id, KelasDTO.KelasUpdate value) throws DataAccessException {
        String baseQuery = "UPDATE kelas SET nama_kelas = :namaKelas WHERE id = :id";

        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("id", id);
        parameterSource.addValue("namaKelas", value.getNamaKelas());

        return jdbcTemplate.update(baseQuery, parameterSource);
    }

    public Optional<KelasDTO.KelasDetail> findById(Long id) throws EmptyResultDataAccessException {
        String baseQuery = "SELECT k.id as id, k.nama_kelas as namaKelas, " +
                "COUNT(m.id) as jumlahMurid " +
                "FROM kelas k LEFT JOIN murid m ON k.id = m.id_kelas " +
                "WHERE k.id = :id " +
                "GROUP BY k.id, k.nama_kelas";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", id);

        try {
            KelasDTO.KelasDetail kelas = jdbcTemplate.queryForObject(baseQuery, params, (ResultSet resultSet, int i) -> {
                KelasDTO.KelasDetail kelas1 = new KelasDTO.KelasDetail();
                kelas1.setId(resultSet.getLong("id"));
                kelas1.setNamaKelas(resultSet.getString("namaKelas"));
                return kelas1;
            });
            return Optional.of(kelas);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public List<KelasDTO.KelasResponse> findAll() throws EmptyResultDataAccessException {
        String baseQuery = "SELECT k.id as id, k.nama_kelas as namaKelas, " +
                "COUNT(m.id) as jumlahMurid " +
                "FROM kelas k LEFT JOIN murid m ON k.id = m.id_kelas " +
                "GROUP BY k.id, k.nama_kelas " +
                "ORDER BY k.id";

        MapSqlParameterSource parameterSource = new MapSqlParameterSource();

        return jdbcTemplate.query(baseQuery, parameterSource, (ResultSet resultSet, int i) -> {
            KelasDTO.KelasResponse kelas = new KelasDTO.KelasResponse();
            kelas.setId(resultSet.getLong("id"));
            kelas.setNamaKelas(resultSet.getString("namaKelas"));
            kelas.setJumlahMurid(resultSet.getInt("jumlahMurid"));
            return kelas;
        });
    }

    public boolean existsById(Long id) {
        String baseQuery = "SELECT COUNT(*) FROM kelas WHERE id = :id";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", id);

        Integer count = jdbcTemplate.queryForObject(baseQuery, params, Integer.class);
        return count != null && count > 0;
    }
}
