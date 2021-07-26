package com.epam.esm.service;

import com.epam.esm.dao.CertificateDAO;
import com.epam.esm.dao.OrderDAO;
import com.epam.esm.dao.UserDAO;
import com.epam.esm.dto.OrderDTO;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.Page;
import com.epam.esm.entity.User;
import com.epam.esm.exception.OrderNotFoundException;
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
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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
    private static Pageable pageable;

    @BeforeEach
    public void initEach() {
        order = Order.builder()
                .cost(new BigDecimal("11"))
                .build();
        orderDTO = OrderDTO.builder()
                .userId(1L)
                .certificateId(new ArrayList<>())
                .cost(new BigDecimal("11"))
                .build();
        pageable = PageRequest.of(0, 10);
    }

    @Test
    void findAllValid() {
        List<Order> orders = new ArrayList<>();
        List<OrderDTO> expected = new ArrayList<>();
        orders.add(order);
        expected.add(orderDTO);
        Mockito.when(mapperDTO.convertOrderToDTO(order)).thenReturn(orderDTO);
        Mockito.when(orderDAO.findAll(pageable)).thenReturn(orders);
        List<OrderDTO> actual = orderService.findAll(new Page());
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void findAllEmpty() {
        List<Order> orders = new ArrayList<>();
        List<OrderDTO> expected = new ArrayList<>();
        Mockito.when(orderDAO.findAll(pageable)).thenReturn(orders);
        List<OrderDTO> actual = orderService.findAll(new Page());
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void findByIdValid() {
        Optional<Order> optionalOrder = Optional.of(order);
        Long id = 1L;
        Mockito.when(orderDAO.findById(id)).thenReturn(optionalOrder);
        Mockito.when(mapperDTO.convertOrderToDTO(order)).thenReturn(orderDTO);
        OrderDTO actual = orderService.findById(id);
        Assertions.assertEquals(orderDTO, actual);
    }

    @Test
    void findByIdException() {
        Long id = 1L;
        Mockito.when(orderDAO.findById(id)).thenReturn(Optional.empty());
        Assertions.assertThrows(OrderNotFoundException.class, () -> orderService.findById(id));
    }

    @Test
    void createValid() {
        Mockito.when(userDAO.findById(1L)).thenReturn(Optional.of(new User()));
        Mockito.when(mapperDTO.convertDTOToOrder(orderDTO)).thenReturn(order);
        Mockito.when(orderDAO.create(order)).thenReturn(order);
        Mockito.when(mapperDTO.convertOrderToDTO(order)).thenReturn(orderDTO);
        OrderDTO actual = orderService.create(orderDTO);
        Assertions.assertEquals(orderDTO, actual);
    }

    @Test
    void findAllByUserIdValid() {
        Long id = 1L;
        User user = User.builder().orders(new HashSet<>()).build();
        Mockito.when(userDAO.findById(id)).thenReturn(Optional.of(user));
        List<OrderDTO> actual = orderService.findAllByUserId(id, new Page());
        Assertions.assertTrue(actual.isEmpty());
    }

    @Test
    void findAllByUserIdException() {
        Long id = 1L;
        Page page = new Page();
        Mockito.when(userDAO.findById(id)).thenReturn(Optional.empty());
        Assertions.assertThrows(UserNotFoundException.class, () -> orderService.findAllByUserId(id, page));
    }
}
