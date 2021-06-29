package com.epam.esm.controller;

import com.epam.esm.dto.CertificateDTO;
import com.epam.esm.dto.PatchDTO;
import com.epam.esm.dto.QuerySpecificationDTO;
import com.epam.esm.exception.IdInvalidException;
import com.epam.esm.exception.InvalidDataFormException;
import com.epam.esm.service.CertificateService;
import com.epam.esm.util.ResponseAssembler;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * The type Certificate controller.
 */
@RestController
@RequestMapping(value = "/certificates")
@AllArgsConstructor
@Slf4j
public class CertificateController {

    private final CertificateService service;

    /**
     * finds all certificates by criteria
     *
     * @param querySpecificationDTO the query specification dto
     * @return List of all certificates
     */
    @GetMapping
    public List<CertificateDTO> findAllByCriteria(QuerySpecificationDTO querySpecificationDTO) {
        return ResponseAssembler.assembleCertificates(service.findAll(querySpecificationDTO));
    }

    /**
     * finds certificate by certificate id
     *
     * @param id the id of certificate
     * @return the certificate
     */
    @GetMapping(value = "/{id}")
    public CertificateDTO findById(@PathVariable Long id) {
        if (id < 0) {
            throw new IdInvalidException(id);
        }
        return ResponseAssembler.assembleCertificate(service.findById(id));
    }

    /**
     * Create certificate
     *
     * @param certificateDTO the certificate dto
     * @param bindingResult  the binding result
     * @return the certificate dto
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CertificateDTO create(@Valid @RequestBody CertificateDTO certificateDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new InvalidDataFormException(bindingResult);
        }
        return ResponseAssembler.assembleCertificate(service.create(certificateDTO));
    }

    /**
     * Delete certificate.
     *
     * @param id the id
     */
    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
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
    public CertificateDTO patch(@PathVariable Long id, @Valid @RequestBody PatchDTO patchDTO, BindingResult bindingResult) {
        if (id < 0) {
            throw new IdInvalidException(id);
        }
        if (bindingResult.hasErrors()) {
            throw new InvalidDataFormException(bindingResult);
        }
        return ResponseAssembler.assembleCertificate(service.applyPatch(id, patchDTO));
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
    public CertificateDTO update(@PathVariable Long id, @Valid @RequestBody CertificateDTO certificateDTO,
                                 BindingResult bindingResult) {
        if (id < 0) {
            throw new IdInvalidException(id);
        }
        if (bindingResult.hasErrors()) {
            throw new InvalidDataFormException(bindingResult);
        }
        certificateDTO.setId(id);
        return ResponseAssembler.assembleCertificate(service.update(certificateDTO));
    }
}
