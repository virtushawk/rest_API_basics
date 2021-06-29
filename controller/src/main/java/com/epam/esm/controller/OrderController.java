package com.epam.esm.controller;

import com.epam.esm.dto.CertificateDTO;
import com.epam.esm.dto.OrderDTO;
import com.epam.esm.exception.IdInvalidException;
import com.epam.esm.exception.InvalidDataFormException;
import com.epam.esm.service.CertificateService;
import com.epam.esm.service.OrderService;
import com.epam.esm.util.ResponseAssembler;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * The type Order controller.
 */
@RestController
@RequestMapping(value = "/orders")
@AllArgsConstructor
@Slf4j
public class OrderController {

    private final OrderService orderService;
    private final CertificateService certificateService;

    /**
     * Find orders
     *
     * @return the list
     */
    @GetMapping
    public List<OrderDTO> findAll() {
        return ResponseAssembler.assembleOrders(orderService.findAll());
    }

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
        return ResponseAssembler.assembleOrder(orderService.create(orderDTO));
    }

    /**
     * Find certificates by order id
     *
     * @param id the id
     * @return the list
     */
    @GetMapping(value = "/{id}/certificates")
    public List<CertificateDTO> findCertificatesByOrderId(@PathVariable Long id) {
        if (id < 0) {
            throw new IdInvalidException(id);
        }
        return ResponseAssembler.assembleCertificates(certificateService.findAllByOrderId(id));
    }

    /**
     * Find order by id
     *
     * @param id the id
     * @return the order dto
     */
    @GetMapping(value = "/{id}")
    public OrderDTO findById(@PathVariable Long id) {
        if (id < 0) {
            throw new IdInvalidException(id);
        }
        return ResponseAssembler.assembleOrder(orderService.findById(id));
    }
}
