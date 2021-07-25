package com.epam.esm.dao.datajpa;

import com.epam.esm.entity.Certificate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface JPACertificateDAO extends JpaRepository<Certificate, Long>, JpaSpecificationExecutor<Certificate> {
}
