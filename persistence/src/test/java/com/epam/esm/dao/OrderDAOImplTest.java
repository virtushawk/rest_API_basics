package com.epam.esm.dao;

import com.epam.esm.config.TestConfig;
import com.epam.esm.creator.EntityCreator;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.Page;
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

@SpringBootTest(classes = {TestConfig.class})
@ActiveProfiles("dev")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@Sql(scripts = "classpath:/insert_data_certificate.sql")
class OrderDAOImplTest {

    @Autowired
    private OrderDAO orderDAO;

    public static Object[][] orderData() {
        return new Object[][]{
                {EntityCreator.order}
        };
    }

    @Test
    void findAllValid() {
        List<Order> certificates = orderDAO.findAll(new Page());
        Assertions.assertFalse(certificates.isEmpty());
    }

    @Test
    void findByIdValid() {
        Long id = 1L;
        Optional<Order> actual = orderDAO.findById(id);
        Assertions.assertFalse(actual.isEmpty());
    }

    @Test
    void findByIdNotExists() {
        Long id = 1244L;
        Optional<Order> actual = orderDAO.findById(id);
        Assertions.assertTrue(actual.isEmpty());
    }

    @ParameterizedTest
    @MethodSource("orderData")
    @Transactional
    void createValid(Order order) {
        Order actual = orderDAO.create(order);
        Assertions.assertEquals(order.getCost(), actual.getCost());
    }
}
