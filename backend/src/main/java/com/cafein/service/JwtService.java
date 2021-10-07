package com.cafein.service;

import com.cafein.entity.User;

import org.springframework.security.core.Authentication;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;

public interface JwtService {
    <T> String createAccessToken(int userId);
    String getAccessToken();
    int getUserId();
    User getUser();
    User getChatUserDB(String accessToken);
    Authentication getAuthentication(String token);
    boolean validateToken(String jwtToken);
    Jws<Claims> getClaims(String jwtToken);
}