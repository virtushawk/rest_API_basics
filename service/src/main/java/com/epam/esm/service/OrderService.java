package com.epam.esm.service;

import com.epam.esm.dto.OrderDTO;

import java.util.List;

/**
 * The interface Order service.
 */
public interface OrderService extends BaseService<OrderDTO, Long> {
    /**
     * Find orders by user id
     *
     * @param id the id
     * @return the list
     */
    List<OrderDTO> findAllByUserId(Long id);
}
