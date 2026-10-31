package com.example.demo.service;

import com.example.demo.dto.OrderServiceDTO;
import com.example.demo.entity.Order;
import com.example.demo.repository.OrderDao;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
public class OrderListService {

    public OrderDao orderDao;

    public OrderListService(OrderDao orderDao){
        this.orderDao=orderDao;
    }

    public List<Order> getAllOrders(){
        return orderDao.findAll();
    }

    public Order createOrder(Order order){
        return orderDao.save(order);
    }

    public Optional<Order> getOrder(Long id){
        return orderDao.findById(id);
    }

    /*This is generic one now lets create using streams
    * Order::getStatus is a method reference.

It means:

"For every Order object, call its getStatus() method and use the returned status as the grouping key."

Your data will be grouped like this:
Status	Order IDs
PLACED	1, 3, 4, 7, 8
CANCELLED	2
DELIVERED	5, 6*/

    public Map<String, Long> getAllOrderStatusesForUser(Long id){
        return orderDao.findByUserId(id)//we need user id here not order id
                .stream()
                .collect(Collectors.groupingBy(Order::getStatus, Collectors.counting()));
    }

    /*Service without DTO*/
    /*public List<Order> getUserAllOrders(Long id){
        return orderDao.findByUserId(id);
    }*/

    public List<OrderServiceDTO> getUserAllOrders(Long id){
        return orderDao.findByUserId(id).stream().map(order -> new OrderServiceDTO(order.getId(), order.getStatus())).toList();
    }

    public List<String> getOrderStatuses(Long id){
        /*This line means like we are calling getUserAllOrders which will give List of orders now from there one by one pick and give the statuses*/
        return getUserAllOrders(id).stream().map(OrderServiceDTO::getStatus).toList();
    }


    Predicate<Order> isPlaced = order -> order.getStatus().equals("PLACED");
    Predicate<Order> isExpensive = order -> order.getTotalAmount() > 100;


    public List<Order> findAllPlacedOrders(){
        return orderDao.findAll()
                .stream()
                .filter(order -> order.getStatus().equals("PLACED"))
                .toList();
    }

    public List<Long> findAllPlacedOrderIds(){
        return orderDao.findAll()
                .stream()
                .filter(order -> order.getStatus().equals("PLACED"))
                .map(Order :: getId)
                .toList();
    }


    public List<Order> findExpensivePlacedOrders(){
        List<Order> orders = orderDao.findAll()
                .stream()
                .filter(isExpensive)
                .filter(isPlaced)
                .toList();

        return orders;
    }
}
