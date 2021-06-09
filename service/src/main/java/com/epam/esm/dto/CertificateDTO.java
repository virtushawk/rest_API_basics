package com.epam.esm.dto;

import jakarta.validation.constraints.*;
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

    @Size(min = 1,max = 150)
    private String name;

    @NotBlank
    private String description;

    @DecimalMin("0.1")
    @DecimalMax("1000.0")
    private BigDecimal price;

    @Min(1)
    @Max(100)
    private int duration;

    @NotNull
    private ZonedDateTime createDate;

    @NotNull
    private ZonedDateTime lastUpdateDate;

    @NotNull
    Set<TagDTO> tags;
}
