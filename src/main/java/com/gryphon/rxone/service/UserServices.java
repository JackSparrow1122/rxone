package com.gryphon.rxone.service;


import java.util.List;
import java.util.Optional;
import java.util.UUID;

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
        user.getExtraFields().get("Org");
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

	public String extraFields(String email) {
		Users user = repository.findByEmail(email).get();
		String org = (String) user.getExtraFields().get("Org");
		String college = (String) user.getExtraFields().get("College");
		return "org= "+org +"  college= "+college;
	}

	public String updateUser(UUID id, RegisterDto userDto) {

	    Optional<Users> byId = repository.findById(id);

	    if (byId.isEmpty()) {
	        return "User not found";
	    }

	    Users user = byId.get();

	    user.setName(userDto.getName());
	    user.setEmail(userDto.getEmail());
	    user.setPasswordHash(userDto.getPasswordHash());
	    user.setExtraFields(userDto.getExtraFields());
	    user.setPhoneNumber(userDto.getPhoneNumber());

	    repository.save(user);

	    return "User updated successfully";
	}

	public String patchUser(UUID id, RegisterDto userDto) {

	    Optional<Users> byId = repository.findById(id);

	    if (byId.isEmpty()) {
	        return "User not found";
	    }

	    Users user = byId.get();

	    if (userDto.getName() != null) {
	        user.setName(userDto.getName());
	    }

	    if (userDto.getEmail() != null) {
	        user.setEmail(userDto.getEmail());
	    }

	    if (userDto.getPasswordHash() != null) {
	        user.setPasswordHash(userDto.getPasswordHash());
	    }

	    if (userDto.getPhoneNumber() != 0) {
	    	user.setPhoneNumber(userDto.getPhoneNumber());
	    }

	    if (userDto.getExtraFields() != null) {
	        user.setExtraFields(userDto.getExtraFields());
	    }

	    repository.save(user);

	    return "User patched successfully";
	}

	public String deleteUser(UUID id) {
		Optional<Users> byId = repository.findById(id);
		if(byId.isPresent()) {
			Users users = byId.get();
			repository.delete(users);
			return "User Delete Successfully";
		}
		return "User not found";
	}

	

	
}