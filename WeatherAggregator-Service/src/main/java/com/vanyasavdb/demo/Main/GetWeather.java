package com.vanyasavdb.demo.Main;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vanyasavdb.demo.domain.Weather;
import com.vanyasavdb.demo.domain.WeatherClass;
import com.vanyasavdb.demo.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.*;

@Component
public class GetWeather {

    //Массив для хранения списка городов
    private ArrayList<String> cities = new ArrayList<>();

    //Получаем список городов из файла src/main/resources/citiesList/config.properties
    public void getCities(){
        FileInputStream fis;
        Properties property = new Properties();
        try {
            fis = new FileInputStream("C:\\Users\\savilov\\Documents\\WeatherAggregator\\WeatherAggregator-Service\\src\\main\\resources\\citiesList\\config.properties");
            property.load(fis);
            String citiesString = property.getProperty("cities.list");
            String[] citiesList;
            citiesList = citiesString.split(",");
            for (String s :citiesList){
                cities.add(s);
            }
        } catch (IOException e) {
            System.err.println("ОШИБКА: Файл свойств отсуствует!");
        }
    }

    private WeatherService weatherService;

    @Autowired
    private GetWeather(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    //Каждые 30 минут вызываем saveWeather для каждого города из спика
    //@Scheduled(fixedRate = 10000)
    @Scheduled(fixedRate = 1800000)
    private void saveWeatherForAllCities(){
        for(String city:cities){
            saveWeather(city);
        }
    }

    //Процедура сохранения данных о погоде в БД
    private void saveWeather(String city){
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false);
        TypeReference<List<WeatherClass>> typeReference = new TypeReference<List<WeatherClass>>(){};
        try {
            String test = String.format("http://api.openweathermap.org/data/2.5/weather?q=%s&APPID=b7e97b965f4ca98ff5ea6411b0d38df3&units=metric&lang=ru",city);
            URL jsonUrl = new URL(test);
            try {
                List<WeatherClass> weatherClasses = mapper.readValue(jsonUrl,typeReference);
                List <Weather> weather;
                for(WeatherClass w: weatherClasses) {
                    weather = w.getWeather();
                    w.setTime((LocalDateTime.ofEpochSecond(w.getDt(),0, ZoneOffset.UTC)).toString()+"Z");
                    for (Weather q: weather){
                        w.setDescription(q.getDescription());
                    }
                }
                weatherService.save(weatherClasses);
                String output = String.format("%s Weather Saved!",city);
                //Выводим сообщение об удачном сохранении данных
                System.out.println(output);
            } catch (IOException e){
                System.out.println("Unable to save weather: " + e.getMessage());
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }
}