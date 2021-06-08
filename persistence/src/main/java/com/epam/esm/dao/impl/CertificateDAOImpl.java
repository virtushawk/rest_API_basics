package com.epam.esm.dao.impl;

import com.epam.esm.dao.CertificateDAO;
import com.epam.esm.entity.Certificate;
import com.epam.esm.mapper.CertificateMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Types;
import java.util.List;
import java.util.Optional;

@Repository
public class CertificateDAOImpl implements CertificateDAO {

    private final JdbcTemplate jdbcTemplate;
    private static final String SQL_SELECT_CERTIFICATE_BY_ID = "SELECT id,name,description,price,duration,create_date,last_update_date" +
            " FROM gift_certificate WHERE id = ?";

    public CertificateDAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Certificate> findAll() {
        return null;
    }

    @Override
    public Certificate create(Certificate certificate) {
        return null;
    }

    @Override
    public Optional<Certificate> findById(Long id) {
        return Optional.ofNullable(jdbcTemplate.queryForObject(SQL_SELECT_CERTIFICATE_BY_ID,new Object[] {id},new int[] {Types.INTEGER},new CertificateMapper()));
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
