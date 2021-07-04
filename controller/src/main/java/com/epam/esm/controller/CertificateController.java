package com.epam.esm.controller;

import com.epam.esm.dto.CertificateDTO;
import com.epam.esm.dto.PatchDTO;
import com.epam.esm.dto.QuerySpecificationDTO;
import com.epam.esm.dto.TagDTO;
import com.epam.esm.entity.Page;
import com.epam.esm.exception.IdInvalidException;
import com.epam.esm.exception.InvalidDataFormException;
import com.epam.esm.service.CertificateService;
import com.epam.esm.service.TagService;
import com.epam.esm.util.ResponseAssembler;
import lombok.AllArgsConstructor;
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
public class CertificateController {

    private final CertificateService certificateService;
    private final TagService tagService;

    /**
     * Find all by criteria list.
     *
     * @param querySpecificationDTO the query specification dto
     * @param page                  the page
     * @return the list
     */
    @GetMapping
    public List<CertificateDTO> findAllByCriteria(QuerySpecificationDTO querySpecificationDTO, Page page) {
        return ResponseAssembler.assembleCertificates(certificateService.findAll(querySpecificationDTO, page));
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
        return ResponseAssembler.assembleCertificate(certificateService.findById(id));
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
        return ResponseAssembler.assembleCertificate(certificateService.create(certificateDTO));
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
        certificateService.delete(id);
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
        certificateService.applyPatch(id, patchDTO);
        return ResponseAssembler.assembleCertificate(certificateService.findById(id));
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
        return ResponseAssembler.assembleCertificate(certificateService.update(certificateDTO));
    }

    /**
     * Find tags by certificate id.
     *
     * @param id the id
     * @return the list
     */
    @GetMapping(value = "/{id}/tags")
    public List<TagDTO> findTagsByCertificateId(@PathVariable Long id) {
        if (id < 0) {
            throw new IdInvalidException(id);
        }
        return ResponseAssembler.assembleTags(tagService.findAllByCertificateId(id));
    }
}
