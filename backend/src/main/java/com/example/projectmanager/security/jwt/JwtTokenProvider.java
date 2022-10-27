package com.example.projectmanager.security.jwt;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.example.projectmanager.model.User;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;


import io.jsonwebtoken.*;

import static com.example.projectmanager.security.SecurityConstants.EXPIRATION_TIME;
import static com.example.projectmanager.security.SecurityConstants.SECRET;

//provides methods for generating, parsing, validating JWT
@Component
public class JwtTokenProvider {

    //generate the token

    public String generateToken(Authentication authentication) {
        User user = (User)authentication.getPrincipal();
        Date now = new Date(System.currentTimeMillis());

        Date expiryDate = new Date(now.getTime()+EXPIRATION_TIME);

        String userId = Long.toString(user.getId());

        Map<String, Object> claimsMap = new HashMap<>();

        claimsMap.put("id", (Long.toString(user.getId())));
        claimsMap.put("username", user.getUsername());
        claimsMap.put("firstName", user.getFirstName());
        claimsMap.put("lastName", user.getLastName());

        return Jwts.builder()
                .setSubject(userId)
                .setClaims(claimsMap)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .compact();

    }


    //validate the token

    public boolean validateToken(String token){
        try{
            Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token);
            return true;
        }catch (SignatureException ex){
            System.out.println("Invalid JWT Signature");
        }catch (MalformedJwtException ex){
            System.out.println("Invalid JWT Token");
        }catch (ExpiredJwtException ex){
            System.out.println("Expired JWT token");
        }catch (UnsupportedJwtException ex){
            System.out.println("Unsupported JWT token");
        }catch (IllegalArgumentException ex){
            System.out.println("JWT claims string is empty");
        }
        return false;
    }

    //get the user id from token

    public Long getUserIdFromJWT(String token){
        Claims claims = Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).getBody();
        String id = (String)claims.get("id");

        return Long.parseLong(id);
    }

}
