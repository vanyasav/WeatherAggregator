package com.vanyasavdb.demo.Contoller;

import com.vanyasavdb.demo.domain.WeatherClass;
import com.vanyasavdb.demo.service.WeatherService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/weathers")
public class WeatherController {

    private WeatherService weatherService;

    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    //Ссыслка для получения всех данных
    @CrossOrigin
    @GetMapping("/list")
    public Iterable<WeatherClass> list() {
        return weatherService.list();
    }

    //Ссылка для получения списка городов
    @CrossOrigin
    @GetMapping("/listcustom")
    public Iterable<WeatherClass> listCities() {
        return weatherService.listCities();
    }

}