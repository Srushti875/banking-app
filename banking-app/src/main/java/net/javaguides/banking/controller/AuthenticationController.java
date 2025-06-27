package net.javaguides.banking.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.javaguides.banking.dto.AuthResponse;
import net.javaguides.banking.dto.loginRequest;
import net.javaguides.banking.entity.Role;
import net.javaguides.banking.entity.User;
import net.javaguides.banking.repository.UserRepository;
import net.javaguides.banking.service.CustomUserDetailsService;
import net.javaguides.banking.service.jwtutil;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
private PasswordEncoder passwordEncoder;


    @Autowired
    private jwtutil jwtUtil;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/register")
public ResponseEntity<String> register(@RequestBody User user) 

{
   
    user.setPassword(passwordEncoder.encode(user.getPassword()));
    user.setRole(Role.ROLE_USER); // or admin
    userRepository.save(user);
    return ResponseEntity.ok("User registered successfully!");
}

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody loginRequest request) {
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );

       
        UserDetails userDetails = userDetailsService.loadUserByUsername((String) request.getUsername());
        String token = jwtUtil.generateToken(userDetails);

        


        return ResponseEntity.ok(new AuthResponse(token));
    }
}
