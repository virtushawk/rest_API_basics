package com.epam.esm.dto;

import com.epam.esm.entity.Tag;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CertificateDTO {
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private int duration;
    private ZonedDateTime createDate;
    private ZonedDateTime lastUpdateDate;
    Set<TagDTO> tags;
}
