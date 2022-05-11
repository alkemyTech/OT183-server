package com.alkemy.ong.auth.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.stream.Collectors;

@Component
public class JwtUtils {

    private static final String SECRET_KEY = "secret" ;

    public static String createToken(UserDetails userDetails) {
        Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY.getBytes());
        String accessToken = JWT.create()
                .withSubject(userDetails.getUsername())
                .withClaim("roles",
                        userDetails.getAuthorities()
                                .stream().map(GrantedAuthority::getAuthority)
                                .collect(Collectors.toList()))
                .withExpiresAt(toMinutes(60))
                .sign(algorithm);
        return accessToken;
    }

    public static String decodeToken(String token) {
        Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY.getBytes());
        JWTVerifier verifier = JWT.require(algorithm).build();
        DecodedJWT decodedJWT = verifier.verify(token);
        return decodedJWT.getSubject();
    }

    public static String[] extractRoles(String token) {
        Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY.getBytes());
        JWTVerifier verifier = JWT.require(algorithm).build();
        DecodedJWT decodedJWT = verifier.verify(token);
        return decodedJWT.getClaim("roles").asArray(String.class);
    }

    public static Date toMinutes(int minutes) {
        return new Date(System.currentTimeMillis() * 1000 * 60 * minutes);
    }


}