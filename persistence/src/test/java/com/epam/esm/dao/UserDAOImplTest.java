package com.epam.esm.dao;

import com.epam.esm.config.TestConfig;
import com.epam.esm.entity.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

@SpringBootTest(classes = {TestConfig.class})
@ActiveProfiles("dev")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class UserDAOImplTest {

    @Autowired
    private UserDAO userDAO;

    @Test
    void findAllValid() {
        List<User> tags = userDAO.findAll();
        Assertions.assertEquals(1, tags.size());
    }

    @Test
    void findByIdValid() {
        Long id = 1L;
        Optional<User> actual = userDAO.findById(id);
        Assertions.assertEquals("Roman", actual.get().getName());
    }

    @Test
    void findByIdNotExists() {
        Long id = 112L;
        Optional<User> actual = userDAO.findById(id);
        Assertions.assertTrue(actual.isEmpty());
    }
}