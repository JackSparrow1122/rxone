package com.gryphon.rxone.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.gryphon.rxone.DTO.LoginDto;
import com.gryphon.rxone.DTO.RegisterDto;
import com.gryphon.rxone.model.Users;
import com.gryphon.rxone.service.UserServices;

@RestController
@CrossOrigin (originPatterns = "*")
public class UserController {

    @Autowired
    private UserServices services;

    @PostMapping("/register")
    public String register(@RequestBody RegisterDto userDto) {
        return services.register(userDto);
    }
    
    @PostMapping("/login")
    public String login(@RequestBody LoginDto userDto) {
    	return services.login(userDto);
    }
    
    @GetMapping("/getUsers")
    public List<Users> getUsers() {
    	return services.getUsers();
    }
}
