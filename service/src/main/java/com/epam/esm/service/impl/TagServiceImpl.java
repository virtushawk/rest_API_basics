package com.epam.esm.service.impl;

import com.epam.esm.dao.TagDAO;
import com.epam.esm.dto.TagDTO;
import com.epam.esm.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagServiceImpl implements TagService {

    @Autowired
    public TagDAO

    @Override
    public List<TagDTO> findAll() {

    }

    @Override
    public TagDTO findById(Long aLong) {
        return null;
    }

    @Override
    public TagDTO create(TagDTO tagDTO) {
        return null;
    }

    @Override
    public boolean delete(Long aLong) {
        return false;
    }
}
