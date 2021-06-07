package com.epam.esm.controller;

import com.epam.esm.model.Certificate;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RequestMapping(value = "/certificate")
@RestController
public class CertificateController {

    @GetMapping(value = "/{id}")
    public String showCertificate(@PathVariable long id, Model model) {
        model.addAttribute("name",id);
        model.addAttribute("cost","14.5");
        return model.toString();
    }

    @PostMapping(value = "/create")
    public String createCertificate(@ModelAttribute("certificate") Certificate certificate) {
        return certificate.toString();
    }

    @GetMapping(value = "/delete/{id}")
    public String deleteCertificate(@PathVariable long id,Model model) {
        return id + " deleted";
    }

    @PostMapping(value = "/update/{id}")
    public String updateCertificate(@PathVariable long id,Model model) {
        return id + " updated";
    }
}
