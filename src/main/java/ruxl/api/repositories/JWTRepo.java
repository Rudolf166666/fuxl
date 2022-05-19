package ruxl.api.repositories;

import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import ruxl.api.models.User;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JWTRepo  {
    private static final String secret="fuxl1312e214";
    private static final Logger LOGGER = LoggerFactory.getLogger(JWTRepo.class);


    public String generateRefreshToken(User user){
        return generateTokenBody(user,getRefreshTokenExpTime());
    }
    public String generateAccessToken(User user){
        return generateTokenBody(user,getAccessTokenExpTime());
    }

    public Long getUserIdByToken(String token){
        return ((Number)getTokenPayload(token).get("id")).longValue();
    }

    public String getUserEmailByToken(String token){
        return (String) getTokenPayload(token).get("email");
    }
    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(secret).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException e) {
            LOGGER.error("Invalid JWT signature: {}", e.getMessage());
        } catch (MalformedJwtException e) {
            LOGGER.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            LOGGER.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            LOGGER.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            LOGGER.error("JWT claims string is empty: {}", e.getMessage());
        }
        return false;
    }

    private  Map<String,Object> getTokenPayload(String token){
        Map<String,Object>claims= Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
        return claims;
    }
    private String generateTokenBody(User user,Date exp){
        Date now = new Date();
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", user.getId());
        claims.put("email", user.getEmail());
        String token= Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setNotBefore(now)
                .setExpiration(exp)
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
        return token;
    }
    private Date getRefreshTokenExpTime(){
        return Date.from(LocalDate.now()
                .plusDays(30)
                .atStartOfDay(ZoneId.systemDefault())
                .toInstant());
    }
    private Date getAccessTokenExpTime(){
        return Date.from(LocalDateTime.now()
                .plusMinutes(30)
                .atZone(ZoneId.systemDefault())
                .toInstant());
    }



}
