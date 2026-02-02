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

import com.bootcamp.training.dto.MuridDTO;

@Repository
public class MuridDao {

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    public int save(MuridDTO.MuridCreate value) throws SQLException {
        String baseQuery = "INSERT INTO murid(nama, id_kelas, umur, tanggal_masuk) " +
                "VALUES(:nama, :idKelas, :umur, CURRENT_TIMESTAMP)";

        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("nama", value.getNama());
        parameterSource.addValue("idKelas", value.getIdKelas());
        parameterSource.addValue("umur", value.getUmur());

        return jdbcTemplate.update(baseQuery, parameterSource);
    }

    public int delete(Long id) throws DataAccessException {
        String baseQuery = "DELETE FROM murid WHERE id = :id";

        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("id", id);

        return jdbcTemplate.update(baseQuery, parameterSource);
    }

    public int update(Long id, MuridDTO.MuridUpdate value) throws DataAccessException {
        String baseQuery = "UPDATE murid SET nama = :nama, id_kelas = :idKelas, " +
                "umur = :umur WHERE id = :id";

        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("id", id);
        parameterSource.addValue("nama", value.getNama());
        parameterSource.addValue("idKelas", value.getIdKelas());
        parameterSource.addValue("umur", value.getUmur());

        return jdbcTemplate.update(baseQuery, parameterSource);
    }

    public Optional<MuridDTO.MuridDetail> findById(Long id) throws EmptyResultDataAccessException {
        String baseQuery = "SELECT m.id as id, m.nama as nama, m.id_kelas as idKelas, " +
                "k.nama_kelas as namaKelas, m.umur as umur, m.tanggal_masuk as tanggalMasuk " +
                "FROM murid m LEFT JOIN kelas k ON m.id_kelas = k.id " +
                "WHERE m.id = :id";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", id);

        try {
            MuridDTO.MuridDetail murid = jdbcTemplate.queryForObject(baseQuery, params, (ResultSet resultSet, int i) -> {
                MuridDTO.MuridDetail murid1 = new MuridDTO.MuridDetail();
                murid1.setId(resultSet.getLong("id"));
                murid1.setNama(resultSet.getString("nama"));
                murid1.setIdKelas(resultSet.getLong("idKelas"));
                murid1.setNamaKelas(resultSet.getString("namaKelas"));
                murid1.setUmur(resultSet.getInt("umur"));
                murid1.setTanggalMasuk(resultSet.getTimestamp("tanggalMasuk").toLocalDateTime());
                return murid1;
            });
            return Optional.of(murid);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public List<MuridDTO.MuridResponse> findAll() throws EmptyResultDataAccessException {
        String baseQuery = "SELECT m.id as id, m.nama as nama, m.id_kelas as idKelas, " +
                "k.nama_kelas as namaKelas, m.umur as umur, m.tanggal_masuk as tanggalMasuk " +
                "FROM murid m LEFT JOIN kelas k ON m.id_kelas = k.id " +
                "ORDER BY m.id";

        MapSqlParameterSource parameterSource = new MapSqlParameterSource();

        return jdbcTemplate.query(baseQuery, parameterSource, (ResultSet resultSet, int i) -> {
            MuridDTO.MuridResponse murid = new MuridDTO.MuridResponse();
            murid.setId(resultSet.getLong("id"));
            murid.setNama(resultSet.getString("nama"));
            murid.setIdKelas(resultSet.getLong("idKelas"));
            murid.setNamaKelas(resultSet.getString("namaKelas"));
            murid.setUmur(resultSet.getInt("umur"));
            murid.setTanggalMasuk(resultSet.getTimestamp("tanggalMasuk").toLocalDateTime());
            return murid;
        });
    }

    public List<MuridDTO.MuridResponse> findByKelasId(Long kelasId) {
        String baseQuery = "SELECT m.id as id, m.nama as nama, m.id_kelas as idKelas, " +
                "k.nama_kelas as namaKelas, m.umur as umur, m.tanggal_masuk as tanggalMasuk " +
                "FROM murid m LEFT JOIN kelas k ON m.id_kelas = k.id " +
                "WHERE m.id_kelas = :kelasId ORDER BY m.id";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("kelasId", kelasId);

        return jdbcTemplate.query(baseQuery, params, (ResultSet resultSet, int i) -> {
            MuridDTO.MuridResponse murid = new MuridDTO.MuridResponse();
            murid.setId(resultSet.getLong("id"));
            murid.setNama(resultSet.getString("nama"));
            murid.setIdKelas(resultSet.getLong("idKelas"));
            murid.setNamaKelas(resultSet.getString("namaKelas"));
            murid.setUmur(resultSet.getInt("umur"));
            murid.setTanggalMasuk(resultSet.getTimestamp("tanggalMasuk").toLocalDateTime());
            return murid;
        });
    }
}