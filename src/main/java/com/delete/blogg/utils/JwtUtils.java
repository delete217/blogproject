package com.delete.blogg.utils;

import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtUtils {
    private static final String jwtToken = "delete111";

    public static String createToken(long userId) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", userId);
        JwtBuilder jwtBuilder = Jwts.builder()
                .signWith(SignatureAlgorithm.HS256, jwtToken)
                .setClaims(claims)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 24 * 60 * 60 * 60 * 1000));
        String token = jwtBuilder.compact();
        return token;
    }

    public static Map<String,Object> checkToken(String token){
        try {
            Jwt parse = Jwts.parser().setSigningKey(jwtToken).parse(token);
            return (Map<String,Object>) parse.getBody();
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        String token = JwtUtils.createToken(100l);
        System.out.println(token);
        Map<String, Object> map = JwtUtils.checkToken(token);
        System.out.println(map.get("userId"));
    }
}
