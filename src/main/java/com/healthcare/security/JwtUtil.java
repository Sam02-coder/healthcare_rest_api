package com.healthcare.security;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {

	private static final String SECRET="healthcareprojectsecretkeyhealthcareprojectsecretkey";
	
	private static final SecretKey KEY=(Keys.hmacShaKeyFor(SECRET.getBytes()));
	
	public String generateToken(String username) {
		
		return Jwts.builder()
				.setSubject(username)
				.setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis()+86400000))
				.signWith(KEY)
				.compact();
	}
	
	public String extractUserName(String token) {
		return Jwts
				.parserBuilder()
				.setSigningKey(KEY)
				.build()
				.parseClaimsJws(token)
				.getBody()
				.getSubject();
	}
	
	public boolean  validateToken(String token, String userName) {
		
		String extractedUserName=extractUserName(token);
		
		return extractedUserName.equals(userName);
	}
}
