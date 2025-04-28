package com.example.lockerAuth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.lockerAuth.dao.UserDao;
import com.example.lockerAuth.entity.Usuarios;
import com.example.lockerAuth.repository.UserRepository;
import com.example.lockerAuth.request.LoginRequest;
import com.example.lockerAuth.request.RegisterRequest;
import com.example.lockerAuth.response.AuthResponse;

@Service
public class UserService {
    @Autowired
    private UserRepository repository;
    @Autowired
    private JwtService jwt_service;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authManager;

    public UserDao getUser(Integer id) {
        Usuarios rsp = repository.findById(id).get();
        UserDao user = new UserDao(rsp.getId(), rsp.getU_nombre(), rsp.getU_password(), rsp.getEmail());
        return user;
    }

    public boolean register(RegisterRequest req) {
        Usuarios newUser = Usuarios.builder()
                .u_nombre(req.getUser())
                .u_password(passwordEncoder.encode(req.getPassword()))
                .email(req.getEmail())
                .build();
        if (repository.findByU_nombre(newUser.getU_nombre()).isEmpty()) {
            repository.save(newUser);
            return true;
        }
        return false;
    }

    public AuthResponse login(LoginRequest req) {
        authManager.authenticate(new UsernamePasswordAuthenticationToken(req.getUser(), req.getPassword()));
        UserDetails user_details = repository.findByU_nombre(req.getUser()).orElseThrow();
        String token = jwt_service.getToken(user_details);
        return AuthResponse.builder()
                .token(token)
                .build();
    }

}
