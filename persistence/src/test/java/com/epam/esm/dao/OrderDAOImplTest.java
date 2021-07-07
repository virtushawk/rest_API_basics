package com.epam.esm.dao;

import com.epam.esm.config.TestConfig;
import com.epam.esm.entity.Certificate;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.Page;
import com.epam.esm.entity.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootTest(classes = {TestConfig.class})
@ActiveProfiles("dev")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class OrderDAOImplTest {

    @Autowired
    private OrderDAO orderDAO;

    @Test
    void findAllValid() {
        Page page = new Page();
        List<Order> certificates = orderDAO.findAll(page);
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

    @Test
    void createValid() {
        Order order = Order.builder()
                .certificates(new ArrayList<>())
                .user(User.builder().id(1L).name("Roman").build())
                .cost(new BigDecimal(5))
                .build();
        order.getCertificates().add(Certificate.builder().name("test").description("test").price(new BigDecimal(5)).duration(1).build());
        Order actual = orderDAO.create(order);
        Assertions.assertEquals(order.getCost(), actual.getCost());
    }
}
