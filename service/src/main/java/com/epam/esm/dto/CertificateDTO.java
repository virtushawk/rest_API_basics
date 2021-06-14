package com.epam.esm.dto;

import javax.validation.constraints.*;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotBlank;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class CertificateDTO {

    private Long id;

    @Size(min = 1,max = 150)
    @Pattern(regexp = "(.|\\s)*\\S(.|\\s)*")
    private String name;

    @NotBlank
    @Pattern(regexp = "(.|\\s)*\\S(.|\\s)*")
    private String description;

    @DecimalMin("0.1")
    @DecimalMax("1000.0")
    private BigDecimal price;

    @Min(1)
    @Max(100)
    private int duration;

    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ")
    private ZonedDateTime createDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ")
    private ZonedDateTime lastUpdateDate;

    private Set<TagDTO> tags;
}
