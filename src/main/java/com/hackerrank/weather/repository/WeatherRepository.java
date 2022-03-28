package com.hackerrank.weather.repository;

import com.hackerrank.weather.model.Weather;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WeatherRepository extends JpaRepository<Weather, Integer> {
    @Query(value = "select * from weather s where s.city ilike %:city% ", nativeQuery = true)
    List<Weather> getAllWeathersByCity(String city);
    @Query(value = "select * from weather s where s.date ilike %:date% ", nativeQuery = true)
    List<Weather>  getAllWeathersByDate(String date);


}
