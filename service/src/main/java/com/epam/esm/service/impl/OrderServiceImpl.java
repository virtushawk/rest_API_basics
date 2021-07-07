package com.epam.esm.service.impl;

import com.epam.esm.dao.CertificateDAO;
import com.epam.esm.dao.OrderDAO;
import com.epam.esm.dao.UserDAO;
import com.epam.esm.dto.OrderDTO;
import com.epam.esm.entity.Certificate;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.Page;
import com.epam.esm.entity.User;
import com.epam.esm.exception.CertificateNotFoundException;
import com.epam.esm.exception.OrderNotFoundException;
import com.epam.esm.exception.UserNotFoundException;
import com.epam.esm.service.OrderService;
import com.epam.esm.util.MapperDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * The type Order service.
 */
@Service
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderDAO orderDAO;
    private final CertificateDAO certificateDAO;
    private final UserDAO userDAO;
    private final MapperDTO mapperDTO;


    @Override
    public List<OrderDTO> findAll(Page page) {
        return orderDAO.findAll(page)
                .stream()
                .map(mapperDTO::convertOrderToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public OrderDTO findById(Long id) {
        return mapperDTO.convertOrderToDTO(orderDAO.findById(id)
                .orElseThrow(() -> new OrderNotFoundException(id.toString())));
    }

    @Override
    @Transactional
    public OrderDTO create(OrderDTO orderDTO) {
        User user = userDAO.findById(orderDTO.getUserId())
                .orElseThrow(() -> new UserNotFoundException(orderDTO.getUserId().toString()));
        orderDTO.setCost(new BigDecimal(0));
        List<Certificate> certificates = new ArrayList<>();
        orderDTO.getCertificateId().forEach(id -> {
            Optional<Certificate> optional = certificateDAO.findById(id);
            if (optional.isEmpty() || !optional.get().isActive()) {
                throw new CertificateNotFoundException(id.toString());
            }
            certificates.add(optional.get());
            orderDTO.setCost(orderDTO.getCost().add(optional.get().getPrice()));
        });
        Order order = mapperDTO.convertDTOToOrder(orderDTO);
        order.setUser(user);
        order.setCertificates(certificates);
        order = orderDAO.create(order);
        return mapperDTO.convertOrderToDTO(order);
    }

    @Override
    public void delete(Long id) {
        throw new UnsupportedOperationException("method not allowed");
    }

    @Override
    public List<OrderDTO> findAllByUserId(Long id) {
        return userDAO
                .findById(id)
                .orElseThrow(() -> new UserNotFoundException(id.toString()))
                .getOrders()
                .stream()
                .distinct()
                .map(mapperDTO::convertOrderToDTO)
                .collect(Collectors.toList());
    }
}
