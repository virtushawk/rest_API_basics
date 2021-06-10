package com.epam.esm.service.impl;

import com.epam.esm.dao.TagDAO;
import com.epam.esm.dto.TagDTO;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.TagNotFoundException;
import com.epam.esm.service.TagService;
import com.epam.esm.util.MapperDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TagServiceImpl implements TagService {

    public final TagDAO tagDAO;
    public final MapperDTO mapperDTO;

    public TagServiceImpl(TagDAO tagDAO, MapperDTO mapperDTO) {
        this.tagDAO = tagDAO;
        this.mapperDTO = mapperDTO;
    }

    @Override
    public List<TagDTO> findAll() {
        return tagDAO.findAll().stream().map(mapperDTO::convertTagToDTO).collect(Collectors.toList());
    }

    @Override
    public TagDTO findById(Long id) {
        Optional<Tag> tag = tagDAO.findById(id);
        if (tag.isEmpty()){
            throw new TagNotFoundException("Requested tag not found, id : " + id);
        }
        return mapperDTO.convertTagToDTO(tag.get());
    }

    @Override
    public TagDTO create(TagDTO tagDTO) {
        Tag tag = mapperDTO.convertDTOToTag(tagDTO);
        return mapperDTO.convertTagToDTO(tagDAO.create(tag));
    }

    @Override
    public boolean delete(Long id) {
        boolean flag = tagDAO.delete(id);
        if (!flag) {
            throw new TagNotFoundException("Requested tag not found, id : " + id);
        }
        return true;
    }
}
