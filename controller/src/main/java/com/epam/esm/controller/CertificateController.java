package com.epam.esm.controller;

import com.epam.esm.model.Certificate;
import com.epam.esm.service.CertificateService;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RequestMapping(value = "/certificate")
@RestController
public class CertificateController {

    private final CertificateService service;

    public CertificateController(CertificateService service) {
        this.service = service;
    }

    @GetMapping(value = "/{id}")
    public String showCertificate(@PathVariable long id, Model model) {
        Certificate certificate = service.findCertificate(id);
        return "id : " +  certificate.getId();
    }

    @PostMapping(value = "/create")
    public String createCertificate(@ModelAttribute("certificate") Certificate certificate) {
        return "id : " + service.createCertificate(certificate).getId();
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
