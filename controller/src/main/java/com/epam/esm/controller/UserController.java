package com.epam.esm.controller;

import com.epam.esm.dto.UserDTO;
import com.epam.esm.exception.IdInvalidException;
import com.epam.esm.service.UserService;
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

    private final UserService service;

    /**
     * Finds all users
     *
     * @return the list
     */
    @GetMapping
    public List<UserDTO> findAll() {
        return service.findAll();
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
        return service.findById(id);
    }

}
