package com.epam.esm.controller;

import com.epam.esm.dto.TagDTO;
import com.epam.esm.exception.InvalidDataFormException;
import com.epam.esm.service.TagService;
import javax.validation.Valid;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/tags")
@AllArgsConstructor
public class TagController {

    private final TagService service;

    @GetMapping
    public List<TagDTO> getAllTags() {
        return service.findAll();
    }

    @PostMapping
    public TagDTO createTag(@Valid @RequestBody TagDTO tagDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new InvalidDataFormException("Invalid data in request");
        }
        return service.create(tagDTO);
    }

    @GetMapping(value = "/{id}")
    public TagDTO getTag(@PathVariable Long id) {
        return service.findById(id);
    }

    @DeleteMapping(value = "{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTag(@PathVariable Long id) {
        service.delete(id);
    }
}
