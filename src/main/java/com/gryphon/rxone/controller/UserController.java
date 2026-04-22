package com.gryphon.rxone.controller;


import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
    
    @GetMapping("/extraFields/{email}")
    public String extraFields(@PathVariable String email) {
    	return services.extraFields(email);
    }
    
    @PutMapping("/editUser/{id}")
    public String updateUser(@PathVariable UUID id, @RequestBody RegisterDto userDto) {
        return services.updateUser(id, userDto);
    }
    
    @PatchMapping("/editUser/{id}")
    public String patchUser(@PathVariable UUID id, @RequestBody RegisterDto userDto){
    	return services.patchUser(id,userDto);
    }
    
    @DeleteMapping("/delete/{id}")
    public String deleteUser(@PathVariable UUID id){
    	return services.deleteUser(id);
    }
}
