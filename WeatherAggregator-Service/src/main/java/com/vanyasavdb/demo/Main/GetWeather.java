package com.vanyasavdb.demo.Main;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vanyasavdb.demo.domain.Weather;
import com.vanyasavdb.demo.domain.WeatherClass;
import com.vanyasavdb.demo.service.WeatherService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

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

    public void testFunction() {
        try {

            File input = new File("C:\\Users\\savilov\\Documents\\WeatherAggregator\\WeatherAggregator-Service\\src\\main\\resources\\test.html");
            Document doc = Jsoup.parse(input, "UTF-8");

            /**
             Document doc = Jsoup.connect("https://rp5.ru/%D0%9F%D0%BE%D0%B3%D0%BE%D0%B4%D0%B0_%D0%B2_%D0%91%D0%B8%D1%87%D1%83%D1%80%D0%B5,_%D0%91%D1%83%D1%80%D1%8F%D1%82%D0%B8%D1%8F")
             .userAgent("Chrome/4.0.249.0 Safari/532.5")
             .referrer("http://www.google.com")
             .get();
             **/
            Elements elements = doc.select("#forecastTable_1 div.cc_0 div");
            for (Element element : elements) {
                //Выделяем информацию об облаках
                String elem = element.attr("onmouseover");
                int j = elem.indexOf("облак");
                String clouds1 = elem.substring(j);
                int k = clouds1.indexOf('\'');
                String cloud2 = elem.substring(j, j + k - 1);

                System.out.println(cloud2);

                //Ищем облака вертикального развития
                String cloudFinal;
                if (cloud2.contains("облака вертикального развития")) {
                    j = cloud2.indexOf(',');
                    cloudFinal = cloud2.substring(j + 2);
                } else {
                    cloudFinal = cloud2;
                }

                //Ищем облака нижнего яруса
                String lowCloud;
                String cloud3;
                if (cloudFinal.contains("нижнего яруса")) {
                    j = cloudFinal.indexOf("са ");
                    k = cloudFinal.indexOf("%");
                    lowCloud = cloudFinal.substring(j + 3, k);
                    cloud3 = cloudFinal.substring(k + 3);
                    //System.out.println("cloud3: " + cloud3);
                } else {
                    lowCloud = "0";
                    cloud3 = cloudFinal;
                    //System.out.println("cloud3: " + cloud3);
                }

                //Ищем облака среднего яруса
                String middleCloud;
                String cloud4;
                if (cloud3.contains("среднего яруса")) {
                    j = cloud3.indexOf("са ");
                    k = cloud3.indexOf("%");
                    middleCloud = cloud3.substring(j + 3, k);
                    cloud4 = cloud3.substring(k + 3);
                    //System.out.println("cloud4: " + cloud4);
                    //System.out.println(middleCloud);
                } else {
                    middleCloud = "0";
                    cloud4 = cloud3;
                    //System.out.println("cloud4: " + cloud4);
                }

                String topCloud;
                if (cloud4.contains("верхнего яруса")) {
                    j = cloud4.indexOf("са ");
                    k = cloud4.indexOf("%");
                    topCloud = cloud4.substring(j + 3, k);
                    //System.out.println(topCloud);
                } else {
                    topCloud = "0";
                    //System.out.println("cloud4: " + cloud4);
                }
                System.out.println("НИЖНИЙ ЯРУС=" + lowCloud + ", СРЕДНИЙ ЯРУС=" + middleCloud + ", ВЕРХНИЙ ЯРУС=" + topCloud);
            }
        } catch (IOException e) {
            System.out.println("Unable to get data");
        }

    }



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