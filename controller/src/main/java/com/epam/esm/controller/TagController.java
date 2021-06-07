package com.epam.esm.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/tag")
public class TagController {

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
