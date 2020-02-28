package com.vanyasavdb.demo.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Data
@AllArgsConstructor
@Embeddable
@JsonFormat(shape= JsonFormat.Shape.OBJECT)
public class Weather {

    @Column( name = "weather_condition_id")
    private int id;
    private String main;
    private String description;
    private String icon;

    public Weather() {
    }
}
