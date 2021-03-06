package com.vanyasavdb.demo.Main;

import com.vanyasavdb.demo.domain.WeatherEntity;
import com.vanyasavdb.demo.service.WeatherService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

@Component
public class GetWeather {

    private WeatherService weatherService;

    private File getFileFromResources(String fileName) {

        ClassLoader classLoader = getClass().getClassLoader();

        URL resource = classLoader.getResource(fileName);
        if (resource == null) {
            throw new IllegalArgumentException("file is not found!");
        } else {
            return new File(resource.getFile());
        }

    }

    /**
    //Массив для хранения списка городов
    private ArrayList<String> cities = new ArrayList<>();

    //Получаем список городов из файла src/main/resources/citiesList/config.properties
    public void getCities(){
        FileInputStream fis;
        Properties property = new Properties();
        try {
            String localDir = System.getProperty("user.dir");
            fis = new FileInputStream(localDir+ "\\WeatherAggregator-Service\\src\\main\\resources\\citiesList\\config.properties");
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
     **/
    private int i = 0;
    private ArrayList<String> lowClouds = new ArrayList<>();
    private ArrayList<String> middleClouds = new ArrayList<>();
    private ArrayList<String> topClouds = new ArrayList<>();
    /**
    private void parseClouds(Elements elements) {
        for (Element element : elements) {
     boolean flag = false;
            //Выделяем информацию об облаках
            String elem = element.attr("onmouseover");
     //System.out.println(elem);


            int j = elem.indexOf("облак");
            String clouds1 = elem.substring(j);
            int k = clouds1.indexOf('\'');
            String cloud2 = elem.substring(j, j + k - 1);
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
     }
     else{
                lowCloud = "0";
                cloud3 = cloudFinal;
            }




     /**
     if (!elem.contains("облаков нет")){
     int j = elem.indexOf("облак");
     String clouds1 = elem.substring(j);
     int k = clouds1.indexOf('\'');
     String cloud2 = elem.substring(j, j + k - 1);
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
     if (cloudFinal.length()> k + 3) {
     cloud3 = cloudFinal.substring(k + 3);
     //Ищем облака среднего яруса
     String middleCloud;
     String cloud4;
     if (cloud3.contains("среднего яруса")) {
     j = cloud3.indexOf("са ");
     k = cloud3.indexOf("%");
     middleCloud = cloud3.substring(j + 3, k);
     cloud4 = cloud3.substring(k + 3);
     } else {
     middleCloud = "0";
     cloud4 = cloud3;
     }
     String topCloud;
     if (cloud4.contains("верхнего яруса")) {
     j = cloud4.indexOf("са ");
     k = cloud4.indexOf("%");
     topCloud = cloud4.substring(j + 3, k);
     } else {
     topCloud = "0";
     }
     System.out.println("НИЖНИЙ ЯРУС=" + lowCloud + ", СРЕДНИЙ ЯРУС=" + middleCloud + ", ВЕРХНИЙ ЯРУС=" + topCloud);
     System.out.println("ДОБАВИЛИ ОБЛАКА-" + i++);
     lowClouds.add(lowCloud);
     middleClouds.add(middleCloud);
     topClouds.add(topCloud);
     }
     else {
     System.out.println("НИЖНИЙ ЯРУС=" + lowCloud);
     System.out.println("ДОБАВИЛИ ОБЛАКА-" + i++);
     lowClouds.add(lowCloud);
     middleClouds.add("0");
     topClouds.add("0");
     }
     } else {
     lowCloud = "0";
     cloud3 = cloudFinal;
     }
     }
     else{
     System.out.println("ОБЛАКОВ НЕТ");
     System.out.println("ДОБАВИЛИ ОБЛАКА-" + i++);
     lowClouds.add("0");
     middleClouds.add("0");
     topClouds.add("0");
            }

        }
    }
     **/

    private ArrayList<String> temperatures = new ArrayList<>();
    private ArrayList<String> directions = new ArrayList<>();
    private ArrayList<String> speeds = new ArrayList<>();
    private ArrayList<String> localTimes = new ArrayList<>();

    private void parseClouds(Elements elements) {
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
                if (cloudFinal.length() > k + 3) {
                    cloud3 = cloudFinal.substring(k + 3);
                } else
                    cloud3 = "";
                //System.out.println("cloud3: " + cloud3);
            } else {
                lowCloud = "0";
                cloud3 = cloudFinal;
                //System.out.println("cloud3: " + cloud3);
            }

