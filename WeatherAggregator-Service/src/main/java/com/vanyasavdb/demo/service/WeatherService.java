package com.vanyasavdb.demo.service;


import com.vanyasavdb.demo.domain.WeatherClass;
import com.vanyasavdb.demo.repository.WeatherRepository;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class WeatherService {

    private WeatherRepository weatherRepository;

    public WeatherService(WeatherRepository weatherRepository) {
        this.weatherRepository = weatherRepository;
    }

    public Iterable<WeatherClass> list() {
        return weatherRepository.findAll();
    }

    public Iterable<WeatherClass> listCities() {
        return weatherRepository.findCities();
    }

    public void save(List<WeatherClass> weatherClasses) {
        weatherRepository.saveAll(weatherClasses);
    }
}
