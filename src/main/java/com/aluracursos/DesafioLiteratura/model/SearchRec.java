package com.aluracursos.DesafioLiteratura.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record SearchRec(
        Long count,
        String next,
        String previous,
        List<BookRec> results
) {
}
