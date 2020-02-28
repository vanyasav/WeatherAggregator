package com.vanyasavdb.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.Embeddable;
import javax.persistence.Transient;

@Data
@AllArgsConstructor
@Embeddable
public class Main {
    private double temp;
    private double feels_like;

    @JsonIgnore
    @Transient
    private double temp_min;
    @JsonIgnore
    @Transient
    private double temp_max;
    private int pressure;
    private int humidity;


    public void setPressure(int pressure) {
        double data = Math.round(pressure*0.75)*100/100D;
        this.pressure = (int)data;
    }

    public Main() {
    }
}
