//package com.sivapavan.restoops.security;
//
//import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.security.Keys;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Component;
//
//import javax.crypto.SecretKey;
//import java.nio.charset.StandardCharsets;
//import java.util.Date;
//
//@Component
//public class JwtTokenProvider {
//
//	@Value("${app.jwt.secret}")
//	private String jwtSecret;
//
//	@Value("${app.jwt.access-token-expiration-ms}")
//	private long accessTokenExpirationMs;
//
//	@Value("${app.jwt.refresh-token-expiration-ms}")
//	private long refreshTokenExpirationMs;
//
//	private SecretKey signingKey() {
//		return Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
//	}
//
//	public String generateAccessToken(String email) {
//		return generateToken(email, accessTokenExpirationMs);
//	}
//
//	public String generateRefreshToken(String email) {
//		return generateToken(email, refreshTokenExpirationMs);
//	}
//
//	private String generateToken(String email, long expirationMs) {
//		Date now = new Date();
//		Date expiry = new Date(now.getTime() + expirationMs);
//
//		return Jwts.builder().subject(email).issuedAt(now).expiration(expiry).signWith(signingKey()).compact();
//	}
//
//	public String getEmailFromToken(String token) {
//		Claims claims = Jwts.parser().verifyWith(signingKey()).build().parseSignedClaims(token).getPayload();
//
//		return claims.getSubject();
//	}
//
//	public boolean validateToken(String token) {
//		try {
//			Jwts.parser().verifyWith(signingKey()).build().parseSignedClaims(token);
//			return true;
//		} catch (Exception e) {
//			return false;
//		}
//	}
//}




package com.sivapavan.restoops.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtTokenProvider {

    private static final Logger log = LoggerFactory.getLogger(JwtTokenProvider.class);

    @Value("${app.jwt.secret}")
    private String jwtSecret;

    @Value("${app.jwt.access-token-expiration-ms:900000}")
    private long accessTokenExpirationMs;

    @Value("${app.jwt.refresh-token-expiration-ms:604800000}")
    private long refreshTokenExpirationMs;

    private SecretKey signingKey() {
        return Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
    }

    public String generateAccessToken(String email) {
        String token = generateToken(email, accessTokenExpirationMs);
        log.info("Generated access token for: {}", email);
        return token;
    }

    public String generateRefreshToken(String email) {
        String token = generateToken(email, refreshTokenExpirationMs);
        log.info("Generated refresh token for: {}", email);
        return token;
    }

    private String generateToken(String email, long expirationMs) {
        Date now = new Date();
        Date expiry = new Date(now.getTime() + expirationMs);

        String token = Jwts.builder()
                .subject(email)
                .issuedAt(now)
                .expiration(expiry)
                .signWith(signingKey())
                .compact();

        log.info("Generated token for {} with expiry: {}", email, expiry);
        return token;
    }

    public String getEmailFromToken(String token) {
        Claims claims = Jwts.parser()
                .verifyWith(signingKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();

        return claims.getSubject();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .verifyWith(signingKey())
                    .build()
                    .parseSignedClaims(token);
            return true;
        } catch (Exception e) {
            log.error("Token validation failed: {}", e.getMessage());
            return false;
        }
    }
}