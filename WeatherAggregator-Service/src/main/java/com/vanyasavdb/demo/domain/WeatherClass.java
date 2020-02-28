package com.vanyasavdb.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@Entity
@Table(schema = "json", name = "weatherTable")
public class WeatherClass {

    public WeatherClass(Coord coord, Sys sys, String name) {
        this.coord = coord;
        this.sys = sys;
        this.name = name;
    }

    @Id
    @GeneratedValue(strategy =GenerationType.AUTO)
    private Long unique_id;

    private Coord coord;

    @Embedded
    @Transient
    private List<Weather> weather;

    @Transient
    @JsonIgnore
    private String base;

    @Embedded
    private Main main;

    private int visibility;

    @Embedded
    private Wind wind;

    @Transient
    @JsonIgnore
    @Embedded
    private Rain rain;

    @Embedded
    private Clouds clouds;

    private int timezone;

    @Transient
    private int dt;

    @Embedded
    private Sys sys;

    @Transient
    @JsonIgnore
    //@Column(name="country_id")
    private long id;

    @Column(name="city")
    private String name;

    @Transient
    @JsonIgnore
    private String cod;

    private String description;

    private String time;

    public WeatherClass() {
    }
}
