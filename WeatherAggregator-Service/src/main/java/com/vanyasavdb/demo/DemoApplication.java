package com.vanyasavdb.demo;

import com.vanyasavdb.demo.Main.GetWeather;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class DemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @Bean
    @Profile("!test")
    CommandLineRunner runner(GetWeather getWeather){
        return args -> {
            //Вызываем функцию для получения списка городов
            getWeather.getCities();
        };
    }
}

