package com.epam.esm.dao;

import com.epam.esm.config.TestConfig;
import com.epam.esm.entity.Order;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;

@SpringBootTest(classes = {TestConfig.class})
@ActiveProfiles("dev")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class OrderDAOImplTest {

    @Autowired
    private OrderDAO orderDAO;

    @Test
    void createValid() {
        Order order = Order.builder()
                .certificateId(2L)
                .userId(2L)
                .cost(new BigDecimal("11"))
                .build();
        Order actual = orderDAO.create(order);
        Assertions.assertEquals(order.getCertificateId(), actual.getCertificateId());
    }

    @Test
    void createExistingValid() {
        Order order = Order.builder()
                .certificateId(1L)
                .userId(1L)
                .cost(new BigDecimal("11"))
                .build();
        Order actual = orderDAO.create(order);
        Assertions.assertEquals(order.getCertificateId(), actual.getCertificateId());
    }
}
