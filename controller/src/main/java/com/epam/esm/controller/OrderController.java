package com.epam.esm.controller;

import com.epam.esm.dto.OrderDTO;
import com.epam.esm.exception.InvalidDataFormException;
import com.epam.esm.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * The type Order controller.
 */
@RestController
@RequestMapping(value = "/orders")
@AllArgsConstructor
public class OrderController {

    private final OrderService orderService;

    /**
     * Create order
     *
     * @param orderDTO      the order dto
     * @param bindingResult the binding result
     * @return the order dto
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OrderDTO create(@Valid @RequestBody OrderDTO orderDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new InvalidDataFormException(bindingResult);
        }
        return orderService.create(orderDTO);
    }
}
