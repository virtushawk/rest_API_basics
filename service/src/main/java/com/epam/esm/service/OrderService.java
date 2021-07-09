package com.epam.esm.service;

import com.epam.esm.dto.OrderDTO;
import com.epam.esm.entity.Page;

import java.util.List;

/**
 * The interface Order service.
 */
public interface OrderService extends BaseService<OrderDTO, Long> {

    /**
     * Find all by user id list.
     *
     * @param id   the id
     * @param page the page
     * @return the list
     */
    List<OrderDTO> findAllByUserId(Long id, Page page);
}
