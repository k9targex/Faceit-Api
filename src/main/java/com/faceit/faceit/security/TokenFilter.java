package com.faceit.faceit.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;



import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class TokenFilter extends OncePerRequestFilter {
    private JwtCore jwtCore;
    private UserDetailsService userDetailsService;


    @Autowired
    public void setUserDetailsService(UserDetailsService userDetailsService) {
<<<<<<< HEAD
        this.userDetailsService=userDetailsService;
=======
    this.userDetailsService=userDetailsService;
>>>>>>> 94a697e9c58e9f9683ee4a97e4c9db455707a693
    }
    @Autowired
    public void setJwtCore(JwtCore jwtCore) {
        this.jwtCore = jwtCore;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String jwt = null;
        String username = null;
        UserDetails userDetails = null;
        UsernamePasswordAuthenticationToken auth = null;
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
<<<<<<< HEAD
        if (!(request.getRequestURI().equals("/auth/signin")) && !(request.getRequestURI().equals("/auth/signup"))){
            try {
                String headerAuth = request.getHeader("Authorization");
                if (headerAuth != null && headerAuth.startsWith("Bearer ")) {
                    jwt = headerAuth.substring(7);
                }
                if (jwt != null) {
                    username = jwtCore.getNameFromJwt(jwt);
                    if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                        userDetails = userDetailsService.loadUserByUsername(username);
                        auth = new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null, authorities);
                        SecurityContextHolder.getContext().setAuthentication(auth);
                    }
                }
            } catch (Exception e) {
                logger.error("JWT token error: {}", e);
            }
        }
            filterChain.doFilter(request, response);

=======

        try{
            String headerAuth = request.getHeader("Authorization");
            if(headerAuth!= null && headerAuth.startsWith("Bearer ")){
                jwt = headerAuth.substring(7);
            }
            if(jwt != null){
                    username = jwtCore.getNameFromJwt(jwt);

                }
                if(username != null && SecurityContextHolder.getContext().getAuthentication() == null){
                    userDetails = userDetailsService.loadUserByUsername(username);
                    auth = new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,authorities);
                    SecurityContextHolder.getContext().setAuthentication(auth);
                }

        } catch (Exception e){
            logger.error("JWT token error: {}", e);

        }

        filterChain.doFilter(request,response);
>>>>>>> 94a697e9c58e9f9683ee4a97e4c9db455707a693
    }
}
