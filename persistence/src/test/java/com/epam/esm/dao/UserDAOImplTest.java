package com.epam.esm.dao;

import com.epam.esm.config.TestConfig;
import com.epam.esm.creator.EntityCreator;
import com.epam.esm.entity.Page;
import com.epam.esm.entity.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.List;
import java.util.Optional;

@SpringBootTest(classes = {TestConfig.class})
@ActiveProfiles({"dev", "jpaData"})
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@Sql(scripts = "classpath:/insert_data_certificate.sql")
class UserDAOImplTest {

    @Autowired
    private UserDAO userDAO;

    @Test
    void findAllValid() {
        List<User> users = userDAO.findAll(EntityCreator.page);
        Assertions.assertFalse(users.isEmpty());
    }

    @Test
    void findByIdValid() {
        Long id = 1L;
        Optional<User> actual = userDAO.findById(id);
        Assertions.assertEquals(id, actual.get().getId());
    }

    @Test
    void findByIdNotExists() {
        Long id = 112L;
        Optional<User> actual = userDAO.findById(id);
        Assertions.assertTrue(actual.isEmpty());
    }
}
