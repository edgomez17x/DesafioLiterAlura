package com.aluracursos.DesafioLiteratura.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record AuthorRec(
        String name,
        int birth_year,
        int death_year
) {

}
