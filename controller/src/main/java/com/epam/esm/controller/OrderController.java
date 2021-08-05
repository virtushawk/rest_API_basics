package com.epam.esm.controller;

import com.epam.esm.dto.CertificateDTO;
import com.epam.esm.dto.OrderDTO;
import com.epam.esm.entity.Page;
import com.epam.esm.service.CertificateService;
import com.epam.esm.service.OrderService;
import com.epam.esm.util.ResponseAssembler;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

/**
 * The type Order controller.
 */
@RestController
@RequestMapping(value = "/orders")
@AllArgsConstructor
@Validated
public class OrderController {

    private final OrderService orderService;
    private final CertificateService certificateService;

    private static final int MIN_ID_VALUE = 1;

    /**
     * Find all.
     *
     * @param page the page
     * @return the list
     */
    @GetMapping
    @PreAuthorize("hasAuthority('user') or hasAuthority('admin')")
    public List<OrderDTO> findAll(@Valid Page page) {
        return ResponseAssembler.assembleOrders(orderService.findAll(page));
    }

    /**
     * Create order
     *
     * @param orderDTO the order dto
     * @return the order dto
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('user') or hasAuthority('admin')")
    public OrderDTO create(@Valid @RequestBody OrderDTO orderDTO) {
        return ResponseAssembler.assembleOrder(orderService.create(orderDTO));
    }

    /**
     * Find all by order id list.
     *
     * @param id   the id
     * @param page the page
     * @return the list
     */
    @GetMapping(value = "/{id}/certificates")
    @PreAuthorize("hasAuthority('user') or hasAuthority('admin')")
    public List<CertificateDTO> findAllByOrderId(@PathVariable @Min(MIN_ID_VALUE) Long id, @Valid Page page) {
        return ResponseAssembler.assembleCertificates(certificateService.findAllByOrderId(id, page));
    }

    /**
     * Find order by id
     *
     * @param id the id
     * @return the order dto
     */
    @GetMapping(value = "/{id}")
    @PreAuthorize("hasAuthority('user') or hasAuthority('admin')")
    public OrderDTO findById(@PathVariable @Min(MIN_ID_VALUE) Long id) {
        return ResponseAssembler.assembleOrder(orderService.findById(id));
    }

}
