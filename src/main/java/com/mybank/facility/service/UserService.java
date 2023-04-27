package com.mybank.facility.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.mybank.facility.dto.UserDto;
import com.mybank.facility.enums.Role;
import com.mybank.facility.modal.User;
import com.mybank.facility.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
 
   
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    public void setPasswordEncoder(@Lazy PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }
    
    @Transactional
    public void save(UserDto userDto) {
        User user = new User();
        user.setName(userDto.getName());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setRole(userDto.getRole());
        userRepository.save(user);
    }
    
    public User getUserByName(String name) throws Exception {
       return  userRepository.findByName(name).orElseThrow(()->new Exception());
    }
    
    public List<UserDto> getAllApplicant() {
       List<User> users=  userRepository.findByRole(Role.APPLICANT);
       return users.stream().map(user->{
           UserDto userDto = new UserDto();
           userDto.setId(user.getId());
           userDto.setName(user.getName());
           return userDto;
       }).collect(Collectors.toList());
    }
    
}
