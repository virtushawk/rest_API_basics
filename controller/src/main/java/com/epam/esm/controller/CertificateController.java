package com.epam.esm.controller;

import com.epam.esm.dto.CertificateDTO;
import com.epam.esm.exception.CertificateNotFoundException;
import com.epam.esm.exception.InvalidDataFormException;
import com.epam.esm.service.CertificateService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;
import java.util.Map;

@RequestMapping(value = "/certificates")
@RestController
@AllArgsConstructor
public class CertificateController {

    private final CertificateService service;

    @GetMapping
    public List<CertificateDTO> getAllCertificates(@RequestParam Map<String, String> parameters) {
        return service.findAll(parameters);
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
    public ResponseEntity<?> deleteCertificate(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.ok("resources deleted");
    }

    @PatchMapping(value = "/{id}")
    public CertificateDTO patchCertificate(@PathVariable Long id, @RequestBody Map<String, Object> patchValues) {
        return service.applyPatch(patchValues, id);
    }

    @PutMapping(value = "/{id}")
    public CertificateDTO updateCertificate(@PathVariable Long id,@Valid @RequestBody CertificateDTO certificateDTO,
                                            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new InvalidDataFormException("Invalid data in request");
        }
        certificateDTO.setId(id);
        return service.update(certificateDTO);
    }
}
