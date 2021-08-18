package com.epam.esm.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApplicationController {

    @GetMapping(path = "/")
    public String getHelloMessage() {
        return "hello!";
    }
}
