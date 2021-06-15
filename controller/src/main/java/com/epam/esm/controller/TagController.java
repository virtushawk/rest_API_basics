package com.epam.esm.controller;

import com.epam.esm.dto.TagDTO;
import com.epam.esm.exception.IdInvalidException;
import com.epam.esm.exception.InvalidDataFormException;
import com.epam.esm.service.TagService;

import javax.validation.Valid;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

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
     * Gets all tags.
     *
     * @return the all tags
     */
    @GetMapping
    public List<TagDTO> getAllTags() {
        return service.findAll();
    }

    /**
     * Create tag
     *
     * @param tagDTO        the tag dto
     * @param bindingResult the binding result
     * @return the tag dto
     */
    @PostMapping
    public TagDTO createTag(@Valid @RequestBody TagDTO tagDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new InvalidDataFormException(bindingResult);
        }
        return service.create(tagDTO);
    }

    /**
     * Gets tag.
     *
     * @param id the id
     * @return the tag
     */
    @GetMapping(value = "/{id}")
    public TagDTO getTag(@PathVariable Long id) {
        if (id < 0) {
            throw new IdInvalidException(id);
        }
        return service.findById(id);
    }

    /**
     * Delete tag.
     *
     * @param id the id
     */
    @DeleteMapping(value = "{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTag(@PathVariable Long id) {
        if (id < 0) {
            throw new IdInvalidException(id);
        }
        service.delete(id);
    }
}
