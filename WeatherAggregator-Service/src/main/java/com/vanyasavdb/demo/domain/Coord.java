package com.vanyasavdb.demo.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.Embeddable;

@Data
@AllArgsConstructor
@Embeddable
public class Coord {

    private double lon;
    private double lat;

    public Coord(double lat) {
        this.lat = lat;
    }

    public Coord() {
    }
}
