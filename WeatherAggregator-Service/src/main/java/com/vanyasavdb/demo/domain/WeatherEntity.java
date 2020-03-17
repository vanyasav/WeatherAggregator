package com.vanyasavdb.demo.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;

@Data
@AllArgsConstructor
@Entity
@Table(schema = "json", name = "AvelarTable")
public class WeatherEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    //Облачность нижнего яруса
    private Integer lowCloud;

    //Облачность среднего яруса
    private Integer middleCloud;

    //Облачность верхнего яруса
    private Integer topCloud;

    //Местное время
    private Integer localTime;

    //Скорость ветра
    private Integer windSpeed;

    //Порывы ветра
    //private Integer gust;

    //Направление ветра
    private String direction;

    //Температура
    private Integer temperature;

    public WeatherEntity() {
    }
}
