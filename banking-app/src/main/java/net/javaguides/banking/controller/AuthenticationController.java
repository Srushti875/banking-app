package net.javaguides.banking.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PathVariable;
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
@RequestMapping("/api/accounts")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private UserRepository user;

    @Autowired
private PasswordEncoder passwordEncoder;


    @Autowired
    private jwtutil jwtUtil;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/{id}/register")
public ResponseEntity<String> register(@PathVariable Long id,@RequestBody User user) 

{
        user.setId(id);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(Role.ROLE_USER); // or admin
        userRepository.save(user);
        return ResponseEntity.ok("User registered successfully!");
}

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody loginRequest request) {
        System.out.println("Inside /login controller");
        System.out.println("Password matches: " +
    passwordEncoder.matches("Pass@123", "$2a$10$q6t0xt/8w4ez8MoCefyISOXgs5s2ovs.s.Y3D1gCDtOsui7zMH6Ba"));
    System.out.println(passwordEncoder.encode("Pass@123"));
    System.out.println(user.findAll());

       authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
    );
    System.out.println("a");

    UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());
    System.out.println("b");
    String token = jwtUtil.generateToken(userDetails);
    System.out.println("apple"+token);

    return ResponseEntity.ok(new AuthResponse(token));
    }
}
