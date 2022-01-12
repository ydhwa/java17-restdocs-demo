package com.example.java17restdocsdemo.response;

import lombok.Getter;

@Getter
public class IdResponse {

    private final Object id;


    public IdResponse(Object id) {
        this.id = id;
    }
}
