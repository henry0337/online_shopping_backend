package com.henry.online_shopping.service;

import com.henry.online_shopping.entity.Role;
import com.henry.online_shopping.entity.User;
import com.henry.online_shopping.utility.constant.Constant;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.lang.NonNull;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import static io.jsonwebtoken.SignatureAlgorithm.HS256;

@Service
public class JwtService {

    public final String generateTokenWithUserInfo(@NonNull User user) {
        Map<String, Object> claims = new HashMap<>();

        claims.put("id", user.getId());
        claims.put("roles", user.getRole().toString());
        claims.put("name", user.getName());
        claims.put("email", user.getUsername());

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(user.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 24 * 7 * 60 * 60 * 10))
                .signWith(getSignInKey(), HS256)
                .compact();
    }

    public final String generateRefreshToken(Map<String, Object> extraClaims, @NonNull User user) {
        return Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(user.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 24 * 7 * 60 * 60 * 10))
                .signWith(getSignInKey(), HS256)
                .compact();
    }

    public final User obtainUserFromToken(final String token) {
        if (token == null) return null;
        Claims claims = extractAllClaims(token);

        return User.builder()
                .id((String) claims.get("id"))
                .name((String) claims.get("name"))
                .role(Role.valueOf(claims.get("roles").toString()))
                .email((String) claims.get("email"))
                .build();
    }

    public final boolean isTokenValid(String token, @NonNull UserDetails userDetails) {
        final String username = extractUsername(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    public final String extractUsername(final String token) {
        return extractClaim(token, Claims::getSubject);
    }

    @NonNull
    private SecretKey getSignInKey() {
        byte[] key = Decoders.BASE64.decode(Constant.RAW_TOKEN);
        return Keys.hmacShaKeyFor(key);
    }

    private boolean isTokenExpired(String token) {
        return extractClaim(token, Claims::getExpiration).before(new Date());
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private <T> T extractClaim(String token, @NonNull Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
}
