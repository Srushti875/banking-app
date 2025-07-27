package net.javaguides.banking.service;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import net.javaguides.banking.entity.User;
import io.jsonwebtoken.io.Decoders;

@Component
public class jwtutil 
{
   

        @Value("${jwt.secret}")
        private String secret;

        @Value("${jwt.expiration}")
        private long jwtExpirationInMs;

        private Key key;

        @PostConstruct
        public void init()
         {
        //     try
        //     {
            byte[] keyBytes = Decoders.BASE64.decode(secret);
        //    // 

        //     if (keyBytes.length < 32) 
        //     {
        //     throw new IllegalArgumentException("JWT secret key is too short. Must be at least 256 bits (32 bytes).");
        //     }
        //         this.key = Keys.hmacShaKeyFor(keyBytes);
        //     }
        // this.key = Keys.hmacShaKeyFor(keyBytes);
        // }
        //  catch (Exception e) 
        //  {
        // throw new RuntimeException("Failed to initialize JWT key: " + e.getMessage(), e);
        // }
        // try {
        // byte[] keyBytes = Decoders.BASE64.decode(secret);
        // if (keyBytes.length < 32)
        //  {
        //     throw new IllegalArgumentException("JWT secret key is too short. Must be at least 256 bits (32 bytes).");
        // }
        // this.key = Keys.hmacShaKeyFor(keyBytes);
        // } 
        // catch (Exception e) 
        // {
        // throw new RuntimeException("Failed to initialize JWT key: " + e.getMessage(), e);
        //  }
           
        // }
        this.key = Keys.hmacShaKeyFor(keyBytes);
         }

      

    public String generateToken(UserDetails userDetails) {
        return Jwts.builder()
            .setSubject(userDetails.getUsername())
            .claim("role", userDetails.getAuthorities().iterator().next().getAuthority())
            .setIssuedAt(new Date(System.currentTimeMillis()))
            .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationInMs)) // 10 hrs
            .signWith(key,SignatureAlgorithm.HS256)
            .compact();
    }
    public String extractUsername(String token) {
        return Jwts.parserBuilder()
            .setSigningKey(key)
            .build()
            .parseClaimsJws(token)
            .getBody()
            .getSubject();
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        return extractUsername(token).equals(userDetails.getUsername());
    }
    }  

