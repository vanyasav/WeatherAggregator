package com.vanyasavdb.demo.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Data
@AllArgsConstructor
@Embeddable
public class Clouds {
    @Column(name="Cloudiness")
    private int all;

    public Clouds() {
    }
}
