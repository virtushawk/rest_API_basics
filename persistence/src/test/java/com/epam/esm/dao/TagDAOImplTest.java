package com.epam.esm.dao;

import com.epam.esm.config.TestConfig;
import com.epam.esm.creator.EntityCreator;
import com.epam.esm.entity.Page;
import com.epam.esm.entity.Tag;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
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

    public static Object[][] tagData() {
        return new Object[][]{
                {EntityCreator.tag},
                {EntityCreator.existingTag}
        };
    }

    @Test
    void findAllValid() {
        List<Tag> certificates = tagDAO.findAll(new Page());
        Assertions.assertFalse(certificates.isEmpty());
    }

    @ParameterizedTest
    @MethodSource("tagData")
    @Transactional
    void create(Tag tag) {
        Tag actual = tagDAO.create(tag);
        Assertions.assertEquals(tag.getName(), actual.getName());
    }

    @Test
    void findByIdValid() {
        Long id = 1L;
        Optional<Tag> actual = tagDAO.findById(id);
        Assertions.assertEquals(id, actual.get().getId());
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

    @ParameterizedTest
    @MethodSource("tagData")
    @Transactional
    void findOrCreateTagExist(Tag tag) {
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
