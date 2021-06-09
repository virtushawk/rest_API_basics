package com.epam.esm.dao.impl;

import com.epam.esm.dao.CertificateDAO;
import com.epam.esm.entity.Certificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.mapper.CertificateMapper;
import com.epam.esm.mapper.TagMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public class CertificateDAOImpl implements CertificateDAO {

    private final JdbcTemplate jdbcTemplate;
    private final CertificateMapper certificateMapper;
    private final TagMapper tagMapper;
    private static final String SQL_SELECT_CERTIFICATE_BY_ID = "SELECT id,name,description,price,duration,create_date,last_update_date" +
            " FROM gift_certificate WHERE id = ?";
    private static final String SQL_SELECT_CERTIFICATES = "SELECT id,name,description,price,duration,create_date,last_update_date " +
            "FROM gift_certificate";
    private static final String SQL_SELECT_TAG_AND_CERTIFICATE_ID = "SELECT id,name,gift_certificate_id FROM tag INNER JOIN " +
            "tag_has_gift_certificate ON id = tag_id";
    private static final String SQL_INSERT_CERTIFICATE = "INSERT INTO gift_certificate(name,description,price,duration,create_date,last_update_date) " +
            "VALUES(?,?,?,?,?,?)";
    private static final String SQL_INSERT_TAG = "INSERT IGNORE INTO tag(name) VALUES(?)";
    private static final String SQL_INSERT_TAG_HAS_GIFT_CERTIFICATE = "INSERT INTO tag_has_gift_certificate(tag_id,gift_certificate_id) " +
            "VALUES(?,?)";
    private static final String SQL_SELECT_TAGS_BY_CERTIFICATE_ID = "SELECT id,name FROM tag INNER JOIN tag_has_gift_certificate ON id = tag_id WHERE gift_certificate_id = 18";


    public CertificateDAOImpl(JdbcTemplate jdbcTemplate, CertificateMapper certificateMapper, TagMapper tagMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.certificateMapper = certificateMapper;
        this.tagMapper = tagMapper;
    }

    @Override
    public List<Certificate> findAll() {
        List<Certificate> certificates = jdbcTemplate.query(SQL_SELECT_CERTIFICATES,certificateMapper);
        return null;
    }

    @Override
    public Certificate create(Certificate certificate) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement preparedStatement = con.prepareStatement(SQL_INSERT_CERTIFICATE);
            preparedStatement.setString(1,certificate.getName());
            preparedStatement.setString(2,certificate.getDescription());
            preparedStatement.setBigDecimal(3,certificate.getPrice());
            preparedStatement.setInt(4,certificate.getDuration());
            preparedStatement.setTimestamp(5, Timestamp.from(certificate.getCreateDate().toInstant()));
            preparedStatement.setTimestamp(6, Timestamp.from(certificate.getLastUpdateDate().toInstant()));
            return preparedStatement;
        },keyHolder);
        certificate.setId((Long) keyHolder.getKey());
        Set<Tag> tags = certificate.getTags();
        tags.forEach(o -> jdbcTemplate.update(SQL_INSERT_TAG,o.getName()));
        tags.forEach(o -> jdbcTemplate.update(SQL_INSERT_TAG_HAS_GIFT_CERTIFICATE,o.getId(),certificate.getId()));
        return certificate;
    }

    @Override
    public Optional<Certificate> findById(Long id) {
        Optional<Certificate> certificate = Optional.ofNullable(jdbcTemplate.queryForObject(SQL_SELECT_CERTIFICATE_BY_ID,
                new Object[] {id},new int[] {Types.INTEGER},certificateMapper));
        if (certificate.isPresent()) {
            Set<Tag> tags = new HashSet<>(jdbcTemplate.query(SQL_SELECT_TAGS_BY_CERTIFICATE_ID,tagMapper));
            certificate.get().setTags(tags);
        }
        return certificate;
    }

    @Override
    public Certificate update(Certificate certificate) {
        return null;
    }

    @Override
    public boolean delete(Long id) {
        return false;
    }
}
