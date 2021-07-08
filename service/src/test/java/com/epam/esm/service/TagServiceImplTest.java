package com.epam.esm.service;

import com.epam.esm.dao.CertificateDAO;
import com.epam.esm.dao.TagDAO;
import com.epam.esm.dto.TagDTO;
import com.epam.esm.entity.Certificate;
import com.epam.esm.entity.Page;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.CertificateNotFoundException;
import com.epam.esm.exception.TagNotFoundException;
import com.epam.esm.service.impl.TagServiceImpl;
import com.epam.esm.util.MapperDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@ExtendWith(MockitoExtension.class)
class TagServiceImplTest {

    @InjectMocks
    private TagServiceImpl tagService;

    @Mock
    public TagDAO tagDAO;

    @Mock
    CertificateDAO certificateDAO;

    @Mock
    public MapperDTO mapperDTO;

    private Tag tag;
    private TagDTO tagDTO;

    @BeforeEach
    public void initEach() {
        tag = Tag.builder().name("Test case 1").build();
        tagDTO = TagDTO.builder().name("Test case 1").build();
    }

    @Test
    void findALlValid() {
        List<Tag> tags = new ArrayList<>();
        tags.add(tag);
        Page page = new Page();
        List<TagDTO> expected = tags.stream().map(mapperDTO::convertTagToDTO).collect(Collectors.toList());
        Mockito.when(tagDAO.findAll(page)).thenReturn(tags);
        List<TagDTO> actual = tagService.findAll(page);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void findAllEmpty() {
        List<Tag> tags = new ArrayList<>();
        List<TagDTO> expected = new ArrayList<>();
        Page page = new Page();
        Mockito.when(tagDAO.findAll(page)).thenReturn(tags);
        List<TagDTO> actual = tagService.findAll(page);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void findByIdValid() {
        Optional<Tag> optionalTag = Optional.of(tag);
        Long id = 1L;
        TagDTO expected = mapperDTO.convertTagToDTO(tag);
        Mockito.when(tagDAO.findById(id)).thenReturn(optionalTag);
        TagDTO actual = tagService.findById(id);
        Assertions.assertEquals(expected, actual);
    }

    @Test()
    void findByIdException() {
        Long id = 1L;
        Mockito.when(tagDAO.findById(id)).thenReturn(Optional.empty());
        Assertions.assertThrows(TagNotFoundException.class, () -> {
            tagService.findById(id);
        });
    }

    @Test
    void createValid() {
        Mockito.when(tagDAO.findOrCreate(tag)).thenReturn(tag);
        Mockito.when(mapperDTO.convertTagToDTO(tag)).thenReturn(tagDTO);
        Mockito.when(mapperDTO.convertDTOToTag(tagDTO)).thenReturn(tag);
        TagDTO actual = tagService.create(tagDTO);
        Assertions.assertEquals(tagDTO, actual);
    }

    @Test
    void deleteException() {
        Long id = 1L;
        Mockito.when(tagDAO.findById(id)).thenReturn(Optional.empty());
        Assertions.assertThrows(TagNotFoundException.class, () -> {
            tagService.delete(id);
        });
    }

    @Test
    void findAllByCertificateIdValid() {
        Long id = 1L;
        Certificate certificate = Certificate.builder().isActive(true).tags(new HashSet<>()).build();
        Mockito.when(certificateDAO.findById(id)).thenReturn(Optional.of(certificate));
        List<TagDTO> actual = tagService.findAllByCertificateId(id);
        Assertions.assertTrue(actual.isEmpty());
    }

    @Test
    void findAllByCertificateIdException() {
        Long id = 1L;
        Mockito.when(certificateDAO.findById(id)).thenReturn(Optional.empty());
        Assertions.assertThrows(CertificateNotFoundException.class, () -> {
            tagService.findAllByCertificateId(id);
        });
    }

    @Test
    void findPopularValid() {
        Mockito.when(tagDAO.findPopular()).thenReturn(tag);
        Mockito.when(mapperDTO.convertTagToDTO(tag)).thenReturn(tagDTO);
        TagDTO actual = tagService.findPopular();
        Assertions.assertEquals(tagDTO, actual);
    }
}
