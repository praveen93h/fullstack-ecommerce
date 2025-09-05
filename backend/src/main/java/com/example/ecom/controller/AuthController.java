package com.example.ecom.controller;

import com.example.ecom.model.User;
import com.example.ecom.repository.UserRepository;
import com.example.ecom.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Map<String,String> body) {
        String username = body.get("username");
        String email = body.get("email");
        String password = body.get("password");
        if (userRepository.existsByUsername(username)) return ResponseEntity.badRequest().body(Map.of("error","username taken"));
        if (userRepository.findByEmail(email).isPresent()) return ResponseEntity.badRequest().body(Map.of("error","email taken"));
        User u = new User();
        u.setUsername(username);
        u.setEmail(email);
        u.setPassword(passwordEncoder.encode(password));
        u.getRoles().add("ROLE_USER");
        userRepository.save(u);
        return ResponseEntity.ok(Map.of("message","registered"));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String,String> body) {
        String username = body.get("username");
        String password = body.get("password");
        Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        String token = jwtUtil.generateToken(auth);
        return ResponseEntity.ok(Map.of("token", token));
    }
}
