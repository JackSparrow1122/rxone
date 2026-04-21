package com.gryphon.rxone.service;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gryphon.rxone.DTO.LoginDto;
import com.gryphon.rxone.DTO.RegisterDto;
import com.gryphon.rxone.config.JwtUtil;
import com.gryphon.rxone.enums.PasswordProvider;
import com.gryphon.rxone.enums.Role;
import com.gryphon.rxone.model.Users;
import com.gryphon.rxone.repository.UserRepository;




@Service
public class UserServices {

	@Autowired
	private JwtUtil jwtUtil;
	
    @Autowired
    private UserRepository repository;
    

    public String register(RegisterDto userDto) {
    	
    	Optional<Users> byEmail = repository.findByEmail(userDto.getEmail());
    	
    	if(byEmail.isPresent()) {
    		return "Email already available";
    	}
    	else {
        Users user = new Users();
        
        user.setEmail(userDto.getEmail());
        user.setPasswordHash(userDto.getPasswordHash());
        user.setName(userDto.getName());
        user.setPhoneNumber(userDto.getPhoneNumber());
        user.setRole(Role.ADMIN);
        user.setPasswordProvider(PasswordProvider.LOCAL);
        user.setExtraFields(userDto.getExtraFields());
         repository.save(user);
         return "User Register successfully";
    	}
    }

	public String login(LoginDto loginDto) {
		Optional<Users> byEmail = repository.findByEmail(loginDto.getEmail());
		
    	if(byEmail.isPresent()) {
    		Users user = byEmail.get();
    		if(user.getPasswordHash().equals(loginDto.getPasswordHash())) {
    			String token = jwtUtil.generateToken(user.getEmail());
    		return token;
    		}
    	}
		return "Invalid User";
	}

	public List<Users> getUsers() {
		return repository.findAll();
	}

	
}