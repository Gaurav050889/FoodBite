package com.example.demo.controller;


import com.example.demo.dto.RestaurantResponse;
import com.example.demo.entity.Restaurant;
import com.example.demo.service.RestaurantListService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/restaurants")
public class RestaurantController {
    public RestaurantListService restaurantListService;

    public RestaurantController(RestaurantListService restaurantListService){
        this.restaurantListService=restaurantListService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Restaurant> getRestaurantById(@PathVariable Long id){
        Optional<Restaurant> restaurant = restaurantListService.findRestaurantWithId(id);
        if(restaurant.isPresent()){
            return ResponseEntity.ok(restaurant.get());
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping
    public List<Restaurant> findAllRestaurants(){
        return restaurantListService.findAllRestaurants();
    }

    @PostMapping("/register")
    public Restaurant createRestaurant(@RequestBody Restaurant restaurant){
        return restaurantListService.createNewRestaurant(restaurant);

    }

    @PostMapping("/register/batch")
    public List<Restaurant> createRestaurant(@RequestBody List<Restaurant> restaurants){
        return restaurantListService.createAllRestaurants(restaurants);
    }

    @GetMapping("/toprated")
    public List<RestaurantResponse> findTopRatedRestaurants(){
        return restaurantListService.listAllTopRestaurants();
    }
}
