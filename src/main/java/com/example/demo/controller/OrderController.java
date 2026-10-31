package com.example.demo.controller;

import com.example.demo.dto.OrderServiceDTO;
import com.example.demo.entity.Order;
import com.example.demo.entity.User;
import com.example.demo.service.OrderListService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    public OrderListService orderListService;

    public OrderController(OrderListService orderListService){
        this.orderListService = orderListService;
    }

    @GetMapping
    public List<Order> getAllOrders(){
        return orderListService.getAllOrders();
    }

    @PostMapping("/neworder")
    public Order CreateOrder(@RequestBody Order order){
        return orderListService.createOrder(order);
    }

    /*@GetMapping("/{id}")
    public Optional<Order> getOrderById(@PathVariable Long id){
        return orderListService.getOrder(id);
    }*/

    /*Here we are using this ResponseEntity because otherwise http://localhost:8080/api/orders/1000 will also would show 200 OK though result will come as []*/
    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable Long id){
            Optional<Order> order = orderListService.getOrder(id);
            if(order.isPresent()){
                return ResponseEntity.ok(order.get());
            }else{
                return ResponseEntity.notFound().build();
            }
    }

    /*With DTO*/
    @GetMapping("/user/{userId}")
    public List<OrderServiceDTO> getUserOrders(@PathVariable Long userId)  {
        return orderListService.getUserAllOrders(userId);
    }

    @GetMapping("/user/{userId}/status")
    public List<String> orderStatus(@PathVariable Long userId){
        return orderListService.getOrderStatuses(userId);
    }


    @GetMapping("/user/{userId}/statuses")
    public Map<String, Long> getAllStatusesForTheUser(@PathVariable Long userId){
        return orderListService.getAllOrderStatusesForUser(userId);
    }


    @GetMapping("/user/placedStatus")
    public List<Order> findAllPlacedOrders(){
        return orderListService.findAllPlacedOrders();
    }

    @GetMapping("/user/placesOrderIds")
    public List<Long> getAllPlacedOrderIds(){
        return orderListService.findAllPlacedOrderIds();
    }

    @GetMapping("/luxuryOrders")
    public List<Order> getAllExpensivePlacedOrder(){
        return orderListService.findExpensivePlacedOrders();
    }
}
