package com.epam.esm.controller;

import com.epam.esm.dto.OrderDTO;
import com.epam.esm.dto.UserDTO;
import com.epam.esm.exception.IdInvalidException;
import com.epam.esm.service.OrderService;
import com.epam.esm.service.UserService;
import com.epam.esm.util.ResponseAssembler;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * The type User controller.
 */
@RestController
@RequestMapping(value = "/users")
@AllArgsConstructor
public class UserController {

    private final UserService userService;
    private final OrderService orderService;

    /**
     * Finds all users
     *
     * @return the list
     */
    @GetMapping
    public List<UserDTO> findAll() {
        return ResponseAssembler.assembleUsers(userService.findAll());
    }

    /**
     * Finds user by user id
     *
     * @param id the id
     * @return the user dto
     */
    @GetMapping(value = "/{id}")
    public UserDTO findById(@PathVariable Long id) {
        if (id < 0) {
            throw new IdInvalidException(id);
        }
        return ResponseAssembler.assembleUser(userService.findById(id));
    }

    /**
     * Find user orders list.
     *
     * @param id the id
     * @return the list
     */
    @GetMapping(value = "/{id}/orders")
    public List<OrderDTO> findUserOrders(@PathVariable Long id) {
        if (id < 0) {
            throw new IdInvalidException(id);
        }
        return ResponseAssembler.assembleOrders(orderService.findAllByUserId(id));
    }

    @GetMapping(value = "/{userId}/orders/{orderId}")
    public OrderDTO findUserOrderById(@PathVariable Long userId, @PathVariable Long orderId) {
        if (userId < 0) {
            throw new IdInvalidException(userId);
        }
        if (orderId < 0) {
            throw new IdInvalidException(userId);
        }
        return null;
    }
}
