package com.onlineWorkers.onlineWorkersProfileService.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtTokenUtil {

    @Value("${jwt.secret}")
    private  String secret;
    @Value("${jwt.expiration}")
    private long expiration;

    public String extractUserName(String authToken) {
        return extractClaim(authToken, Claims::getSubject);
    }

    public Date extractExpiration(String authToken){
        return extractClaim(authToken,Claims::getExpiration);
    }
    private <T> T extractClaim(String authToken, Function<Claims,T> claimResolver) {
        final Claims claims=extractAllClaims(authToken);
        return claimResolver.apply(claims);
    }

    private Claims extractAllClaims(String authToken) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(authToken).getBody();
    }

    public boolean validateToken(String authToken, UserDetails userDetails) {
        //  JwtUser jwtUser=(JwtUser)userDetails;
          final String username=extractUserName(authToken);
          return (username.equals(userDetails.getUsername()) && !isTokenExpired(authToken));
    }

    private boolean isTokenExpired(String authToken) {
        return extractExpiration(authToken).before(new Date());
    }

    public String generateToken(UserDetails userDetails) {
        Map<String,Object> claims=new HashMap<>();
       return createToken(claims,userDetails.getUsername());
    }

    private String createToken(Map<String, Object> claims, String username) {
          return Jwts.builder()
                     .setClaims(claims)
                     .setSubject(username)
                     .setIssuedAt(new Date(System.currentTimeMillis()))
                     .setExpiration(getExpirationDate())
                     .signWith(SignatureAlgorithm.HS256,secret)
                     .compact();
    }

    private Date getExpirationDate() {
         return new Date(System.currentTimeMillis()+expiration*1000);
    }
}
