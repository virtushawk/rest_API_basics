package com.epam.esm.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QuerySpecificationDTO {
    private List<String> tags;

    private String text;

    private List<String> order;

    private List<String> sort;
}
