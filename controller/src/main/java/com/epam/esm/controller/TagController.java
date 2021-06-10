package com.epam.esm.controller;

import com.epam.esm.dto.TagDTO;
import com.epam.esm.service.TagService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/tags")

public class TagController {

    private final TagService service;

    public TagController(TagService service) {
        this.service = service;
    }

    @GetMapping
    public List<TagDTO> getAllTags() {
        return service.findAll();
    }

    @PostMapping
    public TagDTO createTag(@Valid @RequestBody TagDTO tagDTO) {
        return service.create(tagDTO);
    }

    @GetMapping(value = "/{id}")
    public TagDTO getTag(@PathVariable Long id) {
        return service.findById(id);
    }

    @DeleteMapping(value = "{id}")
    public ResponseEntity<?> deleteTag(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.ok("resources deleted");
    }
}
