package com.epam.esm.controller;

import com.epam.esm.service.TagService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/tag")
public class TagController {

    private final TagService service;

    public TagController(TagService service) {
        this.service = service;
    }

    @PostMapping(value = "/create")
    public String createTag(@RequestParam("name") String name) {
        return name;
    }

    @GetMapping(value = "/{id}")
    public String showTag(@PathVariable long id) {
        return "id :" + id;
    }

    @GetMapping(value = "/delete/{id}")
    public String deleteTag(@PathVariable long id) {
        return "deleted " + id;
    }
}
