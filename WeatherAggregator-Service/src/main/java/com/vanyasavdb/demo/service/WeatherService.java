package com.vanyasavdb.demo.service;


import com.vanyasavdb.demo.domain.WeatherEntity;
import com.vanyasavdb.demo.repository.WeatherRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WeatherService {

    private WeatherRepository weatherRepository;

    public WeatherService(WeatherRepository weatherRepository) {
        this.weatherRepository = weatherRepository;
    }

    public Iterable<WeatherEntity> list() {
        return weatherRepository.findAll();
    }

    public void saveList(List<WeatherEntity> weatherEntities) {
        weatherRepository.saveAll(weatherEntities);
    }

    public void save(WeatherEntity weatherEntity) {
        weatherRepository.save(weatherEntity);
    }

}


