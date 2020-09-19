package com.company.storeapi.core.security.jwt;

import com.company.storeapi.core.exceptions.base.ServiceException;
import com.company.storeapi.core.exceptions.enums.LogRefServices;
import com.company.storeapi.core.security.service.UserDetailsImpl;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;
@Component
public class JwtUtils {

    @Value("${inventory.app.jwtSecret}")
    private String jwtSecret;

    @Value("${inventory.app.jwtExpirationMs}")
    private int jwtExpirationMs;

    public String generateJwtToken(Authentication authentication) {


        UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();

        return Jwts.builder()
                .setSubject((userPrincipal.getUsername()))
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    public String getUserNameFromJwtToken(String token) {
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
    }

    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException | MalformedJwtException e) {
            throw new ServiceException(LogRefServices.ERROR_TOKEN_INVALID,"Token no valido");
        } catch (ExpiredJwtException e) {
            throw new ServiceException(LogRefServices.ERROR_EXPIRED,"Token ha expirado");
        } catch (UnsupportedJwtException e) {
            throw new ServiceException(LogRefServices.ERROR_NO_SUPPORTED,"Token no soportado");
        } catch (IllegalArgumentException e) {
            throw new ServiceException(LogRefServices.ERROR_JWT_EMPTY,"Token vacio");
        }
    }
}
