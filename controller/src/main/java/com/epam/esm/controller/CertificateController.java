package com.epam.esm.controller;

import com.epam.esm.dto.CertificateDTO;
import com.epam.esm.dto.PatchDTO;
import com.epam.esm.dto.QuerySpecificationDTO;
import com.epam.esm.exception.IdInvalidException;
import com.epam.esm.exception.InvalidDataFormException;
import com.epam.esm.service.CertificateService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * The type Certificate controller.
 */
@RequestMapping(value = "/certificates")
@RestController
@AllArgsConstructor
public class CertificateController {

    private final CertificateService service;

    /**
     * Gets all certificates.
     *
     * @param querySpecificationDTO the query specification dto
     * @return List of all certificates
     */
    @GetMapping
    public List<CertificateDTO> getAllCertificates(QuerySpecificationDTO querySpecificationDTO) {
        return service.findAll(querySpecificationDTO);
    }

    /**
     * Gets certificate by certificate id
     *
     * @param id the id of certificate
     * @return the certificate
     */
    @GetMapping(value = "/{id}")
    public CertificateDTO getCertificate(@PathVariable Long id) {
        if (id < 0) {
            throw new IdInvalidException(id);
        }
        return service.findById(id);
    }

    /**
     * Create certificate
     *
     * @param certificateDTO the certificate dto
     * @param bindingResult  the binding result
     * @return the certificate dto
     */
    @PostMapping
    public CertificateDTO createCertificate(@Valid @RequestBody CertificateDTO certificateDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new InvalidDataFormException(bindingResult);
        }
        return service.create(certificateDTO);
    }

    /**
     * Delete certificate.
     *
     * @param id the id
     */
    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCertificate(@PathVariable Long id) {
        if (id < 0) {
            throw new IdInvalidException(id);
        }
        service.delete(id);
    }

    /**
     * Patch certificate
     *
     * @param id            the id
     * @param patchDTO      the patch dto
     * @param bindingResult the binding result
     * @return the certificate dto
     */
    @PatchMapping(value = "/{id}")
    public CertificateDTO patchCertificate(@PathVariable Long id, @Valid @RequestBody PatchDTO patchDTO, BindingResult bindingResult) {
        if (id < 0) {
            throw new IdInvalidException(id);
        }
        if (bindingResult.hasErrors()) {
            throw new InvalidDataFormException(bindingResult);
        }
        patchDTO.setId(id);
        return service.applyPatch(patchDTO);
    }

    /**
     * Update certificate
     *
     * @param id             the id
     * @param certificateDTO the certificate dto
     * @param bindingResult  the binding result
     * @return the certificate dto
     */
    @PutMapping(value = "/{id}")
    public CertificateDTO updateCertificate(@PathVariable Long id, @Valid @RequestBody CertificateDTO certificateDTO,
                                            BindingResult bindingResult) {
        if (id < 0) {
            throw new IdInvalidException(id);
        }
        if (bindingResult.hasErrors()) {
            throw new InvalidDataFormException(bindingResult);
        }
        certificateDTO.setId(id);
        return service.update(certificateDTO);
    }
}
