package com.mybank.facility.config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.mybank.facility.service.FacilityUserDetailsService;
import com.mybank.facility.util.JwtTokenUtil;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class FacilityJwtRequestFilter extends OncePerRequestFilter {

    @Autowired
    private FacilityUserDetailsService facilityUserDetailsService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        if (request.getServletPath().contains("/auth")) {
            filterChain.doFilter(request, response);
            return;
          }
          final String authHeader = request.getHeader("Authorization");
          final String jwt;
          final String name;
          if (authHeader == null ||!authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
          }
          jwt = authHeader.substring(7);
          name = jwtTokenUtil.extractUsername(jwt);
          if (name != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = facilityUserDetailsService.loadUserByUsername(name);
           
            if (jwtTokenUtil.isTokenValid(jwt, userDetails)) {
              UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                  userDetails,
                  null,
                  userDetails.getAuthorities()
              );
              authToken.setDetails(
                  new WebAuthenticationDetailsSource().buildDetails(request)
              );
              SecurityContextHolder.getContext().setAuthentication(authToken);
            }
          }
          filterChain.doFilter(request, response);
        }
    

}
