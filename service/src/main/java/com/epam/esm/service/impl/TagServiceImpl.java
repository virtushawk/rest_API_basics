package com.epam.esm.service.impl;

import com.epam.esm.dao.CertificateDAO;
import com.epam.esm.dao.TagDAO;
import com.epam.esm.dto.OrderDTO;
import com.epam.esm.dto.TagDTO;
import com.epam.esm.entity.Certificate;
import com.epam.esm.entity.Page;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.CertificateNotFoundException;
import com.epam.esm.exception.TagNotFoundException;
import com.epam.esm.service.TagService;
import com.epam.esm.util.MapperDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * The type Tag service.
 */
@Service
@AllArgsConstructor
public class TagServiceImpl implements TagService {

    /**
     * The Tag dao.
     */
    public final TagDAO tagDAO;
    public final CertificateDAO certificateDAO;
    /**
     * The Mapper dto.
     */
    public final MapperDTO mapperDTO;

    @Override
    public List<TagDTO> findAll(Page page) {
        return tagDAO.findAll(page).stream().map(mapperDTO::convertTagToDTO).collect(Collectors.toList());
    }

    @Override
    public TagDTO findById(Long id) {
        Optional<Tag> tag = tagDAO.findById(id);
        if (tag.isEmpty()) {
            throw new TagNotFoundException(id.toString());
        }
        return mapperDTO.convertTagToDTO(tag.get());
    }

    @Override
    public TagDTO create(TagDTO tagDTO) {
        Optional<Tag> tag = tagDAO.findByName(tagDTO.getName());
        if (tag.isEmpty()) {
            return mapperDTO.convertTagToDTO(tagDAO.create(mapperDTO.convertDTOToTag(tagDTO)));
        }
        return mapperDTO.convertTagToDTO(tag.get());
    }

    @Transactional
    @Override
    public boolean delete(Long id) {
        Optional<Tag> tag = tagDAO.findById(id);
        if (tag.isEmpty()) {
            throw new TagNotFoundException(id.toString());
        }
        return tagDAO.delete(id);
    }

    @Override
    public List<TagDTO> findAllByCertificateId(Long id) {
        Optional<Certificate> certificate = certificateDAO.findById(id);
        if (certificate.isEmpty()) {
            throw new CertificateNotFoundException(id.toString());
        }
        return certificate.get().getTags().stream().map(mapperDTO::convertTagToDTO).collect(Collectors.toList());
    }

    @Override
    public TagDTO findPopular() {
        return mapperDTO.convertTagToDTO(tagDAO.findPopular());
    }
}
