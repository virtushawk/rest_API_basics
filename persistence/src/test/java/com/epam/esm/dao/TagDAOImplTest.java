package com.epam.esm.dao;

import com.epam.esm.config.TestConfig;
import com.epam.esm.entity.Certificate;
import com.epam.esm.entity.Page;
import com.epam.esm.entity.Tag;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest(classes = {TestConfig.class})
@ActiveProfiles("dev")
@Sql(scripts = "classpath:/insert_data_certificate.sql")
class TagDAOImplTest {

    @Autowired
    private TagDAO tagDAO;

    @Test
    void findAllValid() {
        Page page = new Page();
        List<Tag> certificates = tagDAO.findAll(page);
        Assertions.assertFalse(certificates.isEmpty());
    }


    @Test
    @Transactional
    void createNewValid() {
        Tag tag = Tag.builder()
                .name("tag")
                .build();
        Tag actual = tagDAO.create(tag);
        Assertions.assertEquals(tag.getName(), actual.getName());
    }

    @Test
    @Transactional
    void createExistingValid() {
        Tag tag = Tag.builder()
                .name("IT")
                .build();
        Tag actual = tagDAO.create(tag);
        Assertions.assertEquals(tag.getName(), actual.getName());
    }

    @Test
    void findByIdValid() {
        Long id = 1L;
        Optional<Tag> actual = tagDAO.findById(id);
        Assertions.assertEquals("IT", actual.get().getName());
    }

    @Test
    void findByIdNotExists() {
        Long id = 112L;
        Optional<Tag> actual = tagDAO.findById(id);
        Assertions.assertTrue(actual.isEmpty());
    }

    @Test
    void findByNameValid() {
        String name = "IT";
        Optional<Tag> actual = tagDAO.findByName(name);
        Assertions.assertEquals(name, actual.get().getName());
    }

    @Test
    void findByNameNotExists() {
        String name = "test case";
        Optional<Tag> actual = tagDAO.findByName(name);
        Assertions.assertTrue(actual.isEmpty());
    }

    @Test
    void findOrCreateTagExist() {
        Tag tag = Tag.builder().name("IT").build();
        Tag actual = tagDAO.findOrCreate(tag);
        Assertions.assertEquals(tag.getName(), actual.getName());
    }

    @Test
    @Transactional
    void findOrCreateTagNotExist() {
        Tag tag = Tag.builder().name("new tag").build();
        Tag actual = tagDAO.findOrCreate(tag);
        Assertions.assertEquals(tag.getName(), actual.getName());
    }

    @Test
    void findPopularValid() {
        String expected = "IT";
        String actual = tagDAO.findPopular().getName();
        Assertions.assertEquals(expected, actual);
    }

}
