package com.epam.esm.service;

import com.epam.esm.dao.TagDAO;
import com.epam.esm.service.impl.TagServiceImpl;
import com.epam.esm.util.MapperDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


@ExtendWith(MockitoExtension.class)
class TagServiceImplTest {

    @InjectMocks
    private TagServiceImpl tagService;

    @Mock
    public  TagDAO tagDAO;

    @Mock
    public  MapperDTO mapperDTO;

    @Test
    void create() {
        System.out.println(tagService);
    }

}
