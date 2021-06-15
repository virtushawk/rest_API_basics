package com.epam.esm.dao.impl;

import com.epam.esm.dao.CertificateDAO;
import com.epam.esm.entity.Certificate;
import com.epam.esm.entity.QuerySpecification;
import com.epam.esm.mapper.CertificateMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.util.ObjectUtils;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Types;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * The type Certificate dao.
 */
@Repository
@AllArgsConstructor
@Slf4j
public class CertificateDAOImpl implements CertificateDAO {

    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final CertificateMapper certificateMapper;
    private static final String SQL_SELECT_CERTIFICATE_BY_ID = "SELECT id,name,description,price,duration,create_date,last_update_date" +
            " FROM gift_certificate WHERE id = ?";
    private static final String SQL_INSERT_CERTIFICATE = "INSERT INTO gift_certificate(name,description,price,duration,create_date,last_update_date) " +
            "VALUES(?,?,?,?,?,?)";
    private static final String SQL_DELETE_CERTIFICATE_BY_ID = "DELETE FROM gift_certificate WHERE id = ?";
    private static final String SQL_DELETE_TAG_HAS_GIFT_CERTIFICATE_BY_ID = "DELETE FROM tag_has_gift_certificate WHERE gift_certificate_id = ?";
    private static final String SQL_UPDATE_BY_ID = "UPDATE gift_certificate SET %s = ? WHERE id = ?";
    private static final String SQL_UPDATE_CERTIFICATE = "UPDATE gift_certificate SET name = ?, description = ?, price = ?, " +
            "duration = ?, last_update_date = ? WHERE id = ?";
    private static final String LAST_UPDATE_DATE_COLUMN = "last_update_date";
    private static final String SELECT_CERTIFICATE_QUERY = "SELECT gift_certificate.id,gift_certificate.name,gift_certificate.description,gift_certificate.price,gift_certificate.duration,gift_certificate.create_date,gift_certificate.last_update_date FROM gift_certificate" +
            " LEFT JOIN tag_has_gift_certificate ON gift_certificate_id = gift_certificate.id LEFT JOIN tag ON tag_id = tag.id" +
            " WHERE (:tag IS NULL OR tag.name = :tag) AND (:text IS NULL OR (gift_certificate.name LIKE %s OR gift_certificate.description LIKE %s)) " +
            "ORDER BY %s";
    private static final String SELECT_CERTIFICATES = "SELECT gift_certificate.id,gift_certificate.name,gift_certificate.description,gift_certificate.price,gift_certificate.duration,gift_certificate.create_date,gift_certificate.last_update_date FROM gift_certificate";

    @Override
    public List<Certificate> findAll(QuerySpecification querySpecification) {
        String text = "'%%'";
        StringBuilder result = new StringBuilder();
        result.append("NULL");
        if (!ObjectUtils.isEmpty(querySpecification.getText())) {
            text = "'%" + querySpecification.getText() + "%'";
        }
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("tag", querySpecification.getTag());
        parameterSource.addValue("text", querySpecification.getText());
        parameterSource.addValue("order", null);
        if (!ObjectUtils.isEmpty(querySpecification.getOrder())) {
            List<String> sorts = new ArrayList<>(querySpecification.getOrder());
            sorts = sorts.stream().map(o -> "ASC").collect(Collectors.toList());
            if (!ObjectUtils.isEmpty(querySpecification.getSort())) {
                int i = 0;
                for (String sort : querySpecification.getSort()) {
                    sorts.set(i, sort);
                    i++;
                }
            }
            result = new StringBuilder();
            int i = 0;
            for (String order : querySpecification.getOrder()) {
                result.append(order).append(' ').append(sorts.get(i).toUpperCase(Locale.ROOT)).append(',');
            }
            result.deleteCharAt(result.length() - 1);
        }
        return namedParameterJdbcTemplate.query(String.format(SELECT_CERTIFICATE_QUERY, text, text, result), parameterSource, certificateMapper);
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
    public List<Certificate> findAll() {
        return jdbcTemplate.query(SELECT_CERTIFICATES, certificateMapper);
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
