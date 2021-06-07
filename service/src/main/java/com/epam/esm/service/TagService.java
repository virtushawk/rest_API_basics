package com.epam.esm.service;

import com.epam.esm.model.Tag;

public interface TagService {
    Tag createTag(String name);
    Tag findTag(long id);
    Tag deleteTag(long id);
}
