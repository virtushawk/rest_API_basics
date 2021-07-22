package com.epam.esm.service.impl;

import com.epam.esm.dao.CertificateDAO;
import com.epam.esm.dao.TagDAO;
import com.epam.esm.dto.TagDTO;
import com.epam.esm.entity.Certificate;
import com.epam.esm.entity.Page;
import com.epam.esm.exception.CertificateNotFoundException;
import com.epam.esm.exception.TagNotFoundException;
import com.epam.esm.service.TagService;
import com.epam.esm.util.MapperDTO;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

    public final TagDAO tagDAO;
    public final CertificateDAO certificateDAO;
    public final MapperDTO mapperDTO;

    @Override
    public List<TagDTO> findAll(Page page) {
        Pageable pageRequest = PageRequest.of(page.getPage(), page.getSize());
        return tagDAO.findAll(pageRequest)
                .stream()
                .map(mapperDTO::convertTagToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public TagDTO findById(Long id) {
        return mapperDTO.convertTagToDTO(tagDAO.findById(id)
                .orElseThrow(() -> new TagNotFoundException(id.toString())));
    }

    @Override
    @Transactional
    public TagDTO create(TagDTO tagDTO) {
        return mapperDTO.convertTagToDTO(tagDAO.findOrCreate(mapperDTO.convertDTOToTag(tagDTO)));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        tagDAO.delete(tagDAO.findById(id)
                .orElseThrow(() -> new TagNotFoundException(id.toString())));
    }

    @Override
    public List<TagDTO> findAllByCertificateId(Long id, Page page) {
        Optional<Certificate> optional = certificateDAO.findById(id);
        if (optional.isEmpty() || !optional.get().isActive()) {
            throw new CertificateNotFoundException(id.toString());
        }
        return optional.get().getTags()
                .stream()
                .skip(page.getPage() * page.getSize())
                .limit(page.getSize())
                .map(mapperDTO::convertTagToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public TagDTO findPopular() {
        return mapperDTO.convertTagToDTO(tagDAO.findPopular());
    }
}
