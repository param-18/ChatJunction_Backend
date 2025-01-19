package com.chat_sphere.app.token_generation.utils;

import com.chat_sphere.app.dtos.Reply;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.Claims;
import java.util.Date;

public class JWTUtils {
    private static final String SECRET_KEY = "Hulu@321";

    public static String generateToken(Reply reply,String userAgent) {
        reply.setAttribute("userAgent",userAgent);
        return Jwts.builder()
                .setClaims(reply.getSession())
                .setSubject("MobileAccess")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 3600 * 1000)) // 1 hour validity
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    public static Claims extractClaims(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
    }

    public static boolean isTokenValid(String token, String userAgent) {
        Claims claims = extractClaims(token);
        return claims.get("userAgent").toString().equals(userAgent) && claims.getExpiration().after(new Date());
    }
}
