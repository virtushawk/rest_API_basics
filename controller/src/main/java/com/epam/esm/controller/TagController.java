package com.epam.esm.controller;

import com.epam.esm.dto.TagDTO;
import com.epam.esm.entity.Page;
import com.epam.esm.service.TagService;

import javax.validation.Valid;
import javax.validation.constraints.Min;

import com.epam.esm.util.ResponseAssembler;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
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
@Validated
public class TagController {

    private final TagService service;

    private static final int MIN_ID_VALUE = 1;

    /**
     * Find all.
     *
     * @param page the page
     * @return the list
     */
    @GetMapping
    @PreAuthorize("hasAuthority('user') or hasAuthority('admin')")
    public List<TagDTO> findAll(@Valid Page page) {
        return ResponseAssembler.assembleTags(service.findAll(page));
    }

    /**
     * Create tag
     *
     * @param tagDTO the tag dto
     * @return the tag dto
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('user') or hasAuthority('admin')")
    public TagDTO create(@Valid @RequestBody TagDTO tagDTO) {
        return ResponseAssembler.assembleTag(service.create(tagDTO));
    }

    /**
     * Gets tag.
     *
     * @param id the id
     * @return the tag
     */
    @GetMapping(value = "/{id}")
    @PreAuthorize("hasAuthority('user') or hasAuthority('admin')")
    public TagDTO findById(@PathVariable @Min(MIN_ID_VALUE) Long id) {
        return ResponseAssembler.assembleTag(service.findById(id));
    }

    /**
     * Delete tag.
     *
     * @param id the id
     */
    @DeleteMapping(value = "{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAuthority('admin')")
    public void delete(@PathVariable @Min(MIN_ID_VALUE) Long id) {
        service.delete(id);
    }

    /**
     * Find popular tag dto.
     *
     * @return the tag dto
     */
    @GetMapping(value = "/popular")
    @PreAuthorize("hasAuthority('user') or hasAuthority('admin')")
    public TagDTO findPopular() {
        return ResponseAssembler.assembleTag(service.findPopular());
    }
}
