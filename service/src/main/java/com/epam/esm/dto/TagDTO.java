package com.epam.esm.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TagDTO extends RepresentationModel<TagDTO> {
    private long id;

    @Size(min = 1, max = 150)
    @NotBlank
    private String name;
}
