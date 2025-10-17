package com.fitverse.shared.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.time.Instant;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

public class JwtTokenProvider {

    private final JwtProperties properties;
    private final Key signingKey;

    public JwtTokenProvider(JwtProperties properties) {
        this.properties = properties;
        this.signingKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(properties.getSecret()));
    }

    public String generateToken(String subject, String email, Collection<? extends GrantedAuthority> authorities) {
        Instant now = Instant.now();
        Instant expiry = now.plusSeconds(properties.getAccessTokenValiditySeconds());
        String roles = authorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.joining(","));

        return Jwts.builder()
                .setId(subject)
                .setSubject(email)
                .setIssuer(properties.getIssuer())
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(expiry))
                .claim("roles", roles)
                .signWith(signingKey, SignatureAlgorithm.HS256)
                .compact();
    }

    public Authentication toAuthentication(String token) {
        Claims claims = parse(token);
        String email = claims.getSubject();
        String rolesClaim = claims.get("roles", String.class);
        List<GrantedAuthority> authorities = rolesClaim == null ? List.of() : List.of(rolesClaim.split(",")).stream()
                .filter(role -> !role.isBlank())
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
        User principal = new User(email, "", authorities);
        return new UsernamePasswordAuthenticationToken(principal, token, authorities);
    }

    public boolean isTokenValid(String token) {
        try {
            parse(token);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    private Claims parse(String token) {
        return Jwts.parser()
                .requireIssuer(properties.getIssuer())
                .setSigningKey(signingKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
