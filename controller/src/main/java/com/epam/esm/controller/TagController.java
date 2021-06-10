package com.epam.esm.controller;

import com.epam.esm.dto.TagDTO;
import com.epam.esm.service.TagService;
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
    public String createTag(@RequestParam("name") String name) {
        return name;
    }

    @GetMapping(value = "/{id}")
    public String showTag(@PathVariable long id) {
        return "id :" + id;
    }

    @DeleteMapping(value = "{id}")
    public String deleteTag(@PathVariable long id) {
        return "deleted " + id;
    }
}
