package com.hackerrank.weather.controller;

import com.hackerrank.weather.model.Weather;
import com.hackerrank.weather.repository.WeatherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@RestController
public class WeatherApiRestController {
        @Autowired
        private WeatherRepository weatherRepository;

        @PostMapping("/weather")
        public ResponseEntity<Weather> postWeatherData(@RequestBody Weather weatherData) {
            Weather storedEntity = weatherRepository.save(weatherData);

            return new ResponseEntity<>(storedEntity, HttpStatus.CREATED);

        }
    @GetMapping("/weather/{id}")
    public ResponseEntity<Weather> findWeatherDataById(@PathVariable int id){

        Weather weather = weatherRepository.findById(id).orElse(null);
        if(weather==null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }else
            return ResponseEntity.ok(weather);
    }
    @GetMapping("/weather")
    public ResponseEntity<?> getAll (){
        ArrayList<Weather> list = new ArrayList<>( weatherRepository.findAll());
        list.sort(Comparator.comparingInt(Weather::getId));
        return ResponseEntity.ok(list);

    }


    @GetMapping(value="/weather", params = "city" )
    public ResponseEntity<?> getAllByCity (@RequestParam(required = false)String city){
        ArrayList<Weather> list = new ArrayList<>( weatherRepository.getAllWeathersByCity(city));
        list.sort(Comparator.comparingInt(Weather::getId));
        return ResponseEntity.ok(list);

    }

    @GetMapping(value="/weather", params = "date" )
    public ResponseEntity<?> getAllByDate (@RequestParam(required = false) String date){
        ArrayList<Weather> list = new ArrayList<>( weatherRepository.getAllWeathersByDate(date));
        list.sort(Comparator.comparingInt(Weather::getId));
        return ResponseEntity.ok(list);

    }

    @GetMapping(value="/weather", params = "sort" )
    public ResponseEntity<?> getAllByDateSorted (@RequestParam(required = false) String sort) {
        ArrayList<Weather> list = new ArrayList<>(weatherRepository.findAll());
        if (sort.equals("date")) {
            Comparator<Weather> dateComparison = (weather1, w2) -> (weather1.getDate().compareTo(w2.getDate()));
            list.sort(dateComparison);
        }
        return ResponseEntity.ok(list);
    }
}
