package com.mybank.facility.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.mybank.facility.dto.UserDto;
import com.mybank.facility.service.UserService;

@RequestMapping("/user")
@RestController
@CrossOrigin(
        origins = { "*" },
        allowCredentials = "false",
        allowedHeaders = { "*" },
        exposedHeaders = { "*" },
        maxAge = 60 * 30,
        methods = { RequestMethod.GET, RequestMethod.DELETE, RequestMethod.POST, RequestMethod.PUT })
public class UserController {

    @Autowired
    private UserService userService;


    @GetMapping("/role/applicant")
    public ResponseEntity<List<UserDto>> getAllApplicant() {
        return new ResponseEntity<List<UserDto>>(userService.getAllApplicant(), HttpStatus.OK);
    }
}
