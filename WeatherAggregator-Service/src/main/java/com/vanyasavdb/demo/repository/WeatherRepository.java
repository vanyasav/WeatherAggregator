package com.vanyasavdb.demo.repository;

import com.vanyasavdb.demo.domain.WeatherEntity;
import org.springframework.data.repository.CrudRepository;

//public interface WeatherRepository extends CrudRepository<WeatherClass, Long> {
public interface WeatherRepository extends CrudRepository<WeatherEntity, Long> {

/**
    //Запрос для получения списка всех городов с координатами и страной.
    @Query("select new WeatherClass (user.coord, user.sys, user.name) from WeatherClass user group by user.name,user.coord,user.sys")
    List <WeatherClass> findCities();
 **/
}