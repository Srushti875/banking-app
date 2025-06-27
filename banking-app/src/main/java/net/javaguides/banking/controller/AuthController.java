// package net.javaguides.banking.controller;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.http.ResponseEntity;
// import org.springframework.security.authentication.AuthenticationManager;
// import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
// import org.springframework.security.core.userdetails.UserDetails;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.RequestBody;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RestController;

// import net.javaguides.banking.dto.AuthResponse;
// import net.javaguides.banking.dto.loginRequest;
// import net.javaguides.banking.service.CustomUserDetailsService;
// import net.javaguides.banking.service.jwtutil;

// @RestController
// @RequestMapping("/api/auth")
// public class AuthController
//  {

//     @Autowired
//     private AuthenticationManager authenticationManager;

//     @Autowired
//     private CustomUserDetailsService userDetailsService;

//     @Autowired
//     private jwtutil jwtUtil;

//     @PostMapping("/login")
//     public ResponseEntity<AuthResponse> login(@RequestBody loginRequest request) {
//         authenticationManager.authenticate(
//             new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
//         );

//         UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());
//         String token = jwtUtil.generateToken(userDetails);

//         return ResponseEntity.ok(new AuthResponse(token));
//     }
// }


