package com.aluracursos.DesafioLiteratura.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record BookRec(
        String title,
        List<AuthorRec> authors,
        List<String> languages,
        Long download_count
) {
}
