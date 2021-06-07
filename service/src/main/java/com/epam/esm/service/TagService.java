package com.epam.esm.service;

import com.epam.esm.entity.Tag;

public interface TagService {
    Tag createTag(String name);
    Tag findTag(long id);
    Tag deleteTag(long id);
}
