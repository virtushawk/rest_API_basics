package com.epam.esm.dao;

import com.epam.esm.entity.Tag;

import java.util.List;

public interface TagDAO extends BaseDAO<Tag,Long> {
    List<Tag> findAllByCertificateId(Long certificateId);
}
