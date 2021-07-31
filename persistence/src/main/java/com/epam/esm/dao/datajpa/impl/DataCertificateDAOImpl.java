package com.epam.esm.dao.datajpa.impl;

import com.epam.esm.dao.CertificateDAO;
import com.epam.esm.dao.datajpa.JPACertificateDAO;
import com.epam.esm.entity.Certificate;
import com.epam.esm.entity.QuerySpecification;
import com.epam.esm.specification.CertificateSpecifications;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
@Profile("jpaData")
public class DataCertificateDAOImpl implements CertificateDAO {

    private final JPACertificateDAO jpaCertificateDAO;

    @Override
    public List<Certificate> findAll(Pageable page) {
        return jpaCertificateDAO.findAll(page).getContent();
    }

    @Override
    public Optional<Certificate> findById(Long id) {
        return jpaCertificateDAO.findById(id);
    }

    @Override
    public Certificate create(Certificate certificate) {
        return jpaCertificateDAO.save(certificate);
    }

    @Override
    public void delete(Certificate certificate) {
        certificate.setActive(false);
    }

    @Override
    public List<Certificate> findAll(QuerySpecification querySpecification, Pageable page) {
        Specification<Certificate> specification = CertificateSpecifications.isActive();
        if (!ObjectUtils.isEmpty(querySpecification.getTags())) {
            specification = specification.and(CertificateSpecifications.havingTags(querySpecification.getTags()));
        }
        if (!ObjectUtils.isEmpty(querySpecification.getText())) {
            specification = specification
                    .and(CertificateSpecifications.havingTextInNameOrDescription(querySpecification.getText()));
        }
        if (!ObjectUtils.isEmpty(querySpecification.getOrder())) {
            specification = specification.and(CertificateSpecifications
                    .havingOrderAndSort(querySpecification.getOrder(), querySpecification.getSort()));
        }
        return jpaCertificateDAO.findAll(specification, page).getContent();
    }

    //todo::change update and applyPatch method, Probably should use JPA merge method

    @Override
    public Certificate update(Certificate certificate, Certificate update) {
        certificate.setName(update.getName());
        certificate.setDescription(update.getDescription());
        certificate.setPrice(update.getPrice());
        certificate.setDuration(update.getDuration());
        return certificate;
    }

    @Override
    public Certificate applyPatch(Certificate certificate, Certificate update) {
        certificate.setName(ObjectUtils.isEmpty(update.getName()) ? certificate.getName() : update.getName());
        certificate.setDescription(ObjectUtils.isEmpty(update.getDescription()) ? certificate.getDescription() : update.getDescription());
        certificate.setPrice(ObjectUtils.isEmpty(update.getPrice()) ? certificate.getPrice() : update.getPrice());
        certificate.setDuration(ObjectUtils.isEmpty(update.getDuration()) ? certificate.getDuration() : update.getDuration());
        return certificate;
    }
}
