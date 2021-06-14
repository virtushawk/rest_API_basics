package com.epam.esm.util;

import com.epam.esm.dto.CertificateDTO;
import com.epam.esm.dto.QueryDTO;
import com.epam.esm.dto.TagDTO;
import com.epam.esm.entity.Certificate;
import com.epam.esm.entity.Query;
import com.epam.esm.entity.Tag;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;


@Component
@AllArgsConstructor
public class MapperDTO {

    private final ModelMapper modelMapper;

    public CertificateDTO convertCertificateToDTO(Certificate certificate) {
        return modelMapper.map(certificate, CertificateDTO.class);
    }

    public Certificate convertDTOToCertificate(CertificateDTO certificateDTO) {
        return modelMapper.map(certificateDTO, Certificate.class);
    }

    public TagDTO convertTagToDTO(Tag tag) {
        return modelMapper.map(tag, TagDTO.class);
    }

    public Tag convertDTOToTag(TagDTO tagDTO) {
        return modelMapper.map(tagDTO, Tag.class);
    }

    public Query convertDTOToQuery(QueryDTO queryDTO) {
        return modelMapper.map(queryDTO,Query.class);
    }
}
