package com.mybank.facility.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.mybank.facility.dto.AuthResponse;
import com.mybank.facility.dto.UserDto;
import com.mybank.facility.modal.User;
import com.mybank.facility.service.FacilityUserDetailsService;
import com.mybank.facility.service.UserService;
import com.mybank.facility.util.JwtTokenUtil;

@RequestMapping("/auth")
@RestController
@CrossOrigin(origins = { "*" }, allowCredentials = "false", allowedHeaders = { "*" }, exposedHeaders = {
        "*" }, maxAge = 60
                * 30, methods = { RequestMethod.GET, RequestMethod.DELETE, RequestMethod.POST, RequestMethod.PUT })
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private FacilityUserDetailsService facilityUserDetailsService;

    @PostMapping("/signup")
    public void signupAccount(@RequestBody UserDto userDto) {
        userService.save(userDto);
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthResponse> createAuthenticationToken(@RequestBody UserDto userDto) throws Exception {
        authenticate(userDto.getName(), userDto.getPassword());

        final UserDetails userDetails = facilityUserDetailsService.loadUserByUsername(userDto.getName());

        User user = userService.getUserByName(userDetails.getUsername());

        UserDto usertoReturn = new UserDto();
        usertoReturn.setId(user.getId());
        usertoReturn.setRole(user.getRole());
        return ResponseEntity.ok(new AuthResponse(jwtTokenUtil.generateToken(userDetails), usertoReturn));

    }

    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }

}
