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

import com.bootcamp.training.dto.MataPelajaranDTO;

@Repository
public class MataPelajaranDao {

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    public int save(MataPelajaranDTO.MataPelajaranCreate value) throws SQLException {
        String baseQuery = "INSERT INTO mata_pelajaran(nama_mapel, guru) VALUES(:namaMapel, :guru)";

        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("namaMapel", value.getNamaMapel());
        parameterSource.addValue("guru", value.getGuru());

        return jdbcTemplate.update(baseQuery, parameterSource);
    }

    public int delete(Long id) throws DataAccessException {
        String baseQuery = "DELETE FROM mata_pelajaran WHERE id = :id";

        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("id", id);

        return jdbcTemplate.update(baseQuery, parameterSource);
    }

    public int update(Long id, MataPelajaranDTO.MataPelajaranUpdate value) throws DataAccessException {
        String baseQuery = "UPDATE mata_pelajaran SET nama_mapel = :namaMapel, guru = :guru WHERE id = :id";

        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("id", id);
        parameterSource.addValue("namaMapel", value.getNamaMapel());
        parameterSource.addValue("guru", value.getGuru());

        return jdbcTemplate.update(baseQuery, parameterSource);
    }

    public Optional<MataPelajaranDTO.MataPelajaranDetail> findById(Long id) throws EmptyResultDataAccessException {
        String baseQuery = "SELECT id, nama_mapel as namaMapel, guru " +
                "FROM mata_pelajaran WHERE id = :id";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", id);

        try {
            MataPelajaranDTO.MataPelajaranDetail mapel = jdbcTemplate.queryForObject(baseQuery, params, (ResultSet resultSet, int i) -> {
                MataPelajaranDTO.MataPelajaranDetail mapel1 = new MataPelajaranDTO.MataPelajaranDetail();
                mapel1.setId(resultSet.getLong("id"));
                mapel1.setNamaMapel(resultSet.getString("namaMapel"));
                mapel1.setGuru(resultSet.getString("guru"));
                return mapel1;
            });
            return Optional.of(mapel);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public List<MataPelajaranDTO.MataPelajaranResponse> findAll() throws EmptyResultDataAccessException {
        String baseQuery = "SELECT mp.id as id, mp.nama_mapel as namaMapel, mp.guru as guru, " +
                "COUNT(nm.id) as jumlahNilai " +
                "FROM mata_pelajaran mp LEFT JOIN nilai_murid nm ON mp.id = nm.id_mapel " +
                "GROUP BY mp.id, mp.nama_mapel, mp.guru " +
                "ORDER BY mp.id";

        MapSqlParameterSource parameterSource = new MapSqlParameterSource();

        return jdbcTemplate.query(baseQuery, parameterSource, (ResultSet resultSet, int i) -> {
            MataPelajaranDTO.MataPelajaranResponse mapel = new MataPelajaranDTO.MataPelajaranResponse();
            mapel.setId(resultSet.getLong("id"));
            mapel.setNamaMapel(resultSet.getString("namaMapel"));
            mapel.setGuru(resultSet.getString("guru"));
            mapel.setJumlahNilai(resultSet.getInt("jumlahNilai"));
            return mapel;
        });
    }

    public boolean existsById(Long id) {
        String baseQuery = "SELECT COUNT(*) FROM mata_pelajaran WHERE id = :id";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", id);

        Integer count = jdbcTemplate.queryForObject(baseQuery, params, Integer.class);
        return count != null && count > 0;
    }
}
