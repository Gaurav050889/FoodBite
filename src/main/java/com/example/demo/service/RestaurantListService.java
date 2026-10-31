package com.example.demo.service;

import com.example.demo.entity.Restaurant;
import com.example.demo.repository.RestaurantDao;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RestaurantListService {

    public RestaurantDao restaurantDao;

    public RestaurantListService(RestaurantDao restaurantDao){
        this.restaurantDao=restaurantDao;
    }

    public Optional<Restaurant> findRestaurantWithId(Long id){
        return restaurantDao.findById(id);
    }

    public List<Restaurant> findAllRestaurants(){
        return restaurantDao.findAll();
    }

    public Restaurant createNewRestaurant(Restaurant restaurant){
        return restaurantDao.save(restaurant);
    }

    public List<Restaurant> createAllRestaurants(List<Restaurant> dhaba){
        return restaurantDao.saveAll(dhaba);
    }
}
