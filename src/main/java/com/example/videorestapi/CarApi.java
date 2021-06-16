package com.example.videorestapi;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;



@RestController
@RequestMapping(value = "/cars", produces = {MediaType.APPLICATION_JSON_VALUE})
public class CarApi {

    private List<Car> carsList;

    public CarApi() {
        this.carsList = new ArrayList<>();
        carsList.add(new Car(1l, "Citroen", "picasso", "black"));
        carsList.add(new Car(2l, "Mercdes", "b200", "white"));
        carsList.add(new Car(3l, "ford", "focus", "blue"));
    }

    @GetMapping
    public ResponseEntity<List<Car>> getCars(){
        return new ResponseEntity<>(carsList, HttpStatus.OK);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<Car> getCarById(@PathVariable Long id){
        Optional<Car> first = carsList.stream().filter(car -> car.getId() == id).findFirst();

        if (first.isPresent()) {

            return new ResponseEntity<>(first.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/color/{color}")
    public ResponseEntity<Car> getCarByColor(@PathVariable String color){
        Optional<Car> first = carsList.stream().filter(car -> car.getColor() == color).findFirst();

        if (first.isPresent()){
            return new ResponseEntity<>(first.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    @PostMapping
    public ResponseEntity addCar(@RequestBody Car car){
        boolean add = carsList.add(car);

        if (add){
            return new ResponseEntity(HttpStatus.CREATED);
        }

        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PutMapping
    public ResponseEntity modCar(@RequestBody Car newCar) {
        Optional<Car> first = carsList.stream().filter(car -> car.getId() == newCar.getId()).findFirst();
        if (first.isPresent()) {
            carsList.remove(first.get());
            carsList.add(newCar);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/id/{id}")
    public ResponseEntity removeCar(@PathVariable Long id) {
        Optional<Car> first = carsList.stream().filter(car -> car.getId() == id).findFirst();

        if (first.isPresent()) {
            carsList.remove(first.get());
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
