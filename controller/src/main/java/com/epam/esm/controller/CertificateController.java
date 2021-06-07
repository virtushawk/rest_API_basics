package com.epam.esm.controller;

import com.epam.esm.entity.Certificate;
import com.epam.esm.service.CertificateService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequestMapping(value = "/certificates")
@RestController
public class CertificateController {

    private final CertificateService service;

    public CertificateController(CertificateService service) {
        this.service = service;
    }

    @GetMapping
    public List<Certificate> showAllCertificates() {
        return null;
    }

    @GetMapping(value = "/{id}")
    public Certificate showCertificate(@PathVariable Long id) {
        Optional<Certificate> certificate = service.findById(id);
        return certificate.orElseGet(Certificate::new);
    }

    @PostMapping
    public String createCertificate(@ModelAttribute("certificate") Certificate certificate) {
        return "id : " + service.create(certificate).getId();
    }

    @DeleteMapping(value = "/{id}")
    public String deleteCertificate(@PathVariable long id) {
        return id + " deleted";
    }

    @PutMapping(value = "/{id}")
    public String updateCertificate(@PathVariable long id) {
        return id + " updated";
    }
}
