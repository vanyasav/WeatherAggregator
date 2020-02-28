package com.vanyasavdb.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import javax.persistence.Embeddable;
import javax.persistence.Transient;

@Data
@AllArgsConstructor
@Embeddable
public class Wind {
    private float speed;

    @Transient
    @JsonIgnore
    private float deg;
    @Transient
    @JsonIgnore
    private float gust;
    public Wind() {
    }
}
