package com.epam.esm.controller;

import com.epam.esm.dto.CertificateDTO;
import com.epam.esm.dto.PatchDTO;
import com.epam.esm.exception.InvalidDataFormException;
import com.epam.esm.service.CertificateService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


import javax.validation.Valid;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RequestMapping(value = "/certificates")
@RestController
@AllArgsConstructor
public class CertificateController {

    private final CertificateService service;

    @GetMapping
    public List<CertificateDTO> getAllCertificates() {
        return service.findAll();
    }

    @GetMapping(value = "/{id}")
    public CertificateDTO getCertificate(@PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping
    public CertificateDTO createCertificate(@Valid @RequestBody CertificateDTO certificateDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new InvalidDataFormException("Invalid data in request");
        }
        return service.create(certificateDTO);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCertificate(@PathVariable Long id) {
        service.delete(id);
    }

    @PatchMapping(value = "/{id}")
    public CertificateDTO patchCertificate(@PathVariable Long id, @Valid @RequestBody PatchDTO patchDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new InvalidDataFormException("Invalid data in request");
        }
        patchDTO.setId(id);
        return service.applyPatch(patchDTO);
    }

    @PutMapping(value = "/{id}")
    public CertificateDTO updateCertificate(@PathVariable Long id, @Valid @RequestBody CertificateDTO certificateDTO,
                                            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new InvalidDataFormException("Invalid data in request");
        }
        certificateDTO.setId(id);
        return service.update(certificateDTO);
    }
}
