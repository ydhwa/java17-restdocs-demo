package com.example.java17restdocsdemo.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import org.springframework.hateoas.RepresentationModel;

import java.util.HashMap;
import java.util.Map;

@Getter
public class EmbeddedResponse<T> extends RepresentationModel<EmbeddedResponse<T>> {

    @JsonProperty(value = "_embedded")
    private Map<String, T> embedded;


    public EmbeddedResponse(String relation, T data) {
        embedded = new HashMap<>();
        embedded.put(relation, data);
    }
}