            //Ищем облака среднего яруса
            String middleCloud = "";
            String cloud4;
            if (cloud3.contains("среднего яруса")) {
                j = cloud3.indexOf("са ");
                k = cloud3.indexOf("%");


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
    }

    private void parseTemperature(Elements elements) {
        for (Element element : elements) {
            //System.out.println(element);
            String temperature = element.ownText();
            //System.out.println(temperature);
            temperatures.add(temperature);
        }
    }

    private void parseDirection(Elements elements) {
        for (Element element : elements) {
            //System.out.println(element);
            String direction = element.ownText();
            directions.add(direction);
            //System.out.println(direction);
        }
    }

    private void parseSpeedNoGust(Elements elements) {
        int i = 0;
        for (Element element : elements) {
            if (i == 24) break;
            //System.out.println(element);
            String speed = element.ownText();
            //System.out.println(speed);
            speeds.add(speed);
            i++;
        }
    }

    private void parseTime(Elements elements) {
        int i = 0;
        for (Element element : elements) {
            if (i == 24) break;
            //System.out.println(element);
            String time = element.ownText();
            localTimes.add(time);
            //System.out.println(time);
            i++;
        }
    }

    private void parseSpeed(Elements elements) {
        for (Element element : elements) {
            //Выделяем информацию об облаках
            String elem = element.attr("onmouseover");
            //System.out.println(elem);
            if (elem == "")
                break;
            int j = elem.indexOf('\'');
            int k = elem.indexOf(')');
            String data = elem.substring(j + 1, k);
            j = data.indexOf('(');
            k = data.indexOf("м/с");
            String speed = data.substring(j + 1, k - 1);
            String gust = "";
            if (elem.contains("порывы")) {
                j = elem.indexOf("порывы");
                data = elem.substring(j);
                j = elem.indexOf('\'');
                gust = data.substring(7, j - 5);
                //System.out.println("СКОРОСТЬ ВЕТРА: " + speed + " м/с" + ", ПОРЫВЫ ВЕТРА: " + gust + " м/с");
            }
            //if (gust == "")
            //System.out.println("СКОРОСТЬ ВЕТРА: " + speed + " м/с");
        }
    }

    public void testFunction() {
        try {

            File input = getFileFromResources("test.html");
            Document doc = Jsoup.parse(input, "UTF-8");

            /**
             Document doc = Jsoup.connect("https://rp5.ru/%D0%9F%D0%BE%D0%B3%D0%BE%D0%B4%D0%B0_%D0%B2_%D0%91%D0%B8%D1%87%D1%83%D1%80%D0%B5,_%D0%91%D1%83%D1%80%D1%8F%D1%82%D0%B8%D1%8F")
             .userAgent("Chrome/4.0.249.0 Safari/532.5")
             .referrer("http://www.google.com")
             .get();
             **/
            parseClouds(doc.select("#forecastTable_1 div.cc_0 div"));
            //parseSpeed(doc.select("#forecastTable_1 div.wv_0"));
            parseTemperature(doc.select("#forecastTable_1 div.t_0 b"));
            parseDirection(doc.select("#forecastTable_1").select("td.grayLittlen, td.grayLittlen2,td.grayLittled2"));
            parseSpeedNoGust(doc.select("#forecastTable_1").select("div.wv_0"));
            parseTime(doc.select("#forecastTable_1").select("td.n, td.n2, td.d2"));
            for (int i = 0; i < 24; i++) {
                WeatherEntity weatherEntity = new WeatherEntity();
                weatherEntity.setLocalTime(Integer.valueOf(localTimes.get(i)));
                weatherEntity.setWindSpeed(Integer.valueOf(speeds.get(i)));
                weatherEntity.setTemperature(Integer.valueOf(temperatures.get(i)));
                weatherEntity.setDirection(directions.get(i));
                //weatherEntity.setLowCloud(Integer.valueOf(lowClouds.get(i)));
                //weatherEntity.setMiddleCloud(Integer.valueOf(middleClouds.get(i)));
                //weatherEntity.setTopCloud(Integer.valueOf(topClouds.get(i)));

                weatherService.save(weatherEntity);
            }
        } catch (IOException e) {
            System.out.println("Unable to get data");
        }

    }

    @Autowired
    private GetWeather(WeatherService weatherService) {
        this.weatherService = weatherService;
    }


/**
 //Каждые 30 минут вызываем saveWeather для каждого города из списка
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
 **/
}