package com.vanyasavdb.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Transient;
import java.text.SimpleDateFormat;
import java.util.Date;

@Data
@AllArgsConstructor
@Embeddable
public class Sys {

    @Transient
    @JsonIgnore
    private int type;

    @Transient
    @JsonIgnore
     //@Column(name = "sys_id")
     private int id;

     private String country;
    @Transient
    @JsonIgnore
     private String sunrise;
    @Transient
    @JsonIgnore
     private String  sunset;

    public Sys(String country) {
        this.country = country;
    }

    public Sys() {
     }
}
