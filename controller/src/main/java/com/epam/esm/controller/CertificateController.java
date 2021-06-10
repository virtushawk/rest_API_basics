package com.epam.esm.controller;

import com.epam.esm.dto.CertificateDTO;
import com.epam.esm.exception.CertificateNotFoundException;
import com.epam.esm.service.CertificateService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import java.util.List;
import java.util.Map;

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
    public ResponseEntity<?> deleteCertificate(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.ok("resources deleted");
    }

    @PatchMapping(value = "/{id}")
    public CertificateDTO updateCertificate(@PathVariable Long id, @RequestBody Map<String, Object> patchValues) {
        return service.applyPatch(patchValues, id);
    }
}
