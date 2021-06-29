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
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * The type Order service.
 */
@Service
@AllArgsConstructor
@Slf4j
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
    @Transactional
    public OrderDTO create(OrderDTO orderDTO) {
        Optional<User> user = userDAO.findById(orderDTO.getUserId());
        if (user.isEmpty()) {
            throw new UserNotFoundException(orderDTO.getUserId().toString());
        }
        orderDTO.setCost(new BigDecimal(0));
        orderDTO.getCertificateId().forEach(id -> {
            Optional<Certificate> certificate = certificateDAO.findById(id);
            if (certificate.isEmpty()) {
                throw new CertificateNotFoundException(id.toString());
            }
            orderDTO.setCost(orderDTO.getCost().add(certificate.get().getPrice()));
        });
        Order order = mapperDTO.convertDTOToOrder(orderDTO);
        OrderDTO result = mapperDTO.convertOrderToDTO(orderDAO.create(order));
        result.setCertificateId(orderDTO.getCertificateId());
        result.getCertificateId()
                .forEach(id -> orderDAO.attachCertificate(result.getId(),id)
        );
        return result;
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

    @Override
    public OrderDTO findByUserIdOrderId(Long userId, Long orderId) {
        List<OrderDTO> orders = findAllByUserId(userId);
        if (orders.size() > orderId) {
        }
        return null;
    }
}
