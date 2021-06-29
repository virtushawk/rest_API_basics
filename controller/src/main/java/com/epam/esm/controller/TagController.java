package com.epam.esm.controller;

import com.epam.esm.dto.TagDTO;
import com.epam.esm.exception.IdInvalidException;
import com.epam.esm.exception.InvalidDataFormException;
import com.epam.esm.service.TagService;

import javax.validation.Valid;

import com.epam.esm.util.ResponseAssembler;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * The type Tag controller.
 */
@RestController
@RequestMapping(value = "/tags")
@AllArgsConstructor
public class TagController {

    private final TagService service;

    /**
     * Finds all tags.
     *
     * @return the all tags
     */
    @GetMapping
    public List<TagDTO> findAll() {
        return ResponseAssembler.assembleTags(service.findAll());
    }

    /**
     * Create tag
     *
     * @param tagDTO        the tag dto
     * @param bindingResult the binding result
     * @return the tag dto
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TagDTO create(@Valid @RequestBody TagDTO tagDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new InvalidDataFormException(bindingResult);
        }
        return ResponseAssembler.assembleTag(service.create(tagDTO));
    }

    /**
     * Gets tag.
     *
     * @param id the id
     * @return the tag
     */
    @GetMapping(value = "/{id}")
    public TagDTO findById(@PathVariable Long id) {
        if (id < 0) {
            throw new IdInvalidException(id);
        }
        return ResponseAssembler.assembleTag(service.findById(id));
    }

    /**
     * Delete tag.
     *
     * @param id the id
     */
    @DeleteMapping(value = "{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        if (id < 0) {
            throw new IdInvalidException(id);
        }
        service.delete(id);
    }
}
