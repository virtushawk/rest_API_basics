package com.epam.esm.util;

import com.epam.esm.dto.CertificateDTO;
import com.epam.esm.dto.QuerySpecificationDTO;
import com.epam.esm.dto.TagDTO;
import com.epam.esm.entity.Certificate;
import com.epam.esm.entity.QuerySpecification;
import com.epam.esm.entity.Tag;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;


/**
 * The type Mapper dto.
 */
@Component
@AllArgsConstructor
public class MapperDTO {

    private final ModelMapper modelMapper;

    /**
     * Convert certificate to dto certificate dto.
     *
     * @param certificate the certificate
     * @return the certificate dto
     */
    public CertificateDTO convertCertificateToDTO(Certificate certificate) {
        return modelMapper.map(certificate, CertificateDTO.class);
    }

    /**
     * Convert dto to certificate certificate.
     *
     * @param certificateDTO the certificate dto
     * @return the certificate
     */
    public Certificate convertDTOToCertificate(CertificateDTO certificateDTO) {
        return modelMapper.map(certificateDTO, Certificate.class);
    }

    /**
     * Convert tag to dto tag dto.
     *
     * @param tag the tag
     * @return the tag dto
     */
    public TagDTO convertTagToDTO(Tag tag) {
        return modelMapper.map(tag, TagDTO.class);
    }

    /**
     * Convert dto to tag tag.
     *
     * @param tagDTO the tag dto
     * @return the tag
     */
    public Tag convertDTOToTag(TagDTO tagDTO) {
        return modelMapper.map(tagDTO, Tag.class);
    }

    /**
     * Convert dto to query query specification.
     *
     * @param querySpecificationDTO the query specification dto
     * @return the query specification
     */
    public QuerySpecification convertDTOToQuery(QuerySpecificationDTO querySpecificationDTO) {
        return modelMapper.map(querySpecificationDTO, QuerySpecification.class);
    }
}
