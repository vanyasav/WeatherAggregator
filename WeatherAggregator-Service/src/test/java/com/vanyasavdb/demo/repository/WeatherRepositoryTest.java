package com.vanyasavdb.demo.repository;

import com.vanyasavdb.demo.domain.Coord;
import com.vanyasavdb.demo.domain.Main;
import com.vanyasavdb.demo.domain.WeatherClass;
import com.vanyasavdb.demo.domain.Wind;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@DataJpaTest
class WeatherRepositoryTest {


    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private WeatherRepository weatherRepository;

    /**
     @Test void saveTest() {
     WeatherClass weatherClass = getWeatherClass();
     WeatherClass savedInDB = entityManager.persist(weatherClass);
     entityManager.flush();
     Optional <WeatherClass> getFromDb = weatherRepository.findById((savedInDB.getId()));
     assertThat(((getFromDb.get()))).isEqualTo(savedInDB);

     }
     **/
    private WeatherClass getWeatherClass() {
        WeatherClass weatherClass = new WeatherClass();
        weatherClass.setDescription("ЯСНАЯ ПОГОДА");
        weatherClass.setName("Ракун Сити");
        weatherClass.setTime("25-01-2020");
        weatherClass.setBase("Base");
        weatherClass.setCod("Cod");
        weatherClass.setDt(1000);
        weatherClass.setId(1);
        weatherClass.setTimezone(10000);
        Coord coord = new Coord();
        coord.setLat(10);
        coord.setLon(10);
        weatherClass.setCoord(coord);
        Main main = new Main();
        main.setFeels_like(50);
        weatherClass.setMain(main);
        Wind wind = new Wind();
        wind.setSpeed(100);
        weatherClass.setWind(wind);
        return weatherClass;
    }
}