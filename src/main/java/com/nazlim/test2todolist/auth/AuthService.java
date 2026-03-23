package com.nazlim.test2todolist.auth;

import com.nazlim.test2todolist.dto.LoginRequest;
import com.nazlim.test2todolist.dto.RegisterRequest;
import com.nazlim.test2todolist.dto.AuthResponse;
import com.nazlim.test2todolist.entity.AppUser;
import com.nazlim.test2todolist.security.JwtService;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class AuthService {

    private final UserRepository users;
    private final JwtService jwt;

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public AuthService(UserRepository users, JwtService jwt) {
        this.users = users;
        this.jwt = jwt;
    }

    public void register(RegisterRequest req) {
        if (users.existsByUsername(req.username())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Username already exists");
        }

        AppUser user = new AppUser();
        user.setUsername(req.username());
        user.setPassword(encoder.encode(req.password()));

        users.save(user);
    }

    public AuthResponse login(LoginRequest req) {
        AppUser user = users.findByUsername(req.username())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid credentials"));

        if (!encoder.matches(req.password(), user.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid credentials");
        }

        String token = jwt.generateToken(user.getUsername());
        return new AuthResponse("Giriş yapıldı", token); // ✅ token burada
    }
}