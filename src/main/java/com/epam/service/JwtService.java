package com.epam.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.rsa.crypto.KeyStoreKeyFactory;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.security.KeyPair;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {
    @Value("${jwt.key-store.path}")
    private Resource keyStorePath;
    @Value("${jwt.key-store.password}")
    private String keyStorePassword;
    @Value("${jwt.key-store.alias}")
    private String keyStoreAlias;
    @Value("${jwt.key-pair.password}")
    private String keyPairPassword;
    private KeyPair keyPair;

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public String generateToken(
            Map<String, Object> extraClaims,
            UserDetails userDetails
    ) {
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24))
                .signWith(getSignInKey(), SignatureAlgorithm.RS256)
                .compact();
    }
    public boolean validateToken(String authToken) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(getKeyPair().getPublic())
                    .build()
                    .parseClaimsJws(authToken);
            return true;
        } catch (Exception ex) {
            // log the exception
        }
        return false;
    }
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSignInKey() {
        if (this.keyPair == null) {
            // Initialize the keyPair if it's null
            this.keyPair = getKeyPair();
        }
        // Use the provided private key for JWT signing
        return keyPair.getPrivate();
    }

    private KeyPair getKeyPair() {
        if (keyPair == null) {
            KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(keyStorePath, keyStorePassword.toCharArray());
            keyPair = keyStoreKeyFactory.getKeyPair(keyStoreAlias, keyPairPassword.toCharArray());
        }
        return keyPair;
    }
}
