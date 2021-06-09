package com.epam.esm.util;

import com.epam.esm.dto.CertificateDTO;
import com.epam.esm.dto.TagDTO;
import com.epam.esm.entity.Certificate;
import com.epam.esm.entity.Tag;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class MapperDTO {

    private final ModelMapper modelMapper;

    public MapperDTO(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public CertificateDTO convertCertificateToDTO(Certificate certificate) {
        return modelMapper.map(certificate,CertificateDTO.class);
    }

    public Certificate convertDTOToCertificate(CertificateDTO certificateDTO) {
        return modelMapper.map(certificateDTO,Certificate.class);
    }

    public TagDTO convertTagToDTO(Tag tag) {
        return modelMapper.map(tag,TagDTO.class);
    }

    public Tag convertDTOToTag(TagDTO tagDTO) {
        return modelMapper.map(tagDTO,Tag.class);
    }
}
