package com.epam.esm.dao.impl;

import com.epam.esm.dao.CertificateDAO;
import com.epam.esm.entity.Certificate;
import com.epam.esm.mapper.CertificateMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Types;
import java.util.Optional;

@Repository
public class CertificateDAOImpl implements CertificateDAO {

    private final JdbcTemplate jdbcTemplate;

    private static final String SQL_SELECT_CERTIFICATE_BY_ID = "SELECT id,name,description,price,duration,create_date,last_update_date" +
            " FROM gift_certificate WHERE id = ?";

    @Autowired
    public CertificateDAOImpl(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
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
