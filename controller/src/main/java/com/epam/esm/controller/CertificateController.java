package com.epam.esm.controller;

import com.epam.esm.dto.CertificateDTO;
import com.epam.esm.exception.CertificateNotFoundException;
import com.epam.esm.service.CertificateService;
import jakarta.validation.Validator;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RequestMapping(value = "/certificates")
@RestController
public class CertificateController {

    private final CertificateService service;

    public CertificateController(CertificateService service) {
        this.service = service;
    }

    @GetMapping
    public List<CertificateDTO> showAllCertificates() {
        return service.findAll();
    }

    @GetMapping(value = "/{id}")
    public CertificateDTO getCertificate(@PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping
    public CertificateDTO createCertificate(@Valid @RequestBody CertificateDTO certificateDTO, BindingResult result) {
        if (result.hasErrors()) {
            throw new CertificateNotFoundException("ID : " + certificateDTO.getId());
        }
        return service.create(certificateDTO);
    }

    @DeleteMapping(value = "/{id}")
    public String deleteCertificate(@PathVariable long id) {
        return id + " deleted";
    }

    @PatchMapping(value = "/{id}")
    public String updateCertificate(@PathVariable long id) {
        return id + " updated";
    }
}
