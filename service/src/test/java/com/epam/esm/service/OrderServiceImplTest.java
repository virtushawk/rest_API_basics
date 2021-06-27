package com.epam.esm.service;

import com.epam.esm.dao.CertificateDAO;
import com.epam.esm.dao.OrderDAO;
import com.epam.esm.dao.UserDAO;
import com.epam.esm.dto.OrderDTO;
import com.epam.esm.entity.Certificate;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.User;
import com.epam.esm.exception.CertificateNotFoundException;
import com.epam.esm.exception.UserNotFoundException;
import com.epam.esm.service.impl.OrderServiceImpl;
import com.epam.esm.util.MapperDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class OrderServiceImplTest {

    @InjectMocks
    private OrderServiceImpl orderService;

    @Mock
    public OrderDAO orderDAO;

    @Mock
    public MapperDTO mapperDTO;

    @Mock
    public CertificateDAO certificateDAO;

    @Mock
    public UserDAO userDAO;

    private static Order order;
    private static OrderDTO orderDTO;

    @BeforeEach
    public void initEach() {
        order = Order.builder()
                .certificateId(2L)
                .userId(2L)
                .cost(new BigDecimal("11"))
                .build();
        orderDTO = OrderDTO.builder()
                .certificateId(2L)
                .userId(2L)
                .cost(new BigDecimal("11"))
                .build();
    }

    @Test
    void createValid() {
        Mockito.when(certificateDAO.findById(order.getCertificateId())).thenReturn(Optional.of(new Certificate()));
        Mockito.when(userDAO.findById(order.getUserId())).thenReturn(Optional.of(new User()));
        Mockito.when(orderDAO.findByCertificateId(order.getCertificateId())).thenReturn(Optional.empty());
        Mockito.when(orderDAO.create(order)).thenReturn(order);
        Mockito.when(mapperDTO.convertOrderToDTO(order)).thenReturn(orderDTO);
        Mockito.when(mapperDTO.convertDTOToOrder(orderDTO)).thenReturn(order);
        OrderDTO expected = mapperDTO.convertOrderToDTO(order);
        OrderDTO actual = orderService.create(expected);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void createCertificateIdInvalid() {
        Mockito.when(certificateDAO.findById(order.getCertificateId())).thenReturn(Optional.empty());
        Assertions.assertThrows(CertificateNotFoundException.class, () -> {
            orderService.create(orderDTO);
        });
    }

    @Test
    void createUserIdInvalid() {
        Mockito.when(certificateDAO.findById(order.getCertificateId())).thenReturn(Optional.of(new Certificate()));
        Mockito.when(userDAO.findById(order.getUserId())).thenReturn(Optional.empty());
        Assertions.assertThrows(UserNotFoundException.class, () -> {
            orderService.create(orderDTO);
        });
    }

    @Test
    void createOrderExist() {
        Mockito.when(certificateDAO.findById(order.getCertificateId())).thenReturn(Optional.of(new Certificate()));
        Mockito.when(userDAO.findById(order.getUserId())).thenReturn(Optional.of(new User()));
        Mockito.when(orderDAO.findByCertificateId(order.getCertificateId())).thenReturn(Optional.ofNullable(order));
        Mockito.when(mapperDTO.convertOrderToDTO(order)).thenReturn(orderDTO);
        OrderDTO actual = orderService.create(orderDTO);
        Assertions.assertEquals(actual, orderDTO);
    }

}
