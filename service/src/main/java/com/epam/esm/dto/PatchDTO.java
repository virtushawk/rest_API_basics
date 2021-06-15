package com.epam.esm.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class PatchDTO {

    private Long id;

    @Size(min = 1, max = 150)
    @Pattern(regexp = "(.|\\s)*\\S(.|\\s)*")
    private String name;

    @Size(min = 1, max = 300)
    @Pattern(regexp = "(.|\\s)*\\S(.|\\s)*")
    private String description;

    @DecimalMin("0.1")
    @DecimalMax("1000.0")
    private BigDecimal price;

    @Min(1)
    @Max(100)
    private Integer duration;

    @Valid
    private Set<TagDTO> tags;
}
