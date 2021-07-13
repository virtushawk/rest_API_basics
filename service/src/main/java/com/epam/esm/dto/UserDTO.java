package com.epam.esm.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@EqualsAndHashCode(callSuper = false)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO extends RepresentationModel<UserDTO> {
    private Long id;

    @Size(min = 1, max = 150)
    @NotBlank
    private String name;
}
