package com.example.demo.service;

import com.example.demo.entity.User;
import com.example.demo.repository.UserDao;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    public UserDao userDao;
    public UserService(UserDao userDao){
        this.userDao = userDao;
    }

    public Optional<User> getUser(Long id) {
        return userDao.findById(id);
    }

    public User createUser(User user){
        return userDao.save(user);
    }

    public List<User> getAllUsers(){
        return userDao.findAll();
    }
}
