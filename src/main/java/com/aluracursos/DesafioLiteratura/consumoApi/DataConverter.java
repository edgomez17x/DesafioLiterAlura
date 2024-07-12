package com.aluracursos.DesafioLiteratura.consumoApi;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class DataConverter{

    public <T> T getData(String json, Class<T> clase) {
        try {
            return new ObjectMapper().readValue(json, clase);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
