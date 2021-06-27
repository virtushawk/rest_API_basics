package com.epam.esm.service.impl;

import com.epam.esm.dao.CertificateDAO;
import com.epam.esm.dao.OrderDAO;
import com.epam.esm.dao.UserDAO;
import com.epam.esm.dto.OrderDTO;
import com.epam.esm.entity.Certificate;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.User;
import com.epam.esm.exception.CertificateNotFoundException;
import com.epam.esm.exception.UserNotFoundException;
import com.epam.esm.service.OrderService;
import com.epam.esm.util.MapperDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

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
    public List<OrderDTO> findAll() {
        return null;
    }

    @Override
    public OrderDTO findById(Long aLong) {
        return null;
    }

    @Override
    public OrderDTO create(OrderDTO orderDTO) {
        Optional<Certificate> certificate = certificateDAO.findById(orderDTO.getCertificateId());
        if (certificate.isEmpty()) {
            throw new CertificateNotFoundException(orderDTO.getCertificateId().toString());
        }
        Optional<User> user = userDAO.findById(orderDTO.getUserId());
        if (user.isEmpty()) {
            throw new UserNotFoundException(orderDTO.getUserId().toString());
        }
        Optional<Order> optionalOrder = orderDAO.findByCertificateId(orderDTO.getCertificateId());
        if (optionalOrder.isPresent()) {
            return mapperDTO.convertOrderToDTO(optionalOrder.get());
        }
        Order order = mapperDTO.convertDTOToOrder(orderDTO);
        return mapperDTO.convertOrderToDTO(orderDAO.create(order));
    }

    @Override
    public boolean delete(Long id) {
        return false;
    }

    @Override
    public List<OrderDTO> findAllByUserId(Long id) {
        Optional<User> user = userDAO.findById(id);
        if (user.isEmpty()) {
            throw new UserNotFoundException(id.toString());
        }
        return orderDAO.findAllByUserId(id).stream().map(mapperDTO::convertOrderToDTO).collect(Collectors.toList());
    }
}
