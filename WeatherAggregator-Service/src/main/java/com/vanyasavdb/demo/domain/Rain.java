package com.vanyasavdb.demo.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Data
@AllArgsConstructor
@Embeddable
public class Rain {

    @JsonProperty("1h")
    private String h1;

    @JsonProperty("3h")
    private String h3;

    public Rain() {
    }
}
