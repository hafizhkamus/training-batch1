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

import com.bootcamp.training.dto.NilaiMuridDTO;

@Repository
public class NilaiMuridDao {

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    public int save(NilaiMuridDTO.NilaiMuridCreate value) throws SQLException {
        String baseQuery = "INSERT INTO nilai_murid(id_mapel, id_murid, nilai, semester, " +
                "created_date, created_by, is_deleted) " +
                "VALUES(:idMapel, :idMurid, :nilai, :semester, CURRENT_TIMESTAMP, :createdBy, false)";

        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("idMapel", value.getIdMapel());
        parameterSource.addValue("idMurid", value.getIdMurid());
        parameterSource.addValue("nilai", value.getNilai());
        parameterSource.addValue("semester", value.getSemester());
        parameterSource.addValue("createdBy", value.getCreatedBy());

        return jdbcTemplate.update(baseQuery, parameterSource);
    }

    public int softDelete(Long id, NilaiMuridDTO.NilaiMuridSoftDelete value) throws DataAccessException {
        String baseQuery = "UPDATE nilai_murid SET is_deleted = true, " +
                "deleted_date = CURRENT_TIMESTAMP, deleted_by = :deletedBy " +
                "WHERE id = :id AND is_deleted = false";

        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("id", id);
        parameterSource.addValue("deletedBy", value.getDeletedBy());

        return jdbcTemplate.update(baseQuery, parameterSource);
    }

    public int hardDelete(Long id) throws DataAccessException {
        String baseQuery = "DELETE FROM nilai_murid WHERE id = :id";

        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("id", id);

        return jdbcTemplate.update(baseQuery, parameterSource);
    }

    public int update(Long id, NilaiMuridDTO.NilaiMuridUpdate value) throws DataAccessException {
        String baseQuery = "UPDATE nilai_murid SET nilai = :nilai, semester = :semester " +
                "WHERE id = :id AND is_deleted = false";

        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("id", id);
        parameterSource.addValue("nilai", value.getNilai());
        parameterSource.addValue("semester", value.getSemester());

        return jdbcTemplate.update(baseQuery, parameterSource);
    }

