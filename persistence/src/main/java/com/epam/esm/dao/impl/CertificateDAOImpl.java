package com.epam.esm.dao.impl;

import com.epam.esm.dao.CertificateDAO;
import com.epam.esm.entity.Certificate;
import com.epam.esm.mapper.CertificateMapper;
import lombok.AllArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Types;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class CertificateDAOImpl implements CertificateDAO {

    private final JdbcTemplate jdbcTemplate;
    private final CertificateMapper certificateMapper;
    private static final String SQL_SELECT_CERTIFICATE_BY_ID = "SELECT id,name,description,price,duration,create_date,last_update_date" +
            " FROM gift_certificate WHERE id = ?";
    private static final String SQL_SELECT_CERTIFICATES = "SELECT id,name,description,price,duration,create_date,last_update_date " +
            "FROM gift_certificate";
    private static final String SQL_INSERT_CERTIFICATE = "INSERT INTO gift_certificate(name,description,price,duration,create_date,last_update_date) " +
            "VALUES(?,?,?,?,?,?)";
    private static final String SQL_DELETE_CERTIFICATE_BY_ID = "DELETE FROM gift_certificate WHERE id = ?";
    private static final String SQL_DELETE_TAG_HAS_GIFT_CERTIFICATE_BY_ID = "DELETE FROM tag_has_gift_certificate WHERE gift_certificate_id = ?";
    private static final String SQL_UPDATE_BY_ID = "UPDATE gift_certificate SET %s = ? WHERE id = ?";
    private static final String SQL_UPDATE_CERTIFICATE = "UPDATE gift_certificate SET name = ?, description = ?, price = ?, " +
            "duration = ?, last_update_date = ? WHERE id = ?";
    private static final String LAST_UPDATE_DATE_COLUMN = "last_update_date";
    private static final String CERTIFICATE_ID_COLUMN = "id";


    @Override
    public List<Certificate> findAll() {
        return jdbcTemplate.query(SQL_SELECT_CERTIFICATES, certificateMapper);
    }

    @Override
    public Certificate create(Certificate certificate) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        certificate.setCreateDate(ZonedDateTime.now(ZoneId.systemDefault()));
        certificate.setLastUpdateDate(ZonedDateTime.now(ZoneId.systemDefault()));
        jdbcTemplate.update(con -> {
            PreparedStatement preparedStatement = con.prepareStatement(SQL_INSERT_CERTIFICATE, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, certificate.getName());
            preparedStatement.setString(2, certificate.getDescription());
            preparedStatement.setBigDecimal(3, certificate.getPrice());
            preparedStatement.setInt(4, certificate.getDuration());
            preparedStatement.setTimestamp(5, Timestamp.from(certificate.getCreateDate().toInstant()));
            preparedStatement.setTimestamp(6, Timestamp.from(certificate.getLastUpdateDate().toInstant()));
            return preparedStatement;
        }, keyHolder);
        certificate.setId(keyHolder.getKey().longValue());
        return certificate;
    }

    @Override
    public Optional<Certificate> findById(Long id) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(SQL_SELECT_CERTIFICATE_BY_ID, new Object[]{id}, new int[]{Types.INTEGER},
                    certificateMapper));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Certificate update(Certificate certificate) {
        certificate.setLastUpdateDate(ZonedDateTime.now(ZoneId.systemDefault()));
        jdbcTemplate.update(SQL_UPDATE_CERTIFICATE, certificate.getName(), certificate.getDescription(), certificate.getPrice(),
                certificate.getDuration(), certificate.getLastUpdateDate(), certificate.getId());
        return certificate;
    }

    @Override
    public boolean applyPatch(Map<String, Object> patchValues, Long id) {
        patchValues.forEach((key, value) -> {
            String formattedSQL = String.format(SQL_UPDATE_BY_ID, key);
            jdbcTemplate.update(formattedSQL, value, id);
        });
        String formattedSQL = String.format(SQL_UPDATE_BY_ID, LAST_UPDATE_DATE_COLUMN);
        jdbcTemplate.update(formattedSQL, ZonedDateTime.now(ZoneId.systemDefault()), id);
        return true;
    }

    @Override
    public boolean delete(Long id) {
        boolean flag;
        flag = jdbcTemplate.update(SQL_DELETE_CERTIFICATE_BY_ID, id) > 0;
        if (flag) {
            jdbcTemplate.update(SQL_DELETE_TAG_HAS_GIFT_CERTIFICATE_BY_ID, id);
        }
        return flag;
    }
}
