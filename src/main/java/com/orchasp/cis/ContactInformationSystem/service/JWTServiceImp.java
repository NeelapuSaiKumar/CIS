package com.orchasp.cis.ContactInformationSystem.service;

import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JWTServiceImp {

	@Value("${app.secret}")
	private String SECRET_KEY;

	// 1st
	public SecretKey getSigninKey() {
		byte[] keybytes = Decoders.BASE64URL.decode(SECRET_KEY);
		return Keys.hmacShaKeyFor(keybytes);
	}

	// 2nd
	@SuppressWarnings("deprecation")
	public String generateToken(UserDetails user) {
		String token = Jwts.builder().subject(user.getUsername()).issuedAt(new Date(System.currentTimeMillis()))
				.expiration(new Date(System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(5)))
				.signWith(getSigninKey()).compact();
		return token;
	}

	// 3rd
	private Claims extractAllClaims(String token) {
		return Jwts.parser().verifyWith(getSigninKey()).build().parseSignedClaims(token).getPayload();
	}

	// 4th
	public <T> T extractClaim(String token, Function<Claims, T> resolver) {
		Claims claims = extractAllClaims(token);
		return resolver.apply(claims);
	}

	// 5th
	public String extractUsername(String token) {
		return extractClaim(token, Claims::getSubject);
	}

	// 6th
	public boolean isTokenValid(String token, UserDetails user) {
		String username = extractUsername(token);
		return (username.equals(user.getUsername())) && !isTokenExpired(token);
	}

	private boolean isTokenExpired(String token) {
		
		return extractExpiration(token).before(new Date());
	}

	private Date extractExpiration(String token) {
		
		return extractClaim(token,Claims::getExpiration);
	}

}