    public Optional<NilaiMuridDTO.NilaiMuridDetail> findById(Long id) throws EmptyResultDataAccessException {
        String baseQuery = "SELECT nm.id as id, nm.id_mapel as idMapel, mp.nama_mapel as namaMapel, " +
                "mp.guru as guruMapel, nm.id_murid as idMurid, m.nama as namaMurid, " +
                "k.nama_kelas as kelasMurid, m.umur as umurMurid, nm.nilai as nilai, " +
                "nm.semester as semester, nm.created_date as createdDate, nm.created_by as createdBy, " +
                "nm.is_deleted as isDeleted, nm.deleted_date as deletedDate, nm.deleted_by as deletedBy " +
                "FROM nilai_murid nm " +
                "LEFT JOIN mata_pelajaran mp ON nm.id_mapel = mp.id " +
                "LEFT JOIN murid m ON nm.id_murid = m.id " +
                "LEFT JOIN kelas k ON m.id_kelas = k.id " +
                "WHERE nm.id = :id";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", id);

        try {
            NilaiMuridDTO.NilaiMuridDetail nilai = jdbcTemplate.queryForObject(baseQuery, params, (ResultSet resultSet, int i) -> {
                NilaiMuridDTO.NilaiMuridDetail nilai1 = new NilaiMuridDTO.NilaiMuridDetail();
                nilai1.setId(resultSet.getLong("id"));
                nilai1.setIdMapel(resultSet.getLong("idMapel"));
                nilai1.setNamaMapel(resultSet.getString("namaMapel"));
                nilai1.setGuruMapel(resultSet.getString("guruMapel"));
                nilai1.setIdMurid(resultSet.getLong("idMurid"));
                nilai1.setNamaMurid(resultSet.getString("namaMurid"));
                nilai1.setKelasMurid(resultSet.getString("kelasMurid"));
                nilai1.setUmurMurid(resultSet.getInt("umurMurid"));
                nilai1.setNilai(resultSet.getDouble("nilai"));
                nilai1.setSemester(resultSet.getInt("semester"));
                nilai1.setCreatedDate(resultSet.getTimestamp("createdDate").toLocalDateTime());
                nilai1.setCreatedBy(resultSet.getString("createdBy"));
                nilai1.setIsDeleted(resultSet.getBoolean("isDeleted"));
                if (resultSet.getTimestamp("deletedDate") != null) {
                    nilai1.setDeletedDate(resultSet.getTimestamp("deletedDate").toLocalDateTime());
                }
                nilai1.setDeletedBy(resultSet.getString("deletedBy"));
                return nilai1;
            });
            return Optional.of(nilai);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public List<NilaiMuridDTO.NilaiMuridResponse> findAll() throws EmptyResultDataAccessException {
        String baseQuery = "SELECT nm.id as id, nm.id_mapel as idMapel, mp.nama_mapel as namaMapel, " +
                "nm.id_murid as idMurid, m.nama as namaMurid, nm.nilai as nilai, " +
                "nm.semester as semester, nm.created_date as createdDate, nm.created_by as createdBy, " +
                "nm.is_deleted as isDeleted " +
                "FROM nilai_murid nm " +
                "LEFT JOIN mata_pelajaran mp ON nm.id_mapel = mp.id " +
                "LEFT JOIN murid m ON nm.id_murid = m.id " +
                "WHERE nm.is_deleted = false " +
                "ORDER BY nm.id DESC";

        MapSqlParameterSource parameterSource = new MapSqlParameterSource();

        return jdbcTemplate.query(baseQuery, parameterSource, (ResultSet resultSet, int i) -> {
            NilaiMuridDTO.NilaiMuridResponse nilai = new NilaiMuridDTO.NilaiMuridResponse();
            nilai.setId(resultSet.getLong("id"));
            nilai.setIdMapel(resultSet.getLong("idMapel"));
            nilai.setNamaMapel(resultSet.getString("namaMapel"));
            nilai.setIdMurid(resultSet.getLong("idMurid"));
            nilai.setNamaMurid(resultSet.getString("namaMurid"));
            nilai.setNilai(resultSet.getDouble("nilai"));
            nilai.setSemester(resultSet.getInt("semester"));
            nilai.setCreatedDate(resultSet.getTimestamp("createdDate").toLocalDateTime());
            nilai.setCreatedBy(resultSet.getString("createdBy"));
            nilai.setIsDeleted(resultSet.getBoolean("isDeleted"));
            return nilai;
        });
    }

    public List<NilaiMuridDTO.NilaiMuridResponse> findAllWithFilter(NilaiMuridDTO.NilaiMuridFilter filter) {
        StringBuilder baseQuery = new StringBuilder(
                "SELECT nm.id as id, nm.id_mapel as idMapel, mp.nama_mapel as namaMapel, " +
                "nm.id_murid as idMurid, m.nama as namaMurid, nm.nilai as nilai, " +
                "nm.semester as semester, nm.created_date as createdDate, nm.created_by as createdBy, " +
                "nm.is_deleted as isDeleted " +
                "FROM nilai_murid nm " +
                "LEFT JOIN mata_pelajaran mp ON nm.id_mapel = mp.id " +
                "LEFT JOIN murid m ON nm.id_murid = m.id " +
                "WHERE 1=1 "
        );

        MapSqlParameterSource params = new MapSqlParameterSource();

        if (filter.getIdMurid() != null) {
            baseQuery.append(" AND nm.id_murid = :idMurid");
            params.addValue("idMurid", filter.getIdMurid());
        }

        if (filter.getIdMapel() != null) {
            baseQuery.append(" AND nm.id_mapel = :idMapel");
            params.addValue("idMapel", filter.getIdMapel());
        }

        if (filter.getSemester() != null) {
            baseQuery.append(" AND nm.semester = :semester");
            params.addValue("semester", filter.getSemester());
        }

        if (filter.getNilaiMin() != null) {
            baseQuery.append(" AND nm.nilai >= :nilaiMin");
            params.addValue("nilaiMin", filter.getNilaiMin());
        }

        if (filter.getNilaiMax() != null) {
            baseQuery.append(" AND nm.nilai <= :nilaiMax");
            params.addValue("nilaiMax", filter.getNilaiMax());
        }

        if (!filter.getIncludeDeleted()) {
            baseQuery.append(" AND nm.is_deleted = false");
        }

        baseQuery.append(" ORDER BY nm.id DESC");

        return jdbcTemplate.query(baseQuery.toString(), params, (ResultSet resultSet, int i) -> {
            NilaiMuridDTO.NilaiMuridResponse nilai = new NilaiMuridDTO.NilaiMuridResponse();
            nilai.setId(resultSet.getLong("id"));
            nilai.setIdMapel(resultSet.getLong("idMapel"));
            nilai.setNamaMapel(resultSet.getString("namaMapel"));
            nilai.setIdMurid(resultSet.getLong("idMurid"));
            nilai.setNamaMurid(resultSet.getString("namaMurid"));
            nilai.setNilai(resultSet.getDouble("nilai"));
            nilai.setSemester(resultSet.getInt("semester"));
            nilai.setCreatedDate(resultSet.getTimestamp("createdDate").toLocalDateTime());
            nilai.setCreatedBy(resultSet.getString("createdBy"));
            nilai.setIsDeleted(resultSet.getBoolean("isDeleted"));
            return nilai;
        });
    }

    public List<NilaiMuridDTO.NilaiMuridResponse> findByMuridId(Long muridId) {
        String baseQuery = "SELECT nm.id as id, nm.id_mapel as idMapel, mp.nama_mapel as namaMapel, " +
                "nm.id_murid as idMurid, m.nama as namaMurid, nm.nilai as nilai, " +
                "nm.semester as semester, nm.created_date as createdDate, nm.created_by as createdBy, " +
                "nm.is_deleted as isDeleted " +
                "FROM nilai_murid nm " +
                "LEFT JOIN mata_pelajaran mp ON nm.id_mapel = mp.id " +
                "LEFT JOIN murid m ON nm.id_murid = m.id " +
                "WHERE nm.id_murid = :muridId AND nm.is_deleted = false " +
                "ORDER BY nm.semester, nm.id_mapel";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("muridId", muridId);

        return jdbcTemplate.query(baseQuery, params, (ResultSet resultSet, int i) -> {
            NilaiMuridDTO.NilaiMuridResponse nilai = new NilaiMuridDTO.NilaiMuridResponse();
            nilai.setId(resultSet.getLong("id"));
            nilai.setIdMapel(resultSet.getLong("idMapel"));
            nilai.setNamaMapel(resultSet.getString("namaMapel"));
            nilai.setIdMurid(resultSet.getLong("idMurid"));
            nilai.setNamaMurid(resultSet.getString("namaMurid"));
            nilai.setNilai(resultSet.getDouble("nilai"));
            nilai.setSemester(resultSet.getInt("semester"));
            nilai.setCreatedDate(resultSet.getTimestamp("createdDate").toLocalDateTime());
            nilai.setCreatedBy(resultSet.getString("createdBy"));
            nilai.setIsDeleted(resultSet.getBoolean("isDeleted"));
            return nilai;
        });
    }

    public boolean existsById(Long id) {
        String baseQuery = "SELECT COUNT(*) FROM nilai_murid WHERE id = :id";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", id);

        Integer count = jdbcTemplate.queryForObject(baseQuery, params, Integer.class);
        return count != null && count > 0;
    }
}
