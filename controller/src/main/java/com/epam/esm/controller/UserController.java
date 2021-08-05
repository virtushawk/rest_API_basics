package com.epam.esm.controller;

import com.epam.esm.dto.OrderDTO;
import com.epam.esm.dto.UserDTO;
import com.epam.esm.entity.Page;
import com.epam.esm.service.OrderService;
import com.epam.esm.service.UserService;
import com.epam.esm.util.ResponseAssembler;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

/**
 * The type User controller.
 */
@RestController
@RequestMapping(value = "/users")
@AllArgsConstructor
@Validated
public class UserController {

    private final UserService userService;
    private final OrderService orderService;

    private static final int MIN_ID_VALUE = 1;

    /**
     * Finds all users
     *
     * @return the list
     */
    @GetMapping
    @PreAuthorize("hasAuthority('admin')")
    public List<UserDTO> findAll(@Valid Page page) {
        return ResponseAssembler.assembleUsers(userService.findAll(page));
    }

    /**
     * Finds user by user id
     *
     * @param id the id
     * @return the user dto
     */
    @GetMapping(value = "/{id}")
    @PreAuthorize("hasAuthority('admin')")
    public UserDTO findById(@PathVariable @Min(MIN_ID_VALUE) Long id) {
        return ResponseAssembler.assembleUser(userService.findById(id));
    }

    /**
     * Find user orders list.
     *
     * @param id the id
     * @return the list
     */
    @GetMapping(value = "/{id}/orders")
    @PreAuthorize("hasAuthority('admin')")
    public List<OrderDTO> findUserOrders(@PathVariable @Min(MIN_ID_VALUE) Long id, @Valid Page page) {
        return ResponseAssembler.assembleOrders(orderService.findAllByUserId(id, page));
    }

    /**
     * Find user's orders by id
     *
     * @param userId  the user id
     * @param orderId the order id
     * @return the order dto
     */
    @GetMapping(value = "/{userId}/orders/{orderId}")
    @PreAuthorize("hasAuthority('admin')")
    public OrderDTO findUserOrderById(@PathVariable @Min(MIN_ID_VALUE) Long userId, @PathVariable @Min(MIN_ID_VALUE) Long orderId) {
        return ResponseAssembler.assembleOrder(orderService.findById(orderId));
    }
}
