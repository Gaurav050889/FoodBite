package com.example.demo.service;

import com.example.demo.dto.RestaurantResponse;
import com.example.demo.entity.Restaurant;
import com.example.demo.repository.RestaurantDao;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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


    public List<RestaurantResponse> listAllTopRestaurants(){
        return restaurantDao.findAll()
                .stream()
                .filter(restaurant -> restaurant.getRating() >= 4.5)
                .map(restaurant -> new RestaurantResponse(
                        restaurant.getName()
                ))
                .toList();
    }
}
